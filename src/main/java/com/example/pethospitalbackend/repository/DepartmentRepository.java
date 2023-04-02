package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.department.DepartmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM departments")
    Integer getPageCount(@Param("limit") int limit);

    Department findDepartmentByName(String name);

    @Query("select b.id as id, b.name as name, b.functions as functions, b.phoneNumber as phoneNumber, a.id as directorId, a.name as directorName from Department b join b.director a where b.name = :name")
    DepartmentVO findDepartmentVOByName(@Param("name") String name);

    @Query("select b.id as id, b.name as name, b.functions as functions, b.phoneNumber as phoneNumber, a.id as directorId, a.name as directorName from Department b join b.director a")
    Page<DepartmentVO> findDepartments(Pageable pageable);

    @Query("select b.id as id, b.name as name, b.functions as functions, b.phoneNumber as phoneNumber, a.id as directorId, a.name as directorName from Department b join b.director a")
    List<DepartmentVO> findDepartments();

    List<Department> findDepartmentsByDirectorId(Integer directorId);
}
