package com.example.pethospitalbackend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.output.PaperInfo;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
import com.example.pethospitalbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class Test {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DiseaseTypeRepository diseaseTypeRepository;
    @org.junit.jupiter.api.Test
    void testDao(){
        PaperInfo info = new PaperInfo(1,2,"name0",3,"name5");
        String s = JSON.toJSONString(info);
        JSONObject o = JSON.parseObject(s);
        System.out.println(o);
        /*Question question = new Question(null,2,"test2","a2","b2","c2","d2","B");
        questionRepository.save(question);*/
        /*List<Map<String,Object>> os = questionRepository.getAllQuestions();
        for(Map<String,Object> o : os)
            for(String s : o.keySet())
                System.out.println(s + ":" + o.get(s));*/

//        Collection<Question.SimpleInfo> collection = questionRepository.searchQuestion("es");
//        for(Question.SimpleInfo s : collection){
//            System.out.println(s);
//            System.out.println(s.getTitle());
//        }

        //System.out.println(questionRepository.test("es"));
    }

}
