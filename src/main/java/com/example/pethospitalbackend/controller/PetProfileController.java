package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.PetProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/petProfile")
//说明接口文件
@Api(value = "宠物病例管理", tags = "petProfiles")
public class PetProfileController {

    @Autowired
    private PetProfileService petProfileService;

    @AdminMethod
    @ApiOperation(value = "创建宠物病例")
    @RequestMapping(value = "/addOnePetProfile", method = RequestMethod.POST)
    public CommonResponse addOnePetProfile(@RequestParam("name") String name,
                                           @RequestParam("type") String type,
                                           @RequestParam("gender") Boolean gender,
                                           @RequestParam("birthday") String birthday,
                                           @RequestParam("weight") Double weight,
                                           @RequestParam("description") String description,
                                           @RequestParam("medicines") List<Integer> medicines,
                                           @RequestParam("checkups") List<Integer> checkups,
                                           @RequestParam("diseases") List<Integer> diseases,
                                           @RequestParam("images") List<MultipartFile> images) {
        return petProfileService.createPetProfile(name, type, gender, birthday, weight, description, medicines, checkups, diseases, images);
    }

    @ApiOperation(value = "根据id获取宠物病例")
    @RequestMapping(value = "/getOnePetProfile", method = RequestMethod.GET)
    public CommonResponse getOnePetProfile(@RequestParam("id") Integer id) {
        return petProfileService.getPetProfileByPetId(id);
    }

}
