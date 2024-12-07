package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations,Long> {

}
