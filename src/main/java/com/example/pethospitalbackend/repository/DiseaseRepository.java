package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Integer> {
}
