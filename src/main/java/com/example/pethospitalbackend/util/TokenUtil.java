package com.example.pethospitalbackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.pethospitalbackend.domain.user.UserRole;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

public class TokenUtil {

    private static final int ONE_HOUR_MS = 3600000;

    private static final String SALT = "1a2b3c4d";
    private static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+ SALT.charAt(0)+ SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String getToken(Integer id, Boolean role) {

        //过期时间和加密算法设置
        Date date = new Date(System.currentTimeMillis() + ONE_HOUR_MS);
        Algorithm algorithm = Algorithm.HMAC256(UUID.randomUUID().toString());

        return JWT.create()
                .withClaim("id", id)
                .withClaim("role", role)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static UserRole getUserRoleFromToken(String token) {
        token = token.split(" ")[1];
        DecodedJWT jwt = JWT.decode(token);
        return UserRole.builder()
                .id(jwt.getClaim("id").asInt())
                .role(jwt.getClaim("role").asBoolean())
                .build();
    }

}

