package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {
    Equipment findEquipmentByName(String name);
}
