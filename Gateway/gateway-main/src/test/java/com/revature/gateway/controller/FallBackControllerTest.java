package com.revature.gateway.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class FallBackControllerTest {

    FallBackController fallBackController;

    @BeforeEach
    void setUp() {
        fallBackController = new FallBackController();
    }

    @Test
    void userServiceDown() {
        //Assign
        ResponseEntity<String> expectedResult =
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("User service is currently unavailable. " +
                                "Please check back later!");

        //Act
        ResponseEntity<String> actualResult =
                fallBackController.userServiceDown();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void feedServiceDown() {
        //Assign
        ResponseEntity<String> expectedResult =
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Feed service is currently unavailable. " +
                                "Please check back later!");

        //Act
        ResponseEntity<String> actualResult =
                fallBackController.feedServiceDown();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getTeapot() {
        //Assign
        ResponseEntity<String> expectedResult =
                ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                        .body("I am a teapot...");

        //Act
        ResponseEntity<String> actualResult =
                fallBackController.getTeapot();

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}