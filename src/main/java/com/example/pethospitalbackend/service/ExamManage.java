package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.output.ExamInfo;
import com.example.pethospitalbackend.output.PaperInfo;
import com.example.pethospitalbackend.output.PaperQInfo;
import com.example.pethospitalbackend.output.QuestionInfo;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
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
        return CommonResponse.builder().data(questionInfo).message("获取成功").code(0).build();
    }

    //获取所有试题
    @SneakyThrows
    public CommonResponse getAllQuestions(){
        Collection<Question.SimpleInfo> collection = questionRepository.getAllQuestions();
        List<JSONObject> questionInfos = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            questionInfos.add(qs);
        }

        return CommonResponse.builder().data(questionInfos).message("获取成功").code(0).build();
    }

    //修改单个试题
    public CommonResponse modifyOneQuestion(Integer q_id,Integer disease_id, String title, String a, String b, String c, String d, String answer){
        Question question = new Question(q_id,disease_id,title,a,b,c,d,answer);
        questionRepository.save(question);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //试题搜索
    @SneakyThrows
    public CommonResponse searchQuestion(int disease_id,String text){
        Collection<Question.SimpleInfo> collection;
        if(text.equals(""))
            collection = questionRepository.searchQuestionByDisease(disease_id);
        else
            collection = questionRepository.searchQuestionByDiseaseAndText(disease_id,text);
        List<JSONObject> questionInfos = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            questionInfos.add(qs);
        }
        return CommonResponse.builder().data(questionInfos).message("搜索成功").code(0).build();
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
        return CommonResponse.builder().data(questionInfos).message("搜索成功").code(0).build();
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
        return CommonResponse.builder().data(questionInfos).message("搜索成功").code(0).build();
    }*/

    //添加单张试卷
    public CommonResponse addOnePaper(Integer disease_id, String name, String question_ids, Integer q_num, String points){
        Paper paper = new Paper(null,disease_id,name,question_ids,q_num,points);
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

    //获取所有试卷
    public CommonResponse getAllPapers(){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.findAll();
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return CommonResponse.builder().data(paperInfos).message("获取成功").code(0).build();
    }

    //获取单张试卷详情
    @SneakyThrows
    public CommonResponse getOnePaper(Integer id){
        Paper paper = paperRepository.getOne(id);
        JSONObject paperInfo = new JSONObject();
        paperInfo.put("paper_id",id);
        paperInfo.put("disease_type_name",diseaseTypeRepository.findNameById(paper.getDisease_type_id()));
        List<PaperQInfo> infos = new ArrayList<>();
        String[] qs = paper.getQuestion_ids().trim().split(",");
        String[] points = paper.getQuestion_points().trim().split(",");
        int score = 0;
        for (int i = 0; i < paper.getQuestion_num(); i++) {
            String q = qs[i];
            Question question = questionRepository.getOne(Integer.valueOf(q));
            String d_name = diseaseTypeRepository.findNameById(question.getDisease_type_id());
            int point = Integer.valueOf(points[i]);
            score += point;
            PaperQInfo qInfo = new PaperQInfo(question,d_name,point);
            infos.add(qInfo);
        }
        paperInfo.put("questions",infos);
        paperInfo.put("question_num",paper.getQuestion_num());
        paperInfo.put("name",paper.getName());
        paperInfo.put("score",score);

        return CommonResponse.builder().data(paperInfo).message("获取成功").code(0).build();
    }

    //修改单张试卷
    public CommonResponse modifyOnePaper(Integer paper_id,Integer disease_id, String name, String question_ids, Integer q_num, String points){
        Paper paper = new Paper(paper_id,disease_id,name,question_ids,q_num,points);
        paperRepository.save(paper);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //试卷模糊查询
    public CommonResponse searchPaper(Integer disease_id, String text){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers;
        if (text.equals(""))
            papers = paperRepository.searchPaperByDisease(disease_id);
        else
            papers = paperRepository.searchPaperByDiseaseAndText(disease_id,text);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return CommonResponse.builder().data(paperInfos).message("查询成功").code(0).build();
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
        return CommonResponse.builder().data(paperInfos).message("查询成功").code(0).build();
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
        return CommonResponse.builder().data(paperInfos).message("查询成功").code(0).build();
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

    //获取所有考试
    public CommonResponse getAllExams(){
        List<ExamInfo> examInfos = new ArrayList<>();
        List<Exam> exams = examRepository.findAll();
        for (Exam e : exams){
            ExamInfo info = new ExamInfo(e);
            examInfos.add(info);
        }
        return CommonResponse.builder().data(examInfos).message("获取成功").code(0).build();
    }

    //获取单场考试详情
    @SneakyThrows
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
        paper.put("disease_type_name",diseaseTypeRepository.findNameById(p.getDisease_type_id()));
        int score = 0;
        String[] pionts = p.getQuestion_points().trim().split(",");
        for(String piont : pionts)
            score += Integer.valueOf(piont);
        paper.put("score",score);
        examInfo.put("paper_info",paper);

        return CommonResponse.builder().data(examInfo).message("获取成功").code(0).build();
    }

    //修改单场考试
    public CommonResponse modifyOneExam(Integer exam_id,Integer paper_id, String name, String start, String end, Integer authority){
        Exam exam = new Exam(exam_id,paper_id,name,start,end,authority);
        examRepository.save(exam);
        return CommonResponse.builder().message("修改成功").code(0).build();
    }

    //考试模糊查询
    public CommonResponse searchExam(String text){
        List<ExamInfo> examInfos = new ArrayList<>();
        List<Exam> exams = examRepository.searchExam(text);
        for (Exam e : exams){
            ExamInfo info = new ExamInfo(e);
            examInfos.add(info);
        }
        return CommonResponse.builder().data(examInfos).message("查询成功").code(0).build();
    }

}
