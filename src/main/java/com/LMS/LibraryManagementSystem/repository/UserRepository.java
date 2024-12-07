package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
}
