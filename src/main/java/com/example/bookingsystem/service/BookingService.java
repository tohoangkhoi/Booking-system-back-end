package com.example.bookingsystem.service;

import com.example.bookingsystem.exception.DuplicateException;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.model.Court;
import com.example.bookingsystem.service.bookingDto.BookingDto;
import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.repository.BookingRepository;
import com.example.bookingsystem.repository.CourtRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final String[] timeslot =
   {
            "08:00-08:30",
            "08:30-09:00",
            "09:00-09:30",
            "09:30-10:00",
            "10:00-10:30",
            "10:30-11:00",
            "11:00-11:30",
            "11:30-12:00",
            "12:00-12:30",
            "12:30-13:00",
            "13:00-13:30",
            "13:30-14:00",
            "14:00-14:30",
            "14:30-15:00",
            "15:00-15:30",
            "15:30-16:00",
            "16:00-16:30",
            "16:30-17:00"
   };

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
            throw new ResourceNotFoundException("cannot find court");
        }

        List<Booking> bookingList = bookingRepository.findBookingsByCourtAndDate(court,bookingDto.date);
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
                        .isBooked(false)
                        .build();
                bookingList.add(booking);
            }

            court.setBookings(bookingList);
            courtRepository.save(court);
        }
        return bookingList;
    }

    /*
    * Find if the booking is ready to booked
    * If ready, booked.
    * */
    public void book(BookingDto bookingDto) {
        Court court = courtRepository.findCourtByCourtName(bookingDto.courtName);
        if(court == null) {
            throw new ResourceNotFoundException("cannot find court");
        }
        List<Booking>courtBookings =court.getBookings();
        List<Booking>existedBooking = bookingRepository.findBookingsByCourtAndDateAndTime(court, bookingDto.date, bookingDto.time);

        if(existedBooking.size()!=0) {
            throw new DuplicateException("Booking is not available.");
        }

        Booking booking = modelMapper.map(bookingDto, Booking.class);
        courtBookings.add(booking);
        court.setBookings(courtBookings);

        courtRepository.save(court);
        System.out.println("Added booking: " +booking);
    }

    /*
    * As a user, I want to know which court is available to be booked at this time.
    */
    public boolean isAvailable(BookingDto bookingDto) {
        List<Court> courts = courtRepository.findAll();
        return true;
    }
}
