package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Disease;
import com.example.pethospitalbackend.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MediaRepository extends JpaRepository<Media,String> {
    @Query(value = "select description from media where name = ?1", nativeQuery = true)
    String getDescription(String name);

    @Query(value = "select name from media where name = ?1", nativeQuery = true)
    String isExist(String name);
}
