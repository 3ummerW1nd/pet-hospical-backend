package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Paper;
import com.example.pethospitalbackend.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper,Integer> {

    @Query(value = "select * from paper where disease_type_id =?1 and name like %?2%", nativeQuery = true)
    List<Paper> searchPaperByDiseaseAndText(int id,String text);

    @Query(value = "select * from paper where disease_type_id =?1", nativeQuery = true)
    List<Paper> searchPaperByDisease(int id);

    @Query(value = "select * from paper where name like %?1%", nativeQuery = true)
    List<Paper> searchPaperByText(String text);

    @Query(value = "select disease_type_id from paper where id =?1", nativeQuery = true)
    String searchDiseaseById(int id);
}
