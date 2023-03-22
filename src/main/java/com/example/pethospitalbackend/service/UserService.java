package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.domain.user.UserInfo;
import com.example.pethospitalbackend.domain.user.UserPageInfo;
import com.example.pethospitalbackend.domain.user.UserRole;
import com.example.pethospitalbackend.repository.UserRepository;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public CommonResponse getAllUsers(Integer offset, String content) {
        if (offset <= 0) {
            return CommonResponse.builder()
                    .code(40001)
                    .message("页号非法，从1开始")
                    .build();
        }
        Integer count = userRepository.getPageCount(10);
        if (offset > count) {
            return CommonResponse.builder()
                    .code(40002)
                    .message("offset过大")
                    .build();
        }
        offset -= 1;
        List<UserInfo> allUsers = null;
        if (content.isEmpty()) {
            allUsers = userRepository.findUsers(10, offset * 10);
        } else {
            allUsers = userRepository.findUsers(10, offset * 10);
            // todo:搜索
        }
        UserPageInfo userPageInfo = null;
        userPageInfo = UserPageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .users(allUsers)
                .build();
        return CommonResponse.builder()
                .code(00000)
                .message("成功获取用户列表")
                .result(userPageInfo)
                .build();
    }

    @Transactional
    public CommonResponse register(String name, String phoneNumber, String password) {
        if (userRepository.findUserByPhoneNumber(phoneNumber) != null) {
            return CommonResponse.builder()
                    .message("该手机已经注册过，请直接登录")
                    .code(20001)
                    .build();
        }

        // md5加密密码
        String md5Password = TokenUtil.inputPassToFormPass(password);

        //保存用户信息
        User user = User.builder()
                .name(name)
                .role(false)
                .phoneNumber(phoneNumber)
                .password(md5Password)
                .level(1)
                .build();
        userRepository.save(user);
        return CommonResponse.builder()
                .result(user)
                .message("注册成功")
                .code(10000)
                .build();
    }

    public CommonResponse login(String phoneNumber, String password, boolean backend) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null || !user.getPassword().equals(TokenUtil.inputPassToFormPass(password))) {
            return CommonResponse.builder()
                    .code(20002)
                    .message("用户不存在或密码错误")
                    .build();
        }
        if (backend && !user.getRole()) {
            return CommonResponse.builder()
                    .code(20003)
                    .message("权限不足，只有管理员可以登陆后台系统")
                    .build();
        }
        String token = TokenUtil.getToken(user.getId(), user.getRole());
        return CommonResponse.builder()
                .code(10000)
                .message("登陆成功")
                .result(token)
                .build();
    }

    public CommonResponse deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return CommonResponse.builder()
                .code(10000)
                .message("删除成功")
                .build();
    }

    public CommonResponse updateUserById(Integer id, String name, Boolean role, Integer level, UserRole operator) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return CommonResponse.builder()
                    .code(20003)
                    .message("找不到用户，请确认ID是否正确")
                    .build();
        }
        User user = optionalUser.get();
        if (name != null) {
            if (operator.getId().equals(id))
                user.setName(name);
            else
                return CommonResponse.builder()
                        .code(20005)
                        .message("只有用户自己可以改自己的名字")
                        .build();
        }
        if (role != null) {
            if (operator.getRole())
                user.setRole(role);
            else
                return CommonResponse.builder()
                        .code(20006)
                        .message("只有管理员可以改权限")
                        .build();
        }
        if (level != null) {
            if (operator.getRole())
                user.setRole(role);
            else
                return CommonResponse.builder()
                        .code(20006)
                        .message("只有管理员可以改等级")
                        .build();
        }
        userRepository.save(user);
        return CommonResponse.builder()
                .code(10000)
                .message("更新成功")
                .result(user)
                .build();
    }
}
