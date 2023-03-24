package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.PetProfile;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.PetProfileRepository;
import com.example.pethospitalbackend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PetProfileService {
    @Autowired
    private PetProfileRepository petProfileRepository;

    public CommonResponse addPetProfile(String name, List<MultipartFile> images, String type, Date birthday,
                                        Double weight, String medicalHistory, String allerfyHistory, String bloodType) {
        if(name.length() > 20 || type.length() > 10 || medicalHistory.length() > 2000 ||
                allerfyHistory.length() > 2000 || bloodType.length() > 10) {
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
        StringBuilder fileNameStringBuilder = new StringBuilder();
        List<MultipartFile> realImages = images.stream().filter(FileUtil::isImage).collect(Collectors.toList());
        realImages.forEach(image -> {
            fileNameStringBuilder.append(FileUtil.upload(image)).append(',');
        });
        PetProfile petProfile = PetProfile.builder()
                .name(name)
                .allerfyHistory(allerfyHistory)
                .birthday(birthday)
                .bloodType(bloodType)
                .weight(weight)
                .medicalHistory(medicalHistory)
                .images(fileNameStringBuilder.toString())
                .type(type)
                .build();
        petProfileRepository.save(petProfile);
        return CommonResponse.builder()
                .code(0)
                .message("创建宠物档案成功")
                .result(petProfile)
                .build();
    }

}
