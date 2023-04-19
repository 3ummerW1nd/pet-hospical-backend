package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.LearnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/learn")
//说明接口文件
@Api(value = "职能学习", tags = "learn")
public class LearnController {
    @Autowired
    private LearnService learnService;

    @ApiOperation(value = "计算检查价格")
    @RequestMapping(value = "/getTotalPrice", method = RequestMethod.POST)
    public CommonResponse getTotalPrice(@RequestParam("examination") Integer[] e_ids,
                                        @RequestParam("treatment") Integer[] t_ids) {
        return learnService.getTotalPrice(t_ids,e_ids);
    }

}
