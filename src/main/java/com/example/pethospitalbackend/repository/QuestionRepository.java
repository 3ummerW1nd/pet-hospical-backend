package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Question;
import org.json.JSONObject;
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
}
