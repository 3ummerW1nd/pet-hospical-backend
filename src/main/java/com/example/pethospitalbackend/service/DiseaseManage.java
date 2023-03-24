package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Disease;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.Media;
import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.output.Page;
import com.example.pethospitalbackend.repository.DiseaseRepository;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
import com.example.pethospitalbackend.repository.MediaRepository;
import lombok.SneakyThrows;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DiseaseManage {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private DiseaseTypeRepository typeRepository;
    @Autowired
    private MediaRepository mediaRepository;

    //获取所有大病
    public CommonResponse getBigDisease(){
        List<String> types = typeRepository.getAllType();
        return CommonResponse.builder().result(types).message("获取成功").code(0).build();
    }

    //获取所有病类
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
        return CommonResponse.builder().result(disease_types).message("获取成功").code(0).build();
    }

//    //获取所有病例
//    @SneakyThrows
//    public CommonResponse getAllDiseases(){
//        Collection<Disease.DiseaseInfo> infos = diseaseRepository.getAllDisease();
//        List<JSONObject> diseasesInfos = new ArrayList<>();
//        for(Disease.DiseaseInfo info : infos){
//            JSONObject diseasesInfo = new JSONObject();
//            diseasesInfo.put("disease_id",info.getId());
//            DiseaseType.DiseaseNameInfo names = typeRepository.getNameById(info.getDisease_Type_Id());
//            diseasesInfo.put("disease_type_name",names.getType());
//            diseasesInfo.put("disease_name",names.getName());
//            diseasesInfos.add(diseasesInfo);
//        }
//        return CommonResponse.builder().result(diseasesInfos).message("获取成功").code(0).build();
//    }

    //添加单个病例
    public CommonResponse addOneDisease(String disease_type, String disease_name, String symptom, String examination,
                                        String diagnosis, String treatment, String image, String video){
        if(!typeRepository.getId(disease_type,disease_name).equals(""))
            return CommonResponse.builder().message("添加失败，数据库中已有该病例").code(1).build();
        DiseaseType type = new DiseaseType(null,disease_type,disease_name);
        typeRepository.save(type);
        Integer disease_type_id = Integer.parseInt(typeRepository.getId(disease_type,disease_name));
        Disease disease = new Disease(disease_type_id,symptom,examination,diagnosis,treatment,image,video);
        diseaseRepository.save(disease);
        return CommonResponse.builder().message("添加成功").code(0).build();
    }

    //删除单个病例
    public CommonResponse deleteOneDisease(Integer disease_id){
        diseaseRepository.deleteById(disease_id);
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

    //修改单个病例
    public CommonResponse modifyOneDisease(int disease_type_id, String symptom, String examination,
                                           String diagnosis, String treatment, String image, String video){

        Disease disease = new Disease(disease_type_id,symptom,examination,diagnosis,treatment,image,video);
        diseaseRepository.save(disease);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //获取单个病例详情
    public CommonResponse getOneDisease(Integer disease_id){
        Disease disease = diseaseRepository.getOne(disease_id);
        JSONObject disease_info = new JSONObject();
        disease_info.put("disease_id",disease_id);
        disease_info.put("disease_type_name",typeRepository.findTypeById(disease_id));
        disease_info.put("disease_name",typeRepository.findNameById(disease_id));
        disease_info.put("symptom",disease.getSymptom());
        disease_info.put("examination",disease.getExamination());
        disease_info.put("diagnosis",disease.getDiagnosis());
        disease_info.put("treatment",disease.getTreatment());

        String image_ids = disease.getImage_ids();
        String[] images = image_ids.split(",");
        String video_ids = disease.getVideo_ids();
        String[] videos = video_ids.split(",");
        List<String> file_descriptions = new ArrayList<>();
        List<String> file_urls = new ArrayList<>();
        for(String image : images){
            file_urls.add(image);
            file_descriptions.add(mediaRepository.getDescription(image));
        }
        for(String video : videos){
            file_urls.add(video);
            file_descriptions.add(mediaRepository.getDescription(video));
        }
        disease_info.put("file_descriptions",file_descriptions);
        disease_info.put("file_urls",file_urls);

        return CommonResponse.builder().result(disease_info).message("获取成功").code(0).build();
    }

    //病例搜索
    public CommonResponse searchDisease(String type,String text,int cur){
        List<DiseaseType> types;
        if(type.equals("") && text.equals(""))
            types = typeRepository.findAll();
        else if(text.equals("") && !type.equals(""))
            types = typeRepository.findByType(type);
        else
            types = typeRepository.findByTypeAndText(type,text);
        List<JSONObject> infos = new ArrayList<>();
        for(DiseaseType type1 : types){
            JSONObject d = new JSONObject();
            d.put("disease_type_name",type1.getType());
            d.put("disease_name",type1.getName());
            d.put("disease_id",type1.getId());
            infos.add(d);
        }
        Page page = new  Page(infos,cur);
        return CommonResponse.builder().result(page).message("搜索成功").code(0).build();
    }
}
