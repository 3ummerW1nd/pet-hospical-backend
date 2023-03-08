package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<Paper,Integer> {
}
