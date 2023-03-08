package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.repository.UserRepository;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.util.FileUtil;
import com.example.pethospitalbackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public CommonResponse register(String name, String phoneNumber, String password,
                                   MultipartFile avatar, Boolean gender) {
        if (userRepository.findUserByPhoneNumber(phoneNumber) != null) {
            return CommonResponse.builder().message("该手机已经注册过，请直接登录").code(20001).build();
        }

        // md5加密密码
        String md5Password = TokenUtil.inputPassToFormPass(password);

        // 上传头像
        String avatarName = FileUtil.upload(avatar);

        //保存用户信息
        User user = User.builder()
                .name(name)
                .role(false)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .password(md5Password)
                .avatar(avatarName)
                .level(1)
                .build();
        userRepository.save(user);
        return CommonResponse.builder().data(user).message("注册成功").code(10000).build();
    }

    public CommonResponse login(String phoneNumber, String password) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null || !user.getPassword().equals(TokenUtil.inputPassToFormPass(password))) {
            return CommonResponse.builder()
                    .code(20002)
                    .message("用户不存在或密码错误")
                    .build();
        }
        String token = TokenUtil.getToken(user.getId(), user.getRole());
        return CommonResponse.builder()
                .code(10000)
                .message("登陆成功")
                .data(token)
                .build();
    }
}
