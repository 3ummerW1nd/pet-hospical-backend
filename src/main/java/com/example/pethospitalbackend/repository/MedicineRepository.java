package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Medicine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Integer> {
    @Query(nativeQuery = true, value = "select * from medicines Limit :limit OFFSET :offset")
    List<Medicine> findMedicines(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM medicines")
    Integer getPageCount(@Param("limit") int limit);

    @Query(nativeQuery = true, value = "select price from medicines where id = ?1")
    Double findMedicinePrice(int id);

    List<Medicine> findMedicinesByName(String name);
}
