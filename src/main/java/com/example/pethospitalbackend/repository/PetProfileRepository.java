package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.profile.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetProfileRepository extends JpaRepository<Pet,Integer> {

    @EntityGraph(value = "pet.withDependencies", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Pet> findById(Integer id);

    @EntityGraph(value = "pet.withDependencies", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT d FROM Pet d")
    List<Pet> getAllPets();

    // @EntityGraph(value = "pet.withDependencies", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT * from pet_profiles Limit :limit OFFSET :offset", nativeQuery = true)
    List<Pet> findPetProfiles(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM pet_profiles")
    Integer getPageCount(@Param("limit") int limit);
}




