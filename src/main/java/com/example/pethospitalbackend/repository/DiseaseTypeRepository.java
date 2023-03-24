package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.DiseaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DiseaseTypeRepository extends JpaRepository<DiseaseType,Integer> {
    @Query(value = "select name from disease_type where id = ?1", nativeQuery = true)
    String findNameById(Integer id);

    @Query(value = "select type from disease_type where id = ?1", nativeQuery = true)
    String findTypeById(Integer id);

    @Query(value = "select * from disease_type where type = ?1", nativeQuery = true)
    List<DiseaseType> findByType(String type);

    @Query(value = "select * from disease_type where type = ?1 and name like %?2%", nativeQuery = true)
    List<DiseaseType> findByTypeAndText(String type, String text);

    @Query(value = "select id from disease_type where type = ?1 and name = ?2", nativeQuery = true)
    String getId(String type, String name);

    @Query(value = "select distinct type from disease_type", nativeQuery = true)
    List<String> getAllType();

    @Query(value = "select id,name from disease_type where type = ?1", nativeQuery = true)
    Collection<DiseaseType.DiseaseTypeInfo> getNameByType(String type);

    @Query(value = "select type,name from disease_type where id = ?1", nativeQuery = true)
    DiseaseType.DiseaseNameInfo getNameById(Integer id);
}
