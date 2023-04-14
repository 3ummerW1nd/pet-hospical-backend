package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.DepartmentService;
import com.example.pethospitalbackend.service.PersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/department")
@Api(value = "科室管理", tags = "departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @AdminMethod
    @ApiOperation(value = "创建医疗器材")
    @RequestMapping(value = "/addOneEquipment", method = RequestMethod.POST)
    public CommonResponse addOneEquipment(@RequestParam("name") String name,
                                           @RequestParam("functions") String functions,
                                           String process) {
        return departmentService.createEquipment(name, functions, process);
    }

    @ApiOperation(value = "获取医疗器材")
    @RequestMapping(value = "/getOneEquipment", method = RequestMethod.GET)
    public CommonResponse getOneEquipment(@RequestParam("name") String name) {
        return departmentService.getEquipmentByName(name);
    }

    @AdminMethod
    @ApiOperation(value = "创建科室")
    @RequestMapping(value = "/addOneDepartment", method = RequestMethod.POST)
    public CommonResponse addOneDepartment(@RequestParam("name") String name,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("directorId") Integer directorId,
                                          @RequestParam("functions") String functions) {
        return departmentService.createOrUpdateDepartment(null, directorId, name, phoneNumber, functions);
    }

    @AdminMethod
    @ApiOperation(value = "更新科室")
    @RequestMapping(value = "/updateOneDepartment", method = RequestMethod.POST)
    public CommonResponse updateOneDepartment(@RequestParam("id") Integer id,
                                             String name,
                                             String phoneNumber,
                                             Integer directorId,
                                             String functions) {
        return departmentService.createOrUpdateDepartment(id, directorId, name, phoneNumber, functions);
    }

    @ApiOperation(value = "获取科室列表")
    @RequestMapping(value = "/getAllDepartments", method = RequestMethod.GET)
    public CommonResponse getAllDepartments(@RequestParam("currentPage") Integer currentPage, String content) {
        return departmentService.getAllDepartments(currentPage, content);
    }

    @AdminMethod
    @ApiOperation(value = "根据id删除科室")
    @RequestMapping(value = "/deleteOneDepartment", method = RequestMethod.POST)
    public CommonResponse deleteOneDepartment(@RequestParam("id") Integer id) {
        return departmentService.deleteDepartmentById(id);
    }

    @ApiOperation(value = "根据名称查找科室")
    @RequestMapping(value = "/getOneDepartment", method = RequestMethod.GET)
    public CommonResponse getOneDepartment(@RequestParam("name") String name) {
        return departmentService.getDepartmentByName(name);
    }
}
