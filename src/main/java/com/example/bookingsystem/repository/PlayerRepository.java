package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Long> {
    Player findByEmail(String email);
}
