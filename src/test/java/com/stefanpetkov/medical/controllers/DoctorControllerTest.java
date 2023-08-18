package com.stefanpetkov.medical.controllers;

import com.stefanpetkov.medical.commands.DoctorCommand;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.services.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Disabled
@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    DoctorController controller;
    CredentialsRepository repository;
    DoctorService service;
    DoctorCommand doctorCommand;
    BindingResult result;
    Model model;

    @BeforeEach
    void setUp() {
        controller = new DoctorController(repository, service);
    }

//    @Test
//    void registerNewDoctor() {
//        assertEquals("redirect:/admin/registerDoctor?success",
//                controller.registerNewDoctor(doctorCommand, result, model));
//    }

    @Test
    void deleteDoctorAccount() {
    }
}