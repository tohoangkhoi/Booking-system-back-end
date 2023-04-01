package com.example.bookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer slot;
    private String attendees;
    private boolean joinAble;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Player host;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Booking booking;
}
