package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
//说明接口文件
@Api(value = "测试接口", tags = "test")
public class TestController {

    @GetMapping(value = "/test")
    @ApiOperation(value = "test")
    public CommonResponse test(){
        return CommonResponse.builder().code(0000).message("success").data("success").build();
    }

    @PostMapping(value = "/upload")
    @ApiOperation(value = "upload")
    public CommonResponse upload(MultipartFile file){
        return CommonResponse.builder().code(0000).message("success").data(FileUtil.upload(file)).build();
    }
}
