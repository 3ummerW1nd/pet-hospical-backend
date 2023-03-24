package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Department;
import com.example.pethospitalbackend.domain.PageInfo;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.DepartmentRepository;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;


    public CommonResponse createOrUpdateDepartment(Integer id, Integer directorId, String name, String phoneNumber, String functions) {
        Department department = null;
        if (id != null) {
            department = Department.builder()
                    .id(id)
                    .directorId(directorId)
                    .name(name)
                    .functions(functions)
                    .phoneNumber(phoneNumber)
                    .build();
        } else {
            department = Department.builder()
                    .directorId(directorId)
                    .name(name)
                    .functions(functions)
                    .phoneNumber(phoneNumber)
                    .build();
        }
        departmentRepository.save(department);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(department)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteDepartmentById(Integer id) {
        Optional<Department> optionalPersonnel = departmentRepository.findById(id);
        if (!optionalPersonnel.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("部门不存在，请检查id")
                    .build();
        }
        Department department = optionalPersonnel.get();
        departmentRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(department)
                .build();
    }

    public CommonResponse getAllDepartments(Integer offset, String content) {
        Integer count = departmentRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Department> departments = null;
        if (content == null || content.isEmpty()) {
            departments = departmentRepository.findDepartments(10, offset * 10);
        } else {
            departments = departmentRepository.findDepartments(10, offset * 10);
            //todo:搜索
        }
        PageInfo pageInfo = null;
        pageInfo = PageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .data(departments)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
