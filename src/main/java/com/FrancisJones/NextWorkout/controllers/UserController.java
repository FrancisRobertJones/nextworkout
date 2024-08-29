package com.FrancisJones.NextWorkout.controllers;

import com.FrancisJones.NextWorkout.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDTO>> create(@RequestBody UserDTO userToBeCreated) {
        System.out.println(userToBeCreated);
        return Mono.just(ResponseEntity.ok().body(userToBeCreated));
    }

}
