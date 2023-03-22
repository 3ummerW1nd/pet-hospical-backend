package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.annotation.NoLoginMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.domain.user.UserRole;
import com.example.pethospitalbackend.service.UserService;
import com.example.pethospitalbackend.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
//说明接口文件
@Api(value = "用户管理", tags = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @NoLoginMethod
    @ApiOperation(value = "注册新用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResponse register(@RequestParam("name") String name,
                                   @RequestParam("phoneNumber") String phoneNumber,
                                   @RequestParam("password") String password) {
        return userService.register(name, phoneNumber, password);
    }

    @NoLoginMethod
    @ApiOperation(value = "后台用户登陆")
    @RequestMapping(value = "/loginBackManage", method = RequestMethod.POST)
    public CommonResponse loginBack(@RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("password") String password) {
        return userService.login(phoneNumber, password, true);
    }

    @NoLoginMethod
    @ApiOperation(value = "前台用户登陆")
    @RequestMapping(value = "/loginFrontLearn", method = RequestMethod.POST)
    public CommonResponse loginFront(@RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("password") String password) {
        return userService.login(phoneNumber, password, false);
    }

    @AdminMethod
    @ApiOperation(value = "用户列表")
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public CommonResponse getAllUsers(@RequestParam("currentPage") Integer currentPage, @RequestParam("content") String content) {
        return userService.getAllUsers(currentPage, content);
    }

    @AdminMethod
    @ApiOperation(value = "删除指定用户")
    @RequestMapping(value = "/deleteOneUser", method = RequestMethod.POST)
    public CommonResponse deleteOneUser(Integer id) {
        return userService.deleteUserById(id);
    }


    @ApiOperation(value = "更新指定用户")
    @RequestMapping(value = "/updateOneUser", method = RequestMethod.POST)
    public CommonResponse updateOneUser(@RequestHeader("Authorization") String token, Integer id, String name, Boolean role, Integer level) {
        UserRole userRole = TokenUtil.getUserRoleFromToken(token);
        return userService.updateUserById(id, name, role, level, userRole);
    }
}
