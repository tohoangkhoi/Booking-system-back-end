package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    Court findCourtByCourtName(String courtName);
}
