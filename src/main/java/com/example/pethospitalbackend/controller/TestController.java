package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.util.FileUtil;
import com.microsoft.azure.storage.blob.BlobInputStream;
import com.microsoft.azure.storage.blob.BlobOutputStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
