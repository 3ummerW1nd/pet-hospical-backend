package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.profile.IBasicInfo;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.profile.PetProfileDTO;
import com.example.pethospitalbackend.domain.profile.PetProfileListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetProfileRepository extends JpaRepository<Pet,Integer> {
    @Query("SELECT a.id AS id, a.name AS name, a.birthday AS birthday," +
            " a.type AS type, a.gender AS gender," +
            "b.name AS diseases FROM Pet a JOIN a.diseases b")
    List<PetProfileListDTO> getAllPetProfiles();

    @Query("SELECT a.id AS id, a.name AS name, a.birthday AS birthday," +
            " a.type AS type, a.gender AS gender," +
            "b.name AS diseases FROM Pet a JOIN a.diseases b")
    Page<PetProfileListDTO> findPetProfiles(Pageable pageable);

    // @EntityGraph(value = "pet.withDependencies", type = EntityGraph.EntityGraphType.FETCH)
//    @Query(value = "SELECT * from pet_profiles Limit :limit OFFSET :offset", nativeQuery = true)
//    List<Pet> findPetProfiles(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM pet_profiles")
    Integer getPageCount(@Param("limit") int limit);


    @Query("SELECT a.id AS id, a.name AS name, a.birthday AS birthday," +
            " a.type AS type, a.gender AS gender," +
            " a.images AS images, a.description AS description," +
            " a.weight AS weight" +
//            " b.name AS diseaseNames, b.id AS diseaseIds," +
//            " c.name AS checkupNames, c.id AS checkupIds," +
//            " d.name AS medicineNames, d.id AS medicineIds" +
            " FROM Pet a WHERE a.id = :id")
    PetProfileDTO getProfileById(@Param("id") Integer id);

    @Query(value = "select a.id as id, a.name as name from disease_type a join pet_profile_diseases b on a.id = b.disease_id where b.pet_id = :id", nativeQuery = true)
    List<IBasicInfo> getDiseasesByProfile(Integer id);

    @Query(value = "select a.id as id, a.name as name from checkups a join pet_profile_checkups b on a.id = b.checkup_id where b.pet_id = :id", nativeQuery = true)
    List<IBasicInfo> getCheckupsByProfile(Integer id);

    @Query(value = "select a.id as id, a.name as name from medicines a join pet_profile_medicines b on a.id = b.medicine_id where b.pet_id = :id", nativeQuery = true)
    List<IBasicInfo> getMedicinesByProfile(Integer id);

    @Query(value = "SELECT count(petId) from pet_profile_diseases where disease_id = :id", nativeQuery = true)
    Integer isExistDisease(Integer id);
}




