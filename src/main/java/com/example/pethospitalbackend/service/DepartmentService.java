package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Department;
import com.example.pethospitalbackend.domain.Equipment;
import com.example.pethospitalbackend.domain.PageInfo;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.DepartmentRepository;
import com.example.pethospitalbackend.repository.EquipmentRepository;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import com.example.pethospitalbackend.util.FileUtil;
import com.example.pethospitalbackend.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse createEquipment(String name, String functions, MultipartFile video, String process) {
//        if (!FileUtil.isVideo(video)) {
//            return CommonResponse.builder()
//                    .code(1)
//                    .message("请上传视频文件")
//                    .build();
//        }

        String videoPath = FileUtil.upload(video);
        Equipment equipment = Equipment.builder()
                .functions(functions)
                .name(name)
                .process(JsonUtil.parseJson(process))
                .video(videoPath)
                .build();
        equipmentRepository.save(equipment);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(equipment)
                .build();
    }

    public CommonResponse getEquipmentByName(String name) {
        Equipment equipment = equipmentRepository.findEquipmentByName(name);
        return CommonResponse.builder()
                .result(equipment)
                .code(0)
                .message("success")
                .build();
    }


    @Transactional(rollbackFor = Exception.class)
    public CommonResponse createOrUpdateDepartment(Integer id, Integer directorId, String name, String phoneNumber, String functions) {
        Department oldDepartment = departmentRepository.findDepartmentByName(name);
        Department department = null;
        if (id != null) {
            Boolean exist = departmentRepository.existsById(id);
            if (!exist) {
                return CommonResponse.builder()
                        .code(1)
                        .message("id不存在，请检查")
                        .build();
            }
            if (oldDepartment == null || oldDepartment.getId().equals(id)) {
                department = Department.builder()
                        .id(id)
                        .directorId(directorId)
                        .name(name)
                        .functions(functions)
                        .phoneNumber(phoneNumber)
                        .build();
            } else {
                return CommonResponse.builder()
                        .code(1)
                        .message("科室名已存在，请检查")
                        .build();
            }
        } else {
            if (oldDepartment != null) {
                return CommonResponse.builder()
                        .code(1)
                        .message("科室名已存在，请检查")
                        .build();
            }
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

    public CommonResponse getDepartmentByName(String name) {
        Department department = departmentRepository.findDepartmentByName(name);
        return CommonResponse.builder()
                .message("success")
                .code(0)
                .result(department)
                .build();
    }
}
