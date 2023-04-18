package com.example.pethospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.Media;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.MediaRepository;
import com.example.pethospitalbackend.service.DiseaseManage;
import com.example.pethospitalbackend.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/diseaseManage")
//说明接口文件
@Api(value = "病例展示管理", tags = "diseaseManage")
public class DiseaseManageController {
    @Autowired
    private DiseaseManage diseaseManage;
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private FileUtil fileUtil;

    @AdminMethod
    @ApiOperation(value = "获取所有大病")
    @RequestMapping(value = "/getBigDisease", method = RequestMethod.GET)
    public CommonResponse getBigDisease() {
        return diseaseManage.getBigDisease();
    }

    @AdminMethod
    @ApiOperation(value = "获取所有小病")
    @RequestMapping(value = "/getDiseaseTypes", method = RequestMethod.GET)
    public CommonResponse getDiseaseTypes() {
        return diseaseManage.getDiseaseTypes();
    }

    @AdminMethod
    @ApiOperation(value = "添加单个病例")
    @RequestMapping(value = "/addOneDisease", method = RequestMethod.POST)
    public CommonResponse addOneQuestion(@RequestParam("disease_type") String disease_type,
                                         @RequestParam("disease_name") String disease_name,
                                         @RequestParam("symptom") String symptom,
                                         @RequestParam("examination") String examination,
                                         @RequestParam("diagnosis") String diagnosis,
                                         @RequestParam("treatment") String treatment,
                                         @RequestParam(value = "file_items",required = false) JSONObject[] files) {
        String image_ids = "";
        String video_ids = "";
        if(files != null){
            for (int i = 0; i < files.length; i++) {
                JSONObject file = files[i];
                String f_id = file.getString("url");
                String type = file.getString("type");
                String description = file.getString("description");
                if(type.equals("image")) {
                    image_ids += f_id + ",";
                    Media media1 = new Media(f_id,description);
                    mediaRepository.save(media1);
                }
                else {
                    video_ids += f_id + ",";
                    Media media1 = new Media(f_id,description);
                    mediaRepository.save(media1);
                }
            }
        }
        return diseaseManage.addOneDisease(disease_type,disease_name,symptom,examination,diagnosis,treatment,image_ids,video_ids);
    }

    @AdminMethod
    @ApiOperation(value = "删除单个病例")
    @RequestMapping(value = "/deleteOneDisease", method = RequestMethod.GET)
    public CommonResponse deleteOneDisease(@RequestParam("disease_type_id") Integer disease_id) {
        return diseaseManage.deleteOneDisease(disease_id);
    }

    @AdminMethod
    @ApiOperation(value = "修改单个病例")
    @RequestMapping(value = "/modifyOneDisease", method = RequestMethod.POST)
    public CommonResponse modifyOneQuestion(@RequestParam("disease_type_id") Integer disease_id,
                                            @RequestParam("disease_type") String disease_type_name,
                                            @RequestParam("disease_name") String disease_name,
                                            @RequestParam("symptom") String symptom,
                                            @RequestParam("examination") String examination,
                                            @RequestParam("diagnosis") String diagnosis,
                                            @RequestParam("treatment") String treatment,
                                            @RequestParam(value = "file_items",required = false) JSONObject[] files
//                                            @RequestParam("image") MultipartFile[] image,
//                                            @RequestParam("video") MultipartFile[] video,
//                                            @RequestParam("image_description") String[] image_description,
//                                            @RequestParam("video_description") String[] video_description
                                            ) {

        String image_ids = "";
        String video_ids = "";
        if(files != null){
            for (int i = 0; i < files.length; i++) {
                JSONObject file = files[i];
                String f_id = file.getString("url");
                String type = file.getString("type");
                String description = file.getString("description");
                if(type.equals("image")) {
                    image_ids += f_id + ",";
                    Media media1 = new Media(f_id,description);
                    mediaRepository.save(media1);
                }
                else {
                    video_ids += f_id + ",";
                    Media media1 = new Media(f_id,description);
                    mediaRepository.save(media1);
                }
            }
        }

        return diseaseManage.modifyOneDisease(disease_id,disease_type_name,disease_name,symptom,examination,diagnosis,treatment,image_ids,video_ids);
    }

    //@AdminMethod
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public CommonResponse uploadFile(@RequestParam("file") MultipartFile[] files) {

        List<JSONObject> infos = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            JSONObject info = new JSONObject();
            MultipartFile file = files[i];
            String f_id = fileUtil.upload(file);
            info.put("file_url",f_id);
            String fname = file.getOriginalFilename();
            String type = fname.substring(fname.lastIndexOf('.') + 1).toLowerCase();
            if (type.equals("jpg") || type.equals("gif") || type.equals("png") || type.equals("bmp") )
                info.put("file_type","image");
            else
                info.put("file_type","image");
            infos.add(info);
        }
        JSONObject object = new JSONObject();
        object.put("file_info",infos);

        return CommonResponse.builder().result(object).message("上传成功").code(0).build();
    }

    @AdminMethod
    @ApiOperation(value = "获取单个病例详情")
    @RequestMapping(value = "/getOneDisease", method = RequestMethod.GET)
    public CommonResponse getOneDisease(@RequestParam("disease_id") Integer disease_id) {
        return diseaseManage.getOneDisease(disease_id);
    }

    @AdminMethod
    @ApiOperation(value = "病例搜索")
    @RequestMapping(value = "/searchDisease", method = RequestMethod.POST)
    public CommonResponse searchDisease(@RequestParam("disease_type") String disease_type,
                                        @RequestParam("search_text") String search_text,
                                        @RequestParam("currentPage") Integer cur) {
        return diseaseManage.searchDisease(disease_type,search_text,cur);
    }
}
