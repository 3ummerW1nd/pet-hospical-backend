package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Integer> {
    @Query(value = "select symptom from disease where disease_type_id = ?1", nativeQuery = true)
    String isExist(int id);

    @Query(value = "select disease_type_id from disease", nativeQuery = true)
    List<Integer> findAllId();
}
