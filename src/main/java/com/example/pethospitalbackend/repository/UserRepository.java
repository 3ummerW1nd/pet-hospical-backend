package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.domain.user.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "select id, name, phoneNumber, role, level from users Limit :limit OFFSET :offset")
    List<UserInfo> findUsers(@Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT CEIL(COUNT(*) / :limit) AS pageCount FROM users")
    Integer getPageCount(@Param("limit") int limit);
}
