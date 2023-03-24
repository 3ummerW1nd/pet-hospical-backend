package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.PageInfo;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;


    public CommonResponse createOrUpdatePersonnel(Integer id, String name, Boolean gender, String phoneNumber, String duty, String department) {
        Personnel personnel = null;
        if (id != null) {
            personnel = Personnel.builder()
                    .id(id)
                    .department(department)
                    .duty(duty)
                    .gender(gender)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .build();
        } else {
            personnel = Personnel.builder()
                    .department(department)
                    .duty(duty)
                    .gender(gender)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .build();
        }
        personnelRepository.save(personnel);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(personnel)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deletePersonnelById(Integer id) {
        Optional<Personnel> optionalPersonnel = personnelRepository.findById(id);
        if (!optionalPersonnel.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("工作人员不存在，请检查id")
                    .build();
        }
        Personnel personnel = optionalPersonnel.get();
        personnelRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(personnel)
                .build();
    }

    public CommonResponse getAllPersonnels(Integer offset, String content) {
        Integer count = personnelRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Personnel> allPersonnels = null;
        if (content == null || content.isEmpty()) {
            allPersonnels = personnelRepository.findPersonnels(10, offset * 10);
        } else {
            allPersonnels = personnelRepository.findPersonnels(10, offset * 10);
            //todo:搜索
        }
        PageInfo pageInfo = null;
        pageInfo = PageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .data(allPersonnels)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
