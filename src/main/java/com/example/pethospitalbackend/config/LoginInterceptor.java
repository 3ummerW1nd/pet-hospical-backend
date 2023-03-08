package com.example.pethospitalbackend.config;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.annotation.NoLoginMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.domain.user.UserRole;
import com.example.pethospitalbackend.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不需要登录的注解
        boolean isNoNeedLogin= ((HandlerMethod) handler).getMethodAnnotation(NoLoginMethod.class) != null;
        if(isNoNeedLogin){
            return true;
        }

        //需要登录验证
        boolean isAdminMethod = ((HandlerMethod) handler).getMethodAnnotation(AdminMethod.class) != null;
        String token = request.getHeader("Authorization");

        if (null == token){
            throw new Exception("请先登陆");
        } else {
            UserRole userRole = TokenUtil.getUserRoleFromToken(token);
            if (userRole == null || userRole.getRole() == null) {
                throw new Exception("请先登陆");
            } else {
                if (isAdminMethod && !userRole.getRole()) {
                    throw new Exception("权限不足");
                } else {
                    return true;
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
