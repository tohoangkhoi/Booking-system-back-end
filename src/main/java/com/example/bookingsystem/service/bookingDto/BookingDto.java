package com.example.bookingsystem.service.bookingDto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BookingDto {
    public String date;
    public String courtName;
    public String time;
}
