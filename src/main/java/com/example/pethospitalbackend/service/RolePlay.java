package com.example.pethospitalbackend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.Character;
import com.example.pethospitalbackend.domain.UserExam;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.CharacterRepository;
import com.example.pethospitalbackend.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RolePlay {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private ProcedureRepository procedureRepository;

    //获取所有角色
    public CommonResponse getAllRoles(){
        List<String> roles = characterRepository.getAllRoles();
        JSONObject o = new JSONObject();
        o.put("role_types",roles);
        return CommonResponse.builder().result(o).message("获取成功").code(0).build();
    }

    //根据角色获取action
    public CommonResponse getAllActions(String role){
        Collection<Character.CharacterInfo> infos = characterRepository.getActionByRole(role);
        List<JSONObject> action_infos = new ArrayList<>();
        for(Character.CharacterInfo info :infos){
            JSONObject action = new JSONObject();
            action.put("procedure_id",info.getProcedure_id());
            action.put("action",info.getAction());
            action_infos.add(action);
        }

        JSONObject o = new JSONObject();
        o.put("action_infos",action_infos);
        return CommonResponse.builder().result(o).message("获取成功").code(0).build();
    }

    //获取下一步
    public CommonResponse getNextStep(Integer procedure_id, Integer curr_step){
        int count = procedureRepository.getamount(procedure_id);
        boolean is_end = false;
//        JSONObject result = new JSONObject();
//        result.put("next_step_id",curr_step+1);
        if(curr_step == count)
            is_end = true;
        String next_step = "";
        switch (curr_step){
            case 1:
                next_step = procedureRepository.getStep1(procedure_id);
                break;
            case 2:
                next_step = procedureRepository.getStep2(procedure_id);
                break;
            case 3:
                next_step = procedureRepository.getStep3(procedure_id);
                break;
            case 4:
                next_step = procedureRepository.getStep4(procedure_id);
                break;
            case 5:
                next_step = procedureRepository.getStep5(procedure_id);
                break;
            case 6:
                next_step = procedureRepository.getStep6(procedure_id);
                break;
            default:
                next_step = "";
                break;
        }
        JSONObject result = new JSONObject();
        result.put("next_step_id",curr_step+1);
        result.put("next_step",next_step);
        result.put("is_last_step",is_end);
        return CommonResponse.builder().result(result).message("获取成功").code(0).build();
    }
}
