package com.example.bookingsystem.controller;

import com.example.bookingsystem.service.bookingDto.BookingDto;
import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String testing() {
        return "Server is running";
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Booking>> getBookings (@RequestBody BookingDto bookingDto) {
        return new ResponseEntity<>(bookingService.getAllBookingsByCourtAndDate(bookingDto), HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody BookingDto bookingDto) {
        bookingService.book(bookingDto);
        return new ResponseEntity<>("Successfully booked.", HttpStatus.OK);
    }
}
