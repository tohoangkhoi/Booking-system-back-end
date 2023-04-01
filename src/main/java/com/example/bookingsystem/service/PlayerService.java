package com.example.bookingsystem.service;

import com.example.bookingsystem.exception.DuplicateException;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.model.Player;
import com.example.bookingsystem.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void insertPlayer(Player player) {
        Player lookupPlayer = playerRepository.findByEmail(player.getEmail());
        if(lookupPlayer != null) {
            throw new DuplicateException("Email is already exist.");
        }
        System.out.println("Player inserted: " + player.getEmail());
        playerRepository.save(player);
    }

    public Player signInAndReturnPlayer(String email) {
        Player lookupPlayer = playerRepository.findByEmail(email);

        if(lookupPlayer == null) {
            throw new ResourceNotFoundException("Email does not exist.");
        }
        return lookupPlayer;
    }
}
