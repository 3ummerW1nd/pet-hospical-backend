package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Equipment;
import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.department.DepartmentVO;
import com.example.pethospitalbackend.domain.department.DepartmentVOEntity;
import com.example.pethospitalbackend.domain.page.DepartmentPageInfo;
import com.example.pethospitalbackend.domain.page.MedicinePageInfo;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.DepartmentRepository;
import com.example.pethospitalbackend.repository.EquipmentRepository;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import com.example.pethospitalbackend.search.converter.SearchEntityConverter;
import com.example.pethospitalbackend.search.entity.Result;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import com.example.pethospitalbackend.util.FileUtil;
import com.example.pethospitalbackend.util.JsonUtil;
import com.example.pethospitalbackend.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private SearchUtil searchUtil;

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse createEquipment(String name, String functions, String process) {
        Equipment equipment = Equipment.builder()
                .functions(functions)
                .name(name)
                .process(JsonUtil.parseJson(process))
                .video("")
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
        if (equipment == null) {
            return CommonResponse.builder()
                    .code(1)
                    .message("该器材不存在")
                    .build();
        }
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
                        .director(personnelRepository.findById(directorId).get())
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
                    .director(personnelRepository.findById(directorId).get())
                    .name(name)
                    .functions(functions)
                    .phoneNumber(phoneNumber)
                    .build();
        }
        departmentRepository.save(department);
        return CommonResponse.builder()
                .code(0)
                .message("success")
//                .result(department)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("部门不存在，请检查id")
                    .build();
        }
        Department department = optionalDepartment.get();
        departmentRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(department)
                .build();
    }

    public CommonResponse getAllDepartments(Integer offset, String content) {
        if (content.isEmpty()) {
            return getDepartments(offset);
        }
        return searchDepartments(offset, content);
    }

    private CommonResponse searchDepartments(Integer offset, String content) {
        List<DepartmentVOEntity> searchResult = null;
        try {
            Result result = searchUtil.search(content, "department", offset - 1).get();
            List<SearchableEntity> list = result.getSearchableEntityList();
            searchResult = new ArrayList<>(SearchEntityConverter.getDepartmentsFromSearchableEntity(list));
            DepartmentPageInfo pageInfo = DepartmentPageInfo.builder()
                    .currentPage(offset)
                    .totalPages((int) Math.ceil(result.getTotalCount().doubleValue() / 10.0))
                    .departments(searchResult)
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

    private CommonResponse getDepartments(Integer offset) {
        if (offset == 0) {
            List<DepartmentVO> allDepartments = departmentRepository.findDepartments();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(allDepartments)
                    .build();
        }
        Integer count = departmentRepository.getPageCount(10);
        if (offset < 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        Pageable pageable = PageRequest.of(offset, 10);
        List<DepartmentVO> departmentList = departmentRepository.findDepartments(pageable).get().collect(Collectors.toList());
        // 10, offset * 10
        DepartmentPageInfo pageInfo = DepartmentPageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .departments(departmentList)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }

    public CommonResponse getDepartmentByName(String name) {
        DepartmentVO department = departmentRepository.findDepartmentVOByName(name);
        if (department == null) {
            return CommonResponse.builder()
                    .message("该科室不存在")
                    .code(1)
                    .build();
        }
        return CommonResponse.builder()
                .message("success")
                .code(0)
                .result(department)
                .build();
    }
}
