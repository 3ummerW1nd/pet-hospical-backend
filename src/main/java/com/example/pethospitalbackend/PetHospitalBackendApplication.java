package com.example.pethospitalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.pethospitalbackend.repository")
@EntityScan("com.example.pethospitalbackend.domain")
public class PetHospitalBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetHospitalBackendApplication.class, args);
    }

}
