package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.repository.ExamRepository;
import com.example.pethospitalbackend.repository.PaperRepository;
import com.example.pethospitalbackend.repository.QuestionRepository;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ExamManage {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private ExamRepository examRepository;

    //添加单个试题
    public void addOneQuestion(Integer disease_id, String title, String a, String b, String c, String d, String answer){
        Question question = new Question(null,disease_id,title,a,b,c,d,answer);
        questionRepository.save(question);
    }

    //删除单个试题
    public void deleteOneQuestion(Integer question_id){
        questionRepository.deleteById(question_id);
    }

    //批量删除试题
    public void deleteMultiQuestions(String question_ids){
        String[] ids = question_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                questionRepository.deleteById(Integer.valueOf(id));
        }
    }

    //获取单个试题详情
    public Question getOneQuestion(Integer question_id){
        return questionRepository.getOne(question_id);
    }

    //获取所有试题
    @SneakyThrows
    public List<JSONObject> getAllQuestions(){
        Collection<Question.SimpleInfo> collection = questionRepository.getAllQuestions();
        List<JSONObject> allQs = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            qs.put("diseaseTypeId",s.getDisease_type_id());
            qs.put("title",s.getTitle());
            qs.put("questionId",s.getId());
            allQs.add(qs);
        }

        return allQs;
    }

    //添加单张试卷
    public void addOnePaper(Integer disease_id, String name, String question_ids, Integer q_num, String points){
        Paper paper = new Paper(null,disease_id,name,question_ids,q_num,points);
        paperRepository.save(paper);
    }

    //删除单个试卷
    public void deleteOnePaper(Integer paper_id){
        paperRepository.deleteById(paper_id);
    }

    //批量删除试卷
    public void deleteMultiPapers(String paper_ids){
        String[] ids = paper_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                paperRepository.deleteById(Integer.valueOf(id));
        }
    }

    //添加单场考试
    public void addOneExam(Integer paper_id, String name, String start, String end, Integer authority){
        Exam exam = new Exam(null,paper_id,name,start,end,authority);
        examRepository.save(exam);
    }

    //删除单个考试
    public void deleteOneExam(Integer exam_id){
        examRepository.deleteById(exam_id);
    }

    //批量删除考试
    public void deleteMultiExams(String exam_ids){
        String[] ids = exam_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                examRepository.deleteById(Integer.valueOf(id));
        }
    }


}
