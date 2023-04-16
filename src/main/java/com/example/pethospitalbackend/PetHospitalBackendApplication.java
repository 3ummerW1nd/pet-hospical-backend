package com.example.pethospitalbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;

@Configuration
@ServletComponentScan
@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.pethospitalbackend.repository")
@EntityScan("com.example.pethospitalbackend.domain")
public class PetHospitalBackendApplication {
    @Value("${spring.servlet.multipart.location}")
    private  String tempDir;

    @Bean("mkdir")
    public void mkDir(){
        File file = new File(tempDir);
        if (file.exists()){
            System.out.println("文件夹存在");
        }else {
            System.out.println("文件夹不存在");
            file.mkdirs(); //创建文件夹
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PetHospitalBackendApplication.class, args);
    }

}
