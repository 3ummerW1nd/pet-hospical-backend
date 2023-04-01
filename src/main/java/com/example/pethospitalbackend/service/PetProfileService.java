package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.page.PetProfileInfo;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.profile.PetProfileVO;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.PetProfileRepository;
import com.example.pethospitalbackend.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PetProfileService {
    @Autowired
    private PetProfileRepository petProfileRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .build();
    }


    public CommonResponse getPetProfileByPetId(Integer id) {
        Pet pet = petProfileRepository.findById(id).get();
        if (pet == null) {
            return CommonResponse.builder()
                    .message("宠物档案不存在")
                    .code(1)
                    .build();
        }
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(getPetProfileVO(pet))
                .build();
    }

    @Transactional
    public CommonResponse deletePetProfileByPetId(Integer id) {
        if (!petProfileRepository.existsById(id)) {
            return CommonResponse.builder()
                    .message("宠物档案不存在")
                    .code(1)
                    .build();
        }
        petProfileRepository.deleteById(id);
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
        return null;
    }

    private CommonResponse getPetProfiles(Integer offset) {
        if (offset == 0) {
            List<Pet> allPetProfiles = petProfileRepository.getAllPets();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(allPetProfiles)
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
        List<Pet> petProfiles = petProfileRepository.findPetProfiles(10, offset * 10);
        List<PetProfileVO> result = new ArrayList<>();
        petProfiles.forEach(pet -> {
            result.add(getPetProfileVO(pet));
        });
        PetProfileInfo pageInfo = null;
        pageInfo = PetProfileInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .petProfiles(result)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }

    private PetProfileVO getPetProfileVO(Pet pet) {
        Set<BasicInfo> checkupBasic = new HashSet<>();
        Set<BasicInfo> diseaseBasic = new HashSet<>();
        Set<BasicInfo> medicineBasic = new HashSet<>();
        pet.getCheckups().forEach(checkup -> {
            checkupBasic.add(new BasicInfo(checkup.getId(), checkup.getName()));
        });
        pet.getMedicines().forEach(medicine -> {
            medicineBasic.add(new BasicInfo(medicine.getId(), medicine.getName()));
        });
        pet.getDiseases().forEach(diseaseType -> {
            diseaseBasic.add(new BasicInfo(diseaseType.getId(), diseaseType.getName()));
        });
        return PetProfileVO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .gender(pet.getGender() ? "公" : "母")
                .weight(pet.getWeight())
                .description(pet.getDescription())
                .type(pet.getType())
                .checkups(checkupBasic)
                .diseases(diseaseBasic)
                .medicines(medicineBasic)
                .build();
    }

}
