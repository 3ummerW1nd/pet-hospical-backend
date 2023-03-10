package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.DiseaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseTypeRepository extends JpaRepository<DiseaseType,Integer> {
    @Query(value = "select name from disease_type where id = ?1", nativeQuery = true)
    String findNameById(Integer id);
}
