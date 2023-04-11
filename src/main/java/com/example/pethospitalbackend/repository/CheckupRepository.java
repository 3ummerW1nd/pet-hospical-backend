package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.Medicine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckupRepository extends CrudRepository<Checkup, Integer> {
    @Query(nativeQuery = true, value = "select * from checkups Limit :limit OFFSET :offset")
    List<Checkup> findCheckups(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM checkups")
    Integer getPageCount(@Param("limit") int limit);

    @Query(nativeQuery = true, value = "select price from checkups where id = ?1")
    Double findCheckPrice(int id);
}
