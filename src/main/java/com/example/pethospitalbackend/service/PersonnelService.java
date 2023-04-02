package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.personnel.Personnel;
import com.example.pethospitalbackend.domain.page.PersonnelPageInfo;
import com.example.pethospitalbackend.domain.personnel.PersonnelVO;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.DepartmentRepository;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public CommonResponse createOrUpdatePersonnel(Integer id, String name, String genderString, String phoneNumber, String duty, String department) {
        Boolean gender = null;
        if (genderString.equals("男")) {
            gender = true;
        } else if (genderString.equals("女")) {
            gender = false;
        } else {
            return CommonResponse.builder()
                    .code(1)
                    .message("性别请填写\"男\"或\"女\"")
                    .build();
        }
        Personnel personnel = null;
        if (id != null) {
            Boolean exist = personnelRepository.existsById(id);
            if (!exist) {
                return CommonResponse.builder()
                        .code(1)
                        .message("id不存在，请检查")
                        .build();
            }
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
        List<Department> departments = departmentRepository.findDepartmentsByDirectorId(id);
        StringBuilder stringBuilder = new StringBuilder();
        departments.forEach(department -> {
            stringBuilder.append(department.getName() + "，");
        });
        if (!departments.isEmpty()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("工作人员：" + personnel.getName() + "目前正在" + stringBuilder + "担任主管，不可删除")
                    .build();
        }
        personnelRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(personnel)
                .build();
    }

    public CommonResponse getAllPersonnels(Integer offset, String content) {
        if (content.isEmpty()) {
            return getPersonnels(offset);
        }
        return searchPersonnels(offset, content);
    }

    private CommonResponse searchPersonnels(Integer offset, String content) {
        return null;
    }

    private CommonResponse getPersonnels(Integer offset) {
        if (offset == 0) {
            List<Personnel> allPersonnels = (List<Personnel>) personnelRepository.findAll();
            List<PersonnelVO> result = new ArrayList<>();
            allPersonnels.forEach(personnel -> result.add(getPersonnelVO(personnel)));
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(result)
                    .build();
        }
        Integer count = personnelRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Personnel> allPersonnels = personnelRepository.findPersonnels(10, offset * 10);
        List<PersonnelVO> result = new ArrayList<>();
        allPersonnels.forEach(personnel -> result.add(getPersonnelVO(personnel)));
        PersonnelPageInfo pageInfo = PersonnelPageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .personnels(result)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }

    private PersonnelVO getPersonnelVO(Personnel personnel) {
        return PersonnelVO.builder()
        .id(personnel.getId())
        .name(personnel.getName())
        .department(personnel.getDepartment())
        .duty(personnel.getDuty())
        .phoneNumber(personnel.getPhoneNumber())
        .gender(personnel.getGender() ? "男" : "女")
        .build();
    }

}
