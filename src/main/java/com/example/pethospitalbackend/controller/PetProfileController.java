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
                                           @RequestParam("gender") String gender,
                                           @RequestParam("birthday") String birthday,
                                           @RequestParam("weight") Double weight,
                                           @RequestParam("description") String description,
                                           @RequestParam("medicines") List<Integer> medicines,
                                           @RequestParam("checkups") List<Integer> checkups,
                                           @RequestParam("diseases") List<Integer> diseases) {
        return petProfileService.createOrUpdatePetProfile(null, name, type, gender, birthday, weight, description, medicines, checkups, diseases, null);
    }

    @AdminMethod
    @ApiOperation(value = "更新宠物病例")
    @RequestMapping(value = "/updateOnePetProfile", method = RequestMethod.POST)
    public CommonResponse updateOnePetProfile(@RequestParam("id") Integer id,
                                           String name,
                                           String type,
                                           String gender,
                                           String birthday,
                                           Double weight,
                                           String description,
                                           List<Integer> medicines,
                                           List<Integer> checkups,
                                           List<Integer> diseases) {
        return petProfileService.createOrUpdatePetProfile(id, name, type, gender, birthday, weight, description, medicines, checkups, diseases, null);
    }

    @ApiOperation(value = "根据id获取宠物病例")
    @RequestMapping(value = "/getOnePetProfile", method = RequestMethod.GET)
    public CommonResponse getOnePetProfile(@RequestParam("id") Integer id) {
        return petProfileService.getPetProfileByPetId(id);
    }

    @AdminMethod
    @ApiOperation(value = "根据id删除宠物病例")
    @RequestMapping(value = "/deleteOnePetProfile", method = RequestMethod.GET)
    public CommonResponse deleteOnePetProfile(@RequestParam("id") Integer id) {
        return petProfileService.deletePetProfileByPetId(id);
    }

    @ApiOperation(value = "获取宠物病例列表")
    @RequestMapping(value = "/getAllPetProfiles", method = RequestMethod.GET)
    public CommonResponse getAllPetProfiles( @RequestParam("currentPage") Integer currentPage, String content) {
        return petProfileService.getAllPetProfiles(currentPage, content);
    }

}
