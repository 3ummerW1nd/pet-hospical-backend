package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.profile.PetProfileRepository;
import com.example.pethospitalbackend.util.DateUtil;
import com.example.pethospitalbackend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public CommonResponse createPetProfile(String name, String type, Boolean gender, String birthday, Double weight, String description, List<Integer> medicines, List<Integer> checkups, List<Integer> diseases, List<MultipartFile> images) {
        if(name.length() > 20 || type.length() > 10 || description.length() > 2000) {
            return CommonResponse.builder()
                    .code(1)
                    .message("输入文字过长，请检查")
                    .build();
        }
        if (images.size() > 9) {
            return CommonResponse.builder()
                    .code(1)
                    .message("每份宠物档案最多可以附9张图片，请检查")
                    .build();
        }
        // 上传图片
        StringBuilder imageStringBuilder = new StringBuilder();
        images.forEach(image -> imageStringBuilder.append(FileUtil.upload(image) + ","));
        imageStringBuilder.deleteCharAt(imageStringBuilder.length() - 1);

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
                .images(imageStringBuilder.toString())
                .birthday(DateUtil.getDate(birthday))
                .description(description)
                .weight(weight)
                .diseases(diseaseSet)
                .checkups(checkupSet)
                .medicines(medicineSet)
                .build();
        petProfileRepository.save(pet);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .build();
    }


    public CommonResponse getPetProfileByPetId(Integer id) {
        Pet pet = petProfileRepository.getPetById(id);
        if (pet == null) {
            return CommonResponse.builder()
                    .message("宠物档案不存在")
                    .code(1)
                    .build();
        }
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pet)
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
}
