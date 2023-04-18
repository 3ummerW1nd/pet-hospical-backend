package com.example.pethospitalbackend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.UserExam;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.output.Page;
import com.example.pethospitalbackend.repository.ExamRepository;
import com.example.pethospitalbackend.repository.UserExamRepository;

import com.example.pethospitalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {
    @Autowired
    UserExamRepository userExamRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    UserRepository userRepository;

    //获取所有考试
    public CommonResponse getAllExams(int user_id, int cur){
        List<JSONObject> infos = new ArrayList<>();
        List<UserExam> userExams = userExamRepository.searchExamByUser(user_id);
        List<Integer> exam_ids = new ArrayList<>();
        Integer authority = userRepository.getLevelById(user_id);
        for (UserExam userexam : userExams){
            JSONObject info = new JSONObject();
            int exam_id = userexam.getExam_id();
            exam_ids.add(exam_id);
            info.put("exam_id",exam_id);
            Exam exam = examRepository.getOne(exam_id);
            info.put("exam_name",exam.getName());
            info.put("start_time",exam.getStart_time());
            info.put("end_time",exam.getEnd_time());
            info.put("is_done",userexam.getIs_done());
            info.put("history_score",userexam.getHistory_score());
            infos.add(info);
        }

        List<Exam> exams = examRepository.searchExamByAuthority(authority);
        for(Exam exam : exams){
            int exam_id = exam.getId();
            if(exam_ids.contains(exam_id))
                continue;

            JSONObject info = new JSONObject();
            info.put("exam_id",exam_id);
            info.put("exam_name",exam.getName());
            info.put("start_time",exam.getStart_time());
            info.put("end_time",exam.getEnd_time());
            info.put("is_done",false);
            info.put("history_score",0);
            infos.add(info);
        }
        Page page = new Page(infos,cur);
        return CommonResponse.builder().result(page).message("获取成功").code(0).build();
    }

    //更新用户考试信息
    public CommonResponse updateUserExam(int user_id, int exam_id, int history_score){
        Boolean flag = userExamRepository.isExist(user_id,exam_id);
        if(flag == null)
            userExamRepository.insertScore(user_id,exam_id,history_score);
        else
            userExamRepository.updateScore(user_id,exam_id,history_score);
        return CommonResponse.builder().message("更新成功").code(0).build();
    }
}

