package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseManage {
    @Autowired
    private DiseaseRepository diseaseRepository;
}
