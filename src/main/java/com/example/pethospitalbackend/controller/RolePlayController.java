package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.RolePlay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/rolePlay")
//说明接口文件
@Api(value = "角色扮演", tags = "rolePlay")
public class RolePlayController {
    @Autowired
    private RolePlay rolePlay;

    @ApiOperation(value = "获取所有角色")
    @RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
    public CommonResponse getAllRoles() {
        return rolePlay.getAllRoles();
    }

    @ApiOperation(value = "获取角色的Action")
    @RequestMapping(value = "/getAllActions", method = RequestMethod.GET)
    public CommonResponse getAllActions(@RequestParam("role_type") String role_type) {
        return rolePlay.getAllActions(role_type);
    }

    @ApiOperation(value = "获取角色的Action")
    @RequestMapping(value = "/getNextStep", method = RequestMethod.GET)
    public CommonResponse getNextStep(@RequestParam("procedure_id") Integer procedure_id,
                                      @RequestParam("curr_step") Integer curr_step) {
        return rolePlay.getNextStep(procedure_id,curr_step);
    }
}
