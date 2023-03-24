package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Character;
import com.example.pethospitalbackend.domain.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
    @Query(value = "select step1 from procedure where id = ?1", nativeQuery = true)
    String getStep1(int id);

    @Query(value = "select step2from procedure where id = ?1", nativeQuery = true)
    String getStep2(int id);

    @Query(value = "select step3 from procedure where id = ?1", nativeQuery = true)
    String getStep3(int id);

    @Query(value = "select step4 from procedure where id = ?1", nativeQuery = true)
    String getStep4(int id);

    @Query(value = "select step5 from procedure where id = ?1", nativeQuery = true)
    String getStep5(int id);

    @Query(value = "select step6 from procedure where id = ?1", nativeQuery = true)
    String getStep6(int id);

    @Query(value = "select amount from procedure where id = ?1", nativeQuery = true)
    Integer getamount(int id);
}
