package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    @Query(value = "select distinct role from db_character", nativeQuery = true)
    List<String> getAllRoles();

    @Query(value = "select action,procedure_id from db_character where role = ?1", nativeQuery = true)
    Collection<Character.CharacterInfo> getActionByRole(String role);

}
