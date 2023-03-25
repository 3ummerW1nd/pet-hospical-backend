package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.MedicineService;
import com.example.pethospitalbackend.service.PersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicine")
@Api(value = "药品管理", tags = "medicines")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @AdminMethod
    @ApiOperation(value = "创建药品")
    @RequestMapping(value = "/addOneMedicine", method = RequestMethod.POST)
    public CommonResponse addOnePersonnel(@RequestParam("name") String name,
                                          @RequestParam("introduction") String introduction,
                                          @RequestParam("price") Double price,
                                          @RequestParam("quantity") Integer quantity) {
        return medicineService.createOrUpdateMedicine(null, name, introduction, price, quantity);
    }

    @AdminMethod
    @ApiOperation(value = "更新药品")
    @RequestMapping(value = "/updateOneMedicine", method = RequestMethod.POST)
    public CommonResponse updateOnePersonnel(@RequestParam("id") Integer id,
                                             @RequestParam("name") String name,
                                             @RequestParam("introduction") String introduction,
                                             @RequestParam("price") Double price,
                                             @RequestParam("quantity") Integer quantity) {
        return medicineService.createOrUpdateMedicine(id, name, introduction, price, quantity);
    }

    @ApiOperation(value = "获取药品列表")
    @RequestMapping(value = "/getAllMedicines", method = RequestMethod.GET)
    public CommonResponse getAllMedicines(@RequestParam("currentPage") Integer currentPage, @RequestParam("content") String content) {
        return medicineService.getAllMedicines(currentPage, content);
    }

    @AdminMethod
    @ApiOperation(value = "根据id删除药品")
    @RequestMapping(value = "/deleteOneMedicine", method = RequestMethod.POST)
    public CommonResponse deleteOnePersonnel(@RequestParam("id") Integer id) {
        return medicineService.deleteMedicineById(id);
    }
}
