package com.example.pethospitalbackend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.CheckupRepository;
import com.example.pethospitalbackend.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnService {
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private CheckupRepository checkupRepository;

    //计算检查价格
    public CommonResponse getTotalPrice(List<String> m_ids, List<String> c_ids){
        JSONObject object = new JSONObject();
        double m_price = 0.0;
        for (String m_id : m_ids){
            double price = medicineRepository.findMedicinePrice(Integer.parseInt(m_id));
            m_price += price;
        }
        object.put("treatment_price",m_price);
        double c_price = 0.0;
        for (String c_id : c_ids){
            double price = checkupRepository.findCheckPrice(Integer.parseInt(c_id));
            c_price += price;
        }
        object.put("examination_price",c_price);
        object.put("total_price",c_price+m_price);
        return CommonResponse.builder().result(object).message("计算成功").code(0).build();
    }
}
