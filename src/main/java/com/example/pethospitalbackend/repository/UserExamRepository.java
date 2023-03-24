package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.UserExam;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExamRepository extends JpaRepository<UserExam, Integer> {
    @Query(value = "select * from user_exam where user_id =?1 ", nativeQuery = true)
    List<UserExam> searchExamByUser(int user_id);
}
