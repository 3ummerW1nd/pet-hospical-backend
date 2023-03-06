package com.example.pethospitalbackend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
//说明接口文件
@Api(value = "测试接口", tags = "test")
public class TestController {

    @PostMapping(value = "/test")
    @ApiOperation(value = "test")
    public String saveUser(){
        return "success";
    }
}
