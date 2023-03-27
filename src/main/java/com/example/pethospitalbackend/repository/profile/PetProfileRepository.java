package com.example.pethospitalbackend.repository.profile;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.profile.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetProfileRepository extends JpaRepository<Pet,Integer> {

    @EntityGraph(value = "PetProfile")
    Pet getPetById(Integer id);

    @EntityGraph(value = "PetProfile")
    @Query(nativeQuery = true, value = "select * from pet_profiles")
    List<Pet> getAllPets();

    @EntityGraph(value = "PetProfile")
    @Query(nativeQuery = true, value = "select * from pet_profiles Limit :limit OFFSET :offset")
    List<Pet> findPetProfiles(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM pet_profiles")
    Integer getPageCount(@Param("limit") int limit);
}




