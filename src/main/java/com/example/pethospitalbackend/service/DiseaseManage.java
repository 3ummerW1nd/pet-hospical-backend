package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Disease;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.DiseaseRepository;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DiseaseManage {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private DiseaseTypeRepository typeRepository;

    //获取所有病类
    @SneakyThrows
    public CommonResponse getDiseaseTypes(){
        List<String> types = typeRepository.getAllType();
        List<JSONObject> disease_types = new ArrayList<>();
        int i = 0;
        for (String type : types){
            JSONObject disease_type = new JSONObject();
            disease_type.put("disease_type",type);
            disease_type.put("disease_id",i);
            Collection<DiseaseType.DiseaseTypeInfo> infos = typeRepository.getNameByType(type);
            List<JSONObject> children = new ArrayList<>();
            for(DiseaseType.DiseaseTypeInfo info : infos){
                JSONObject c = new JSONObject();
                c.put("disease_type_id",info.getId());
                c.put("disease_type_name",info.getName());
                children.add(c);
            }
            disease_type.put("children",children);
            disease_types.add(disease_type);
            i++;
        }
        return CommonResponse.builder().data(disease_types).message("获取成功").code(0).build();
    }

    //获取所有病例
    @SneakyThrows
    public CommonResponse getAllDiseases(){
        Collection<Disease.DiseaseInfo> infos = diseaseRepository.getAllDisease();
        List<JSONObject> diseasesInfos = new ArrayList<>();
        for(Disease.DiseaseInfo info : infos){
            JSONObject diseasesInfo = new JSONObject();
            diseasesInfo.put("disease_id",info.getId());
            DiseaseType.DiseaseNameInfo names = typeRepository.getNameById(info.getDisease_Type_Id());
            diseasesInfo.put("disease_type_name",names.getType());
            diseasesInfo.put("disease_name",names.getName());
            diseasesInfos.add(diseasesInfo);
        }
        return CommonResponse.builder().data(diseasesInfos).message("获取成功").code(0).build();
    }

    //获取所有病例
    public CommonResponse addOneDisease(int disease_type_id, String symptom, String examination, String diagnosis,
                                         String treatment, String image, String video, String image_description,
                                         String video_description, String disease_name){
        Collection<Disease.DiseaseInfo> infos = diseaseRepository.getAllDisease();
        List<JSONObject> diseasesInfos = new ArrayList<>();
        for(Disease.DiseaseInfo info : infos){
            JSONObject diseasesInfo = new JSONObject();
            diseasesInfo.put("disease_id",info.getId());
            DiseaseType.DiseaseNameInfo names = typeRepository.getNameById(info.getDisease_Type_Id());
            diseasesInfo.put("disease_type_name",names.getType());
            diseasesInfo.put("disease_name",names.getName());
            diseasesInfos.add(diseasesInfo);
        }
        return CommonResponse.builder().data(diseasesInfos).message("获取成功").code(0).build();
    }

}
