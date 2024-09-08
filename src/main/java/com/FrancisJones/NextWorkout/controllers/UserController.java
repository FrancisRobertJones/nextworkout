package com.FrancisJones.NextWorkout.controllers;

import com.FrancisJones.NextWorkout.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/create")
    public Mono<ResponseEntity<UserDTO>> create(@RequestBody UserDTO userToBeCreated) {
        System.out.println(userToBeCreated);
        return Mono.just(ResponseEntity.ok().body(userToBeCreated));
    }

    @GetMapping("/hello")
    public Mono<String> hello(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt);
        String email = jwt.getClaim("email");
        return Mono.just("Hello, your email is " + email);
    }
}
