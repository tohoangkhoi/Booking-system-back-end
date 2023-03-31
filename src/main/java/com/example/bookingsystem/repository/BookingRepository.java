package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByCourtAndDate(Court court, String date);

}
