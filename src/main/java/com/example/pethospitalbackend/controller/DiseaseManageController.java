package com.example.pethospitalbackend.controller;

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
                                         @RequestParam("image") MultipartFile[] image,
                                         @RequestParam("video") MultipartFile[] video,
                                         @RequestParam("image_description") String[] image_description,
                                         @RequestParam("video_description") String[] video_description) {
        String image_ids = "";
        for (int i = 0; i < image.length; i++) {
            MultipartFile image_i = image[i];
            String image_id = fileUtil.upload(image_i);
            Media media1 = new Media(image_id,image_description[i]);
            mediaRepository.save(media1);
            image_ids += image_id + ",";
        }
        String video_ids = "";
        for (int i = 0; i < video.length; i++) {
            MultipartFile vidoe_i = video[i];
            String video_id = fileUtil.upload(vidoe_i);
            Media media2 = new Media(video_id,video_description[i]);
            mediaRepository.save(media2);
            video_ids += video_id + ",";
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
    public CommonResponse modifyOneQuestion(@RequestParam("disease_type_id") Integer disease_type_id,
                                         @RequestParam("symptom") String symptom,
                                         @RequestParam("examination") String examination,
                                         @RequestParam("diagnosis") String diagnosis,
                                         @RequestParam("treatment") String treatment,
                                         @RequestParam("image") MultipartFile[] image,
                                         @RequestParam("video") MultipartFile[] video,
                                         @RequestParam("image_description") String[] image_description,
                                         @RequestParam("video_description") String[] video_description) {
        String image_ids = "";
        for (int i = 0; i < image.length; i++) {
            MultipartFile image_i = image[i];
            String image_id = fileUtil.upload(image_i);
            Media media1 = new Media(image_id,image_description[i]);
            mediaRepository.save(media1);
            image_ids += image_id + ",";
        }
        String video_ids = "";
        for (int i = 0; i < video.length; i++) {
            MultipartFile vidoe_i = video[i];
            String video_id = fileUtil.upload(vidoe_i);
            Media media2 = new Media(video_id,video_description[i]);
            mediaRepository.save(media2);
            video_ids += video_id + ",";
        }
        return diseaseManage.modifyOneDisease(disease_type_id,symptom,examination,diagnosis,treatment,image_ids,video_ids);
    }

    @AdminMethod
    @ApiOperation(value = "获取单个病例详情")
    @RequestMapping(value = "/getOneDisease", method = RequestMethod.GET)
    public CommonResponse getOneDisease(@RequestParam("disease_type_id") Integer disease_id) {
        return diseaseManage.getOneDisease(disease_id);
    }

    @AdminMethod
    @ApiOperation(value = "试题搜索")
    @RequestMapping(value = "/searchDisease", method = RequestMethod.POST)
    public CommonResponse searchDisease(@RequestParam("disease_type") String disease_type,
                                         @RequestParam("search_text") String search_text,
                                         @RequestParam("currentPage") Integer cur) {
        return diseaseManage.searchDisease(disease_type,search_text,cur);
    }
}
