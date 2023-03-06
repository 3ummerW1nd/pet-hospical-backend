package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
//说明接口文件
@Api(value = "测试接口", tags = "test")
public class TestController {

    @GetMapping(value = "/test")
    @ApiOperation(value = "test")
    public String test(){
        return "success";
    }

    @PostMapping(value = "/upload")
    @ApiOperation(value = "upload")
    public String upload(String path){
        return FileUtil.upload(path);
    }

    @GetMapping(value = "/download")
    @ApiOperation(value = "download")
    public void download(String blobPath, String targetPath){
        FileUtil.download(blobPath, targetPath);
    }

}
