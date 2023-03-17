package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.NoLoginMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.UserService;
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
    @ApiOperation(value = "用户登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResponse login(@RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("password") String password) {
        return userService.login(phoneNumber, password);
    }

    @NoLoginMethod
    @ApiOperation(value = "用户列表")
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public CommonResponse getAllUsers(@RequestParam("offset") Integer offset) {
        return userService.getAllUsers(offset);
    }

}
