package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByName(String userName);

    Optional<Users> findByEmailId(String emailId);

}
