package com.stefanpetkov.medical.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    HomeController controller;

    @BeforeEach
    void setUp(){
        controller = new HomeController();
    }
    @Test
    void home() {
        assertEquals("home/home", controller.home());
    }
}