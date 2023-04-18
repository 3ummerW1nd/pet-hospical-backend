package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.UserExam;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserExamRepository extends JpaRepository<UserExam, Integer> {
    @Query(value = "select * from user_exam where user_id =?1 ", nativeQuery = true)
    List<UserExam> searchExamByUser(int user_id);

    @Query(value = "select is_done from user_exam where user_id =?1 and exam_id", nativeQuery = true)
    Boolean isExist(int user_id, int exam_id);

    @Transactional
    @Modifying
    @Query(value = "update user_exam set is_done = true, history_score = ?3 from user_exam where user_id =?1 and exam_id = ?2",
            nativeQuery = true)
    List<UserExam> updateScore(int user_id, int exam_id, int score);

    @Transactional
    @Modifying
    @Query(value = "insert into user_exam (user_id,exam_id,is_done,history_score) values (?1,?2,true,?3)",
            nativeQuery = true)
    List<UserExam> insertScore(int user_id, int exam_id, int score);
}
