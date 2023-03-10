package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.Question;
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
    public QuestionInfo getOneQuestion(Integer question_id){
        Question q = questionRepository.getOne(question_id);
        String d_name = diseaseTypeRepository.findNameById(q.getDisease_type_id());
        QuestionInfo info = new QuestionInfo(q,d_name);
        return info;
    }

    //获取所有试题
    @SneakyThrows
    public List<JSONObject> getAllQuestions(){
        Collection<Question.SimpleInfo> collection = questionRepository.getAllQuestions();
        List<JSONObject> allQs = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            allQs.add(qs);
        }

        return allQs;
    }

    //试题模糊搜索
    @SneakyThrows
    public List<JSONObject> searchQuestion(String text){
        Collection<Question.SimpleInfo> collection = questionRepository.searchQuestion(text);
        List<JSONObject> allQs = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
            allQs.add(qs);
        }
        return allQs;
    }

    //试题病类搜索
    @SneakyThrows
    public List<JSONObject> searchQuestionByDisease(int disease_type){
        Collection<Question.SimpleInfo> collection = questionRepository.searchQuestionByDisease(disease_type);
        List<JSONObject> allQs = new ArrayList<>();
        for(Question.SimpleInfo s : collection){
            JSONObject qs = new JSONObject();
            int d_id = s.getDisease_type_id();
            qs.put("disease_type_name",diseaseTypeRepository.findNameById(d_id));
            qs.put("title",s.getTitle());
            qs.put("question_id",s.getId());
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

    //获取所有试卷
    public List<PaperInfo> getAllPapers(){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.findAll();
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return paperInfos;
    }

    //获取单张试卷详情
    @SneakyThrows
    public JSONObject getOnePaper(Integer id){
        Paper paper = paperRepository.getOne(id);
        JSONObject object = new JSONObject();
        object.put("paper_id",id);
        object.put("disease_type_name",diseaseTypeRepository.findNameById(paper.getDisease_type_id()));
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
        object.put("questions",infos);
        object.put("question_num",paper.getQuestion_num());
        object.put("name",paper.getName());
        object.put("score",score);

        return object;
    }

    //试卷模糊查询
    public List<PaperInfo> searchPaper(String text){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.searchPaper(text);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return paperInfos;
    }

    //试卷病类搜索
    public List<PaperInfo> searchPaperByDisease(int id){
        List<PaperInfo> paperInfos = new ArrayList<>();
        List<Paper> papers = paperRepository.searchPaperByDisease(id);
        for (Paper paper : papers){
            String d_name = diseaseTypeRepository.findNameById(paper.getDisease_type_id());
            PaperInfo info = new PaperInfo(paper,d_name);
            paperInfos.add(info);
        }
        return paperInfos;
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

    //获取所有考试
    public List<ExamInfo> getAllExams(){
        List<ExamInfo> examInfos = new ArrayList<>();
        List<Exam> exams = examRepository.findAll();
        for (Exam e : exams){
            ExamInfo info = new ExamInfo(e);
            examInfos.add(info);
        }
        return examInfos;
    }

    //获取单场考试详情
    @SneakyThrows
    public JSONObject getOneExam(int id){
        JSONObject object = new JSONObject();
        Exam exam = examRepository.getOne(id);
        object.put("exam_id",exam.getId());
        object.put("start_time",exam.getStart_time());
        object.put("end_time",exam.getEnd_time());
        object.put("exam_name",exam.getName());
        object.put("authority",exam.getAuthority());

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
        object.put("paper_info",paper);

        return object;
    }

    //考试模糊查询
    public List<ExamInfo> searchExam(String text){
        List<ExamInfo> examInfos = new ArrayList<>();
        List<Exam> exams = examRepository.searchExam(text);
        for (Exam e : exams){
            ExamInfo info = new ExamInfo(e);
            examInfos.add(info);
        }
        return examInfos;
    }

}
