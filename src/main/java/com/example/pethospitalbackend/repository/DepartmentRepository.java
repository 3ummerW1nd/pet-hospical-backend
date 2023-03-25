package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.Department;
import com.example.pethospitalbackend.domain.Personnel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    @Query(nativeQuery = true, value = "select * from departments Limit :limit OFFSET :offset")
    List<Department> findDepartments(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM departments")
    Integer getPageCount(@Param("limit") int limit);

    Department findDepartmentByName(String name);
}
