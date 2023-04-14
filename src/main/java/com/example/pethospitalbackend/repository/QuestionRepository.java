package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Question;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
//    @Query(value = "select id,disease_type_id,title from question", nativeQuery = true)
//    List<Map<String,Object>> getAllQuestions();

    @Query(value = "select id,disease_type_id,title from question", nativeQuery = true)
    Collection<Question.SimpleInfo> getAllQuestions();

    @Query(value = "select id,disease_type_id,title from question where title like %?2% and disease_type_id = ?1", nativeQuery = true)
    Collection<Question.SimpleInfo> searchQuestionByDiseaseAndText(int disease_id,String text);

    @Query(value = "select id,disease_type_id,title from question where title like %?1% ", nativeQuery = true)
    Collection<Question.SimpleInfo> searchQuestionByText(String text);

//    @Query(value = "select id,disease_type_id,title from question where title like %?1%", nativeQuery = true)
//    Collection<Question.SimpleInfo> searchQuestion(String text);

    @Query(value = "select id,disease_type_id,title from question where disease_type_id =?1", nativeQuery = true)
    Collection<Question.SimpleInfo> searchQuestionByDisease(int id);

    /*@Query(value = "select * from question where title like %?1%", nativeQuery = true)
    List<Question> test(String text);*/
}
