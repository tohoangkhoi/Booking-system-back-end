package com.example.bookingsystem.controller;

import com.example.bookingsystem.model.Player;
import com.example.bookingsystem.service.PlayerService;
import com.example.bookingsystem.service.playerDTO.PlayerDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/insertPlayer")
    public ResponseEntity<?>insertPlayer(@RequestBody @Valid PlayerDTO playerDTO) {

        Player player = modelMapper.map(playerDTO, Player.class);
        playerService.insertPlayer(player);
        return new ResponseEntity<>("Insert successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody @Valid PlayerDTO playerDTO) {

        return new ResponseEntity<>(playerService.signInAndReturnPlayer(playerDTO.email), HttpStatus.OK);
    }
}
