package com.example.pethospitalbackend.config;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/02/07/20:08
 * @Description:
 */
@Configuration
@ControllerAdvice
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/register")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        return CommonResponse.builder().message(e.getMessage()).code(00000).build();
    }

}
