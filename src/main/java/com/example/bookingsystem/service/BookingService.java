package com.example.bookingsystem.service;

import com.example.bookingsystem.model.Court;
import com.example.bookingsystem.service.bookingDto.BookingDto;
import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.repository.BookingRepository;
import com.example.bookingsystem.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private final String[] timeslot =
   {"8:00-8:50 am",
    "9:00-9:50 am",
    "10:00-10:50 am",
    "11:00-11:50 am",
    "12:00-12:50 pm",
    "1:00-1:50 pm",
    "2:30-3:20 pm",
    "4:00-4:50 pm",
    "5:30-6:20 pm",
    "7:00-7:50 pm"};

    /*
    Retrieve the bookings for a day
    If the bookings is empty, it means that it's the first booking:
    - Generate a list of bookings whose isBooked is false by default
    else:
    - simply return the bookings list.
    */
    public List<Booking> getAllBookingsByCourtAndDate(BookingDto bookingDto) {

        Court court = courtRepository.findCourtByCourtName(bookingDto.courtName);
        if(court == null) {
            throw new IllegalArgumentException("cannot find court");
        }
        List<Booking> bookingList =  bookingRepository.findBookingsByCourtAndDate(court, bookingDto.date);

        if(bookingList.size() == 0) {
            Long counter = 0L;
            bookingList = court.getBookings();
            for(String item : timeslot) {
                counter++;
                Booking booking = Booking.builder()
                        .id(counter)
                        .date(bookingDto.date)
                        .time(item)
                        .court(court)
                        .build();
                bookingList.add(booking);
            }

            court.setBookings(bookingList);
            courtRepository.save(court);
        }
        return bookingList;
    }

    /*
    * Find the bookingList of that date
    * Find the booking's section that user want to book
    * if booking.isBooked() == true, not ready to book
    * if booking.isBooked() == false, ready to book
    * */
    public boolean book(BookingDto bookingDto) {
        Court court = courtRepository.findCourtByCourtName(bookingDto.courtName);
        if(court == null) {
            throw new IllegalArgumentException("cannot find court");
        }
        List<Booking>bookingList =court.getBookings();

         for(Booking booking : bookingList) {
             if(booking.getDate().equals(bookingDto.date) && booking.getTime().equals(bookingDto.time)) {
                if (booking.isBooked()) return false;

                booking.setBooked(true);
                court.setBookings(bookingList);
                courtRepository.save(court);
                return true;
             }
         }
        return true;
    }
}
