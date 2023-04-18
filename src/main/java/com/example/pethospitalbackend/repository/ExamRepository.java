package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Exam;
import com.example.pethospitalbackend.domain.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {
    @Query(value = "select * from exam where name like %?1%", nativeQuery = true)
    List<Exam> searchExam(String text);

    @Query(value = "select * from exam where authority = ?1", nativeQuery = true)
    List<Exam> searchExamByAuthority(int authority);
}
