package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.CheckupService;
import com.example.pethospitalbackend.service.MedicineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkup")
@Api(value = "检查管理", tags = "checkups")
public class CheckupController {
    @Autowired
    private CheckupService checkupService;

    @AdminMethod
    @ApiOperation(value = "创建检查")
    @RequestMapping(value = "/addOneCheckup", method = RequestMethod.POST)
    public CommonResponse addOneCheckup(@RequestParam("name") String name,
                                          @RequestParam("introduction") String introduction,
                                          @RequestParam("price") Double price) {
        return checkupService.createOrUpdateCheckup(null, name, introduction, price);
    }

    @AdminMethod
    @ApiOperation(value = "更新检查")
    @RequestMapping(value = "/updateOneCheckup", method = RequestMethod.POST)
    public CommonResponse updateOneCheckup(@RequestParam("id") Integer id,
                                             String name,
                                             String introduction,
                                             Double price) {
        return checkupService.createOrUpdateCheckup(id, name, introduction, price);
    }

    @ApiOperation(value = "获取检查项目列表")
    @RequestMapping(value = "/getAllCheckups", method = RequestMethod.GET)
    public CommonResponse getAllCheckups(@RequestParam("currentPage") Integer currentPage, String content) {
        return checkupService.getAllCheckups(currentPage, content);
    }

    @AdminMethod
    @ApiOperation(value = "根据id删除药品")
    @RequestMapping(value = "/deleteOneCheckup", method = RequestMethod.POST)
    public CommonResponse deleteOneCheckup(@RequestParam("id") Integer id) {
        return checkupService.deleteCheckupById(id);
    }
}
