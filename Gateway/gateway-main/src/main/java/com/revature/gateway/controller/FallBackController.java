package com.revature.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fallback")
public class FallBackController {
    @RequestMapping("user")
    public ResponseEntity<String> userServiceDown() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("User service is currently unavailable. " +
                        "Please check back later!");
    }

    @RequestMapping("feed")
    public ResponseEntity<String> feedServiceDown() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Feed service is currently unavailable. " +
                        "Please check back later!");
    }

    @RequestMapping("teapot")
    public ResponseEntity<String> getTeapot() {
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body("I am a teapot...");
    }
}
