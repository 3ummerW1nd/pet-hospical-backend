package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.util.FileUtil;
import com.microsoft.azure.storage.blob.BlobInputStream;
import com.microsoft.azure.storage.blob.BlobOutputStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    public String test(){
        return "success";
    }

    @PostMapping(value = "/upload")
    @ApiImplicitParam(name = "file", value = "单个文件", required = true, dataType = "__file")
    @ApiOperation(value = "upload")
    public String upload(File file){
        return FileUtil.upload(file);
    }

    @GetMapping(value = "/download")
    @ApiOperation(value = "download")
    public void download(String blobPath, HttpServletResponse response) throws IOException {
        BlobInputStream blobInputStream = FileUtil.download(blobPath);
        OutputStream os = response.getOutputStream();
        byte[] bytes = new byte[blobInputStream.available()];
        blobInputStream.read(bytes);
        // 重置 response
        response.reset();
        // 设置 response 的下载响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(blobPath, "UTF-8"));  // 注意，这里要设置文件名的编码，否则中文的文件名下载后不显示
        // 写出字节数组到输出流
        os.write(bytes);
        // 刷新输出流
        os.flush();
    }

}
