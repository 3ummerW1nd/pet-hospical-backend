package com.example.pethospitalbackend.repository.profile;

import com.example.pethospitalbackend.domain.profile.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetProfileRepository extends JpaRepository<Pet,Integer> {

    @EntityGraph(value = "PetProfile")
    Pet getPetById(Integer id);

}




