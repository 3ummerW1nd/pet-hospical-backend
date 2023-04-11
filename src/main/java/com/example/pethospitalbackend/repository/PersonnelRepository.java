package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.personnel.Personnel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnelRepository  extends CrudRepository<Personnel, Integer> {
    @Query(nativeQuery = true, value = "select * from personnels Limit :limit OFFSET :offset")
    List<Personnel> findPersonnels(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM personnels")
    Integer getPageCount(@Param("limit") int limit);
}
