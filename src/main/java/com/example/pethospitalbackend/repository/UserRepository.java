package com.example.pethospitalbackend.repository;

import com.example.pethospitalbackend.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByPhoneNumber(String phoneNumber);
}
