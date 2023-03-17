package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetProfileRepository extends JpaRepository<PetProfile,Integer> {
}
