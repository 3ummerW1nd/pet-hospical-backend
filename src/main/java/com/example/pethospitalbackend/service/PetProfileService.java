package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.page.PetProfileInfo;
import com.example.pethospitalbackend.domain.profile.*;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.PetProfileRepository;
import com.example.pethospitalbackend.search.converter.SearchEntityConverter;
import com.example.pethospitalbackend.search.entity.Result;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import com.example.pethospitalbackend.util.DateUtil;
import com.example.pethospitalbackend.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PetProfileService {
    @Autowired
    private PetProfileRepository petProfileRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SearchUtil searchUtil;

    @Transactional
    public CommonResponse createOrUpdatePetProfile(Integer id, String name, String type, String genderString, String birthday, Double weight, String description, List<Integer> medicines, List<Integer> checkups, List<Integer> diseases, List<MultipartFile> images) {
        Boolean gender = null;
        if (genderString.equals("公")) {
            gender = true;
        } else if (genderString.equals("母")) {
            gender = false;
        } else {
            return CommonResponse.builder()
                    .code(1)
                    .message("性别请填写\"公\"或\"母\"")
                    .build();
        }
        if(name.length() > 20 || type.length() > 10 || description.length() > 2000) {
            return CommonResponse.builder()
                    .code(1)
                    .message("输入文字过长，请检查")
                    .build();
        }
//        if (images.size() > 9) {
//            return CommonResponse.builder()
//                    .code(1)
//                    .message("每份宠物档案最多可以附9张图片，请检查")
//                    .build();
//        }
        if (id != null && !petProfileRepository.existsById(id)) {
            return CommonResponse.builder()
                    .code(1)
                    .message("宠物档案不存在，请检查")
                    .build();
        }
        // 上传图片
//        StringBuilder imageStringBuilder = new StringBuilder();
//        images.forEach(image -> imageStringBuilder.append(FileUtil.upload(image) + ","));
//        imageStringBuilder.deleteCharAt(imageStringBuilder.length() - 1);

        // 获取疾病集合
        Set<DiseaseType> diseaseSet = entityManager.createQuery("SELECT d FROM DiseaseType d WHERE d.id IN (:ids)", DiseaseType.class)
                .setParameter("ids", diseases)
                .getResultStream().collect(Collectors.toSet());

        // 获取药品集合
        Set<Medicine> medicineSet = entityManager.createQuery("SELECT d FROM Medicine d WHERE d.id IN (:ids)", Medicine.class)
                .setParameter("ids", medicines)
                .getResultStream().collect(Collectors.toSet());

        // 获取检查集合
        Set<Checkup> checkupSet = entityManager.createQuery("SELECT d FROM Checkup d WHERE d.id IN (:ids)", Checkup.class)
                .setParameter("ids", checkups)
                .getResultStream().collect(Collectors.toSet());


        Pet pet = Pet.builder()
                .name(name)
                .type(type)
                .gender(gender)
//                .images(imageStringBuilder.toString())
                .birthday(DateUtil.getDate(birthday))
                .description(description)
                .weight(weight)
                .diseases(diseaseSet)
                .checkups(checkupSet)
                .medicines(medicineSet)
                .build();
        if (id != null) {
            pet.setId(id);
        }
        petProfileRepository.save(pet);
        searchUtil.upload(SearchEntityConverter.getSearchableEntity(pet));
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .build();
    }

    public CommonResponse getPetProfileByPetId(Integer id) {
        PetProfileDTO pet = petProfileRepository.getProfileById(id);
        List<IBasicInfo> medicines = petProfileRepository.getMedicinesByProfile(id);
        List<IBasicInfo> checkups = petProfileRepository.getCheckupsByProfile(id);
        List<IBasicInfo> diseases = petProfileRepository.getDiseasesByProfile(id);
        if (pet == null) {
            return CommonResponse.builder()
                    .message("宠物档案不存在")
                    .code(1)
                    .build();
        }
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(getPetProfileVO(pet, medicines, checkups, diseases))
                .build();
    }

    private PetProfileVO getPetProfileVO(PetProfileDTO pet, List<IBasicInfo> medicines, List<IBasicInfo> checkups, List<IBasicInfo> diseases) {
        return PetProfileVO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .gender(pet.getGender() ? "公" : "母")
                .images(pet.getImages())
                .weight(pet.getWeight())
                .description(pet.getDescription())
                .type(pet.getType())
                .age(getPetAge(pet.getBirthday()))
                .checkups(checkups)
                .diseases(diseases)
                .medicines(medicines)
                .build();
    }

    @Transactional
    public CommonResponse deletePetProfileByPetId(Integer id) {
        Optional<Pet> petOptional = petProfileRepository.findById(id);
        if (!petOptional.isPresent()) {
            return CommonResponse.builder()
                    .message("宠物档案不存在")
                    .code(1)
                    .build();
        }
        petProfileRepository.deleteAllChecks(id);
        petProfileRepository.deleteAllMedicines(id);
        petProfileRepository.deleteAllDiseases(id);
        petProfileRepository.deletePet(id);
        searchUtil.delete(SearchEntityConverter.getSearchableEntity(petOptional.get()));
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .build();
    }

    public CommonResponse getAllPetProfiles(Integer offset, String content) {
        if (content.isEmpty()) {
            return getPetProfiles(offset);
        }
        return searchPetProfiles(offset, content);
    }

    private CommonResponse searchPetProfiles(Integer offset, String content) {
        List<PetProfileListVO> searchResult = null;
        try {
            Result result = searchUtil.search(content, "pet_profile", offset - 1).get();
            List<SearchableEntity> list = result.getSearchableEntityList();
            searchResult = new ArrayList<>(SearchEntityConverter.getPetProfilesFromSearchableEntity(list));
            PetProfileInfo pageInfo = PetProfileInfo.builder()
                    .currentPage(offset)
                    .totalPages((int) Math.ceil(result.getTotalCount().doubleValue() / 10.0))
                    .petProfiles(searchResult)
                    .build();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(pageInfo)
                    .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CommonResponse getPetProfiles(Integer offset) {
        if (offset == 0) {
            List<PetProfileListDTO> allPetProfiles = petProfileRepository.getAllPetProfiles();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(getPetProfileListVO(allPetProfiles))
                    .build();
        }
        Integer count = petProfileRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        Pageable pageable = PageRequest.of(offset, 10);
        List<PetProfileListDTO> petProfiles = petProfileRepository.findPetProfiles(pageable).get().collect(Collectors.toList());
        PetProfileInfo pageInfo = null;
        pageInfo = PetProfileInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .petProfiles(getPetProfileListVO(petProfiles))
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }

    private long getPetAge(Date birthday) {
        long birthdayTime = birthday.getTime();
        long now = System.currentTimeMillis();
        long ageInMillis = (long) (365.25 * 24 * 60 * 60 * 1000);
        return (now - birthdayTime) / ageInMillis;
    }

    private List<PetProfileListVO> getPetProfileListVO(List<PetProfileListDTO> dtoList) {
        List<PetProfileListVO> result = new ArrayList<>();
        dtoList.forEach(dto -> {
            result.add(PetProfileListVO.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .type(dto.getType())
                    .gender(dto.getGender() ? "公" : "母")
                    .age(getPetAge(dto.getBirthday()))
                    .diseases(dto.getDiseases())
                    .build());
        });
        return result;

    }
}
