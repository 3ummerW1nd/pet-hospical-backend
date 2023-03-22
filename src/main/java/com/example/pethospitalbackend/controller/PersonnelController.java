package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.annotation.NoLoginMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.PersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personnel")
//说明接口文件
@Api(value = "用户管理", tags = "users")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @AdminMethod
    @ApiOperation(value = "创建工作人员")
    @RequestMapping(value = "/addOnePersonnel", method = RequestMethod.POST)
    public CommonResponse addOnePersonnel(@RequestParam("name") String name,
                                          @RequestParam("gender") Boolean gender,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("duty") String duty,
                                          @RequestParam("department") String department) {
        return personnelService.createOrUpdatePersonnel(null, name, gender, phoneNumber, duty, department);
    }

    @AdminMethod
    @ApiOperation(value = "更新工作人员")
    @RequestMapping(value = "/updateOnePersonnel", method = RequestMethod.POST)
    public CommonResponse updateOnePersonnel(@RequestParam("id") Integer id,
                                             @RequestParam("name") String name,
                                             @RequestParam("gender") Boolean gender,
                                             @RequestParam("phoneNumber") String phoneNumber,
                                             @RequestParam("duty") String duty,
                                             @RequestParam("department") String department) {
        return personnelService.createOrUpdatePersonnel(id, name, gender, phoneNumber, duty, department);
    }

    @ApiOperation(value = "获取工作人员列表")
    @RequestMapping(value = "/getAllPersonnels", method = RequestMethod.GET)
    public CommonResponse getAllPersonnels(@RequestParam("currentPage") Integer currentPage, @RequestParam("content") String content) {
        return personnelService.getAllPersonnels(currentPage, content);
    }

    @AdminMethod
    @ApiOperation(value = "根据id删除工作人员")
    @RequestMapping(value = "/deleteOnePersonnel", method = RequestMethod.POST)
    public CommonResponse deleteOnePersonnel(@RequestParam("id") Integer id) {
        return personnelService.deletePersonnelById(id);
    }

}
