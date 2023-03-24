package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.domain.user.UserInfo;
import com.example.pethospitalbackend.domain.PageInfo;
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
        Integer count = userRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<UserInfo> allUsers = null;
        if (content == null || content.isEmpty()) {
            allUsers = userRepository.findUsers(10, offset * 10);
        } else {
            allUsers = userRepository.findUsersByName(10, offset * 10, content);
        }
        PageInfo pageInfo = null;
        pageInfo = PageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .data(allUsers)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }

    @Transactional
    public CommonResponse register(String name, String phoneNumber, String password) {
        if (userRepository.findUserByPhoneNumber(phoneNumber) != null) {
            return CommonResponse.builder()
                    .message("一个手机号只能注册一个账号")
                    .code(1)
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
                .message("success")
                .code(0)
                .build();
    }

    public CommonResponse login(String phoneNumber, String password, boolean backend) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null || !user.getPassword().equals(TokenUtil.inputPassToFormPass(password))) {
            return CommonResponse.builder()
                    .code(1)
                    .message("用户不存在或密码错误.")
                    .build();
        }
        if (backend && !user.getRole()) {
            return CommonResponse.builder()
                    .code(2)
                    .message("权限不足，只有管理员可以登录后台系统")
                    .build();
        }
        String token = TokenUtil.getToken(user.getId(), user.getRole());
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(token)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse updateUserById(Integer id, String name, Boolean role, Integer level, UserRole operator) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("找不到用户，请确认ID是否正确")
                    .build();
        }
        User user = optionalUser.get();
        if (name != null && !name.isEmpty()) {
            if (operator.getId().equals(id))
                user.setName(name);
            else
                return CommonResponse.builder()
                        .code(1)
                        .message("只有用户自己可以改自己的名字")
                        .build();
        }
        if (role != null) {
            if (operator.getRole())
                user.setRole(role);
            else
                return CommonResponse.builder()
                        .code(2)
                        .message("只有管理员可以改权限")
                        .build();
        }
        if (level != null) {
            if (level < 1 || level > 5) {
                return CommonResponse.builder()
                        .code(1)
                        .message("等级只有1到5")
                        .build();
            }
            if (operator.getRole())
                user.setRole(role);
            else
                return CommonResponse.builder()
                        .code(1)
                        .message("只有管理员可以改等级")
                        .build();
        }
        userRepository.save(user);
        return CommonResponse.builder()
                .code(0)
                .message("更新成功")
                .build();
    }

    public CommonResponse changePassword(Integer id, String originalPassword, String newPassword, UserRole userRole) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("用户不存在")
                    .build();
        }
        String md5OriginalPassword = TokenUtil.inputPassToFormPass(originalPassword);
        User user = optionalUser.get();
        if (user.getId().equals(id) && user.getPassword().equals(md5OriginalPassword)) {
            String md5NewPassword = TokenUtil.inputPassToFormPass(newPassword);
            user.setPassword(md5NewPassword);
            userRepository.save(user);
            return CommonResponse.builder()
                    .code(0)
                    .message("更新成功")
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(1)
                    .message("密码错误")
                    .build();
        }

    }
}
