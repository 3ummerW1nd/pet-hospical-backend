package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.output.*;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
import com.example.pethospitalbackend.repository.ExamRepository;
import com.example.pethospitalbackend.repository.PaperRepository;
import com.example.pethospitalbackend.repository.QuestionRepository;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSON;

import org.apache.commons.collections4.collection.CompositeCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class ExamManage {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private DiseaseTypeRepository diseaseTypeRepository;

    //添加单个试题
    public CommonResponse addOneQuestion(Integer disease_id, String title, String a, String b, String c, String d, String answer){
        Question question = new Question(null,disease_id,title,a,b,c,d,answer);
        questionRepository.save(question);
        return CommonResponse.builder().message("添加成功").code(0).build();
    }

    //删除单个试题
    public CommonResponse deleteOneQuestion(Integer question_id){
        questionRepository.deleteById(question_id);
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

    //批量删除试题
    public CommonResponse deleteMultiQuestions(String question_ids){
        String[] ids = question_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                questionRepository.deleteById(Integer.valueOf(id));
        }
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

    //获取单个试题详情
    public CommonResponse getOneQuestion(Integer question_id){
        Question q = questionRepository.getOne(question_id);
        String d_name = diseaseTypeRepository.findNameById(q.getDisease_type_id());
        QuestionInfo questionInfo = new QuestionInfo(q,d_name);
        JSONObject o = new JSONObject();
        o.put("question_info",questionInfo);
        return CommonResponse.builder().result(o).message("获取成功").code(0).build();
    }

    //获取所有试题
//    @SneakyThrows
//    public CommonResponse getAllQuestions(int currentPage){
//        Collection<Question.SimpleInfo> collection = questionRepository.getAllQuestions();
//        List<JSONObject> questionInfos = new ArrayList<>();
//        for(Question.SimpleInfo s : collection){
//            JSONObject qs = new JSONObject();
//            int d_id = s.getDisease_type_id();
//            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
//            qs.put("title",s.getTitle());
//            qs.put("question_id",s.getId());
//            questionInfos.add(qs);
//        }
//        return CommonResponse.builder().result(questionInfos).message("获取成功").code(0).build();
//    }

    //修改单个试题
    public CommonResponse modifyOneQuestion(Integer q_id,Integer disease_id, String title, String a, String b, String c, String d, String answer){
        Question question = new Question(q_id,disease_id,title,a,b,c,d,answer);
        questionRepository.save(question);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //试题搜索
    public CommonResponse searchQuestion(int disease_id,String text,int cur){
        System.out.println("start");
        Collection<Question.SimpleInfo> collection;
        if(text.equals("") && disease_id == -1) {
            collection = questionRepository.getAllQuestions();
            System.out.println("------");
        }
        else if(text.equals("") && disease_id != -1) {
            collection = questionRepository.searchQuestionByDisease(disease_id);
            System.out.println("++++:"+collection);
        }
        else {
            collection = questionRepository.searchQuestionByDiseaseAndText(disease_id, text);
            System.out.println("#####");
        }
        List<JSONObject> questionInfos = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            System.out.println("***:"+d_id);
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            questionInfos.add(qs);
        }
        Page page = new  Page(questionInfos,cur);
        return CommonResponse.builder().result(page).message("搜索成功").code(0).build();
    }

    /*//试题模糊搜索
    @SneakyThrows
    public CommonResponse searchQuestion(String text){
        Collection<Question.SimpleInfo> collection = questionRepository.searchQuestion(text);
        List<JSONObject> questionInfos = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            questionInfos.add(qs);
        }
        return CommonResponse.builder().result(questionInfos).message("搜索成功").code(0).build();
    }

    //试题病类搜索
    @SneakyThrows
    public CommonResponse searchQuestionByDisease(int disease_type){
        Collection<Question.SimpleInfo> collection = questionRepository.searchQuestionByDisease(disease_type);
        List<JSONObject> questionInfos = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            questionInfos.add(qs);
        }
        return CommonResponse.builder().result(questionInfos).message("搜索成功").code(0).build();
    }*/

    //添加单张试卷
    public CommonResponse addOnePaper(Integer disease_id, String name, String question_ids, Integer q_num, String point){
        Paper paper = new Paper(null,disease_id,name,question_ids,q_num,Integer.parseInt(point));
        paperRepository.save(paper);
        return CommonResponse.builder().message("添加成功").code(0).build();
    }

    //删除单个试卷
    public CommonResponse deleteOnePaper(Integer paper_id){
        paperRepository.deleteById(paper_id);
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

    //批量删除试卷
    public CommonResponse deleteMultiPapers(String paper_ids){
        String[] ids = paper_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                paperRepository.deleteById(Integer.valueOf(id));
        }
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

//    //获取所有试卷
//    public CommonResponse getAllPapers(){
//        List<PaperInfo> paperInfos = new ArrayList<>();
//        List<Paper> papers = paperRepository.findAll();
//        for (Paper paper : papers){
//            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
//            PaperInfo info = new PaperInfo(paper,d_name);
//            paperInfos.add(info);
//        }
//        return CommonResponse.builder().result(paperInfos).message("获取成功").code(0).build();
//    }

    //获取单张试卷详情
    public CommonResponse getOnePaper(Integer id){
        Paper paper = paperRepository.getOne(id);
        JSONObject paperInfo = new JSONObject();
        paperInfo.put("paper_id",id);
        paperInfo.put("disease_type_name",diseaseTypeRepository.findNameById(paper.getDisease_type_id()));
        List<QuestionInfo> infos = new ArrayList<>();
        String[] qs = paper.getQuestion_ids().trim().split(",");
        //String[] points = paper.getQuestion_points().trim().split(",");
        int point = paper.getPoint();
        int score = point * paper.getQuestion_num();
        for (int i = 0; i < paper.getQuestion_num(); i++) {
            String q = qs[i];
            Question question = questionRepository.getOne(Integer.valueOf(q));
            String d_name = diseaseTypeRepository.findNameById(question.getDisease_type_id());
            //int point = Integer.valueOf(points[i]);
            //score += point;
            QuestionInfo qInfo = new QuestionInfo(question,d_name);
            infos.add(qInfo);
        }
        paperInfo.put("questions",infos);
        paperInfo.put("question_num",paper.getQuestion_num());
        paperInfo.put("name",paper.getName());
        paperInfo.put("question_score",score);

        JSONObject o = new JSONObject();
        o.put("paper_info",paperInfo);
        return CommonResponse.builder().result(o).message("获取成功").code(0).build();
    }

    //修改单张试卷
    public CommonResponse modifyOnePaper(Integer paper_id, String name, String question_ids, Integer q_num, String point){
        int disease_id = Integer.parseInt(paperRepository.searchDiseaseById(paper_id));
        Paper paper = new Paper(paper_id,disease_id,name,question_ids,q_num,Integer.parseInt(point));
        paperRepository.save(paper);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //试卷模糊查询
    public CommonResponse searchPaper(Integer disease_id, String text , int cur){
        //List<PaperInfo> paperInfos = new ArrayList<>();
        List<JSONObject> paperInfos = new ArrayList<>();
        List<Paper> papers;

        if (text.equals("") && disease_id == -1)
            papers = paperRepository.findAll();
        else if(text.equals("") && disease_id != -1)
            papers = paperRepository.searchPaperByDisease(disease_id);
        else
            papers = paperRepository.searchPaperByDiseaseAndText(disease_id,text);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            String s = JSON.toJSONString(info);
            JSONObject o = JSON.parseObject(s);
            paperInfos.add(o);
        }
        Page page = new Page(paperInfos,cur);
        return CommonResponse.builder().result(page).message("查询成功").code(0).build();
    }

    /*//试卷模糊查询
    public CommonResponse searchPaper(String text){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.searchPaper(text);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return CommonResponse.builder().result(paperInfos).message("查询成功").code(0).build();
    }

    //试卷病类搜索
    public CommonResponse searchPaperByDisease(int id){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.searchPaperByDisease(id);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return CommonResponse.builder().result(paperInfos).message("查询成功").code(0).build();
    }*/

    //添加单场考试
    public CommonResponse addOneExam(Integer paper_id, String name, String start, String end, Integer authority){
        Exam exam = new Exam(null,paper_id,name,start,end,authority);
        examRepository.save(exam);
        return CommonResponse.builder().message("添加成功").code(0).build();
    }

    //删除单个考试
    public CommonResponse deleteOneExam(Integer exam_id){
        examRepository.deleteById(exam_id);
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

    //批量删除考试
    public CommonResponse deleteMultiExams(String exam_ids){
        String[] ids = exam_ids.trim().split(",");
        for(String id : ids){
            if(!id.isEmpty() && !id.equals(""))
                examRepository.deleteById(Integer.valueOf(id));
        }
        return CommonResponse.builder().message("删除成功").code(0).build();
    }

//    //获取所有考试
//    public CommonResponse getAllExams(){
//        List<ExamInfo> examInfos = new ArrayList<>();
//        List<Exam> exams = examRepository.findAll();
//        for (Exam e : exams){
//            ExamInfo info = new ExamInfo(e);
//            examInfos.add(info);
//        }
//        return CommonResponse.builder().result(examInfos).message("获取成功").code(0).build();
//    }

    //获取单场考试详情
    public CommonResponse getOneExam(int id){
        JSONObject examInfo = new JSONObject();
        Exam exam = examRepository.getOne(id);
        examInfo.put("exam_id",exam.getId());
        examInfo.put("start_time",exam.getStart_time());
        examInfo.put("end_time",exam.getEnd_time());
        examInfo.put("exam_name",exam.getName());
        examInfo.put("authority",exam.getAuthority());

        JSONObject paper = new JSONObject();
        int p_id = exam.getPaper_id();
        paper.put("paper_id",p_id);
        Paper p = paperRepository.getOne(p_id);
        paper.put("paper_name",p.getName());
        //paper.put("disease_type_name",diseaseTypeRepository.findNameById(p.getDisease_type_id()));
        int score = p.getPoint() * p.getQuestion_num();
//        String[] pionts = p.getQuestion_points().trim().split(",");
//        for(String piont : pionts)
//            score += Integer.valueOf(piont);
        paper.put("score",score);
        examInfo.put("paper_info",paper);

        JSONObject o = new JSONObject();
        o.put("exam_info",examInfo);
        return CommonResponse.builder().result(o).message("获取成功").code(0).build();
    }

    //修改单场考试
    public CommonResponse modifyOneExam(Integer exam_id,Integer paper_id, String name, String start, String end, Integer authority){
        Exam exam = new Exam(exam_id,paper_id,name,start,end,authority);
        examRepository.save(exam);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //考试模糊查询
    public CommonResponse searchExam(String text, int cur){
        //List<ExamInfo> examInfos = new ArrayList<>();
        List<JSONObject> examInfos = new ArrayList<>();
        List<Exam> exams = examRepository.searchExam(text);
        for (Exam e : exams){
            ExamInfo info = new ExamInfo(e);
            String s = JSON.toJSONString(info);
            JSONObject o = JSON.parseObject(s);
            examInfos.add(o);
        }
        Page page = new Page(examInfos,cur);
        return CommonResponse.builder().result(page).message("查询成功").code(0).build();
    }

}
