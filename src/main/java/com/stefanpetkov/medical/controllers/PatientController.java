package com.stefanpetkov.medical.controllers;


import com.stefanpetkov.medical.commands.PatientCommand;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.services.PatientService;
import com.stefanpetkov.medical.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Controller
public class PatientController {
    private final CredentialsRepository credentialsRepository;

    private final PatientService patientService;


    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String registerNewPatient(@Valid @ModelAttribute("patient") PatientCommand patientCommand,
                                     BindingResult result,
                                     Model model) {
        log.info("PatientController::registerNewPatient patient command = {}", patientCommand);
        Optional<UserCredentials> existingUser = credentialsRepository.findByEmail(patientCommand.getEmail());
        if(!existingUser.isEmpty()){
            result.rejectValue("email",null,"There is already user with same email!!!");
        }
        if(result.hasErrors()){
            model.addAttribute("patient", patientCommand);
            return "/register";
        }
        PatientCommand savedCommand = patientService.saveNewPatient(patientCommand);
        model.addAttribute("patient", savedCommand);
        return "redirect:/login?success";
    }

    @RequestMapping(path = "/saveEditPatient", method = RequestMethod.POST)
    public String saveEditPatient(@Valid @ModelAttribute("patient") PatientCommand patientCommand,
                                     BindingResult result,
                                     Model model) {
        log.info("PatientController::saveEditPatient patient command = {}", patientCommand);
        Optional<UserCredentials> existingUser = credentialsRepository.findByEmail(patientCommand.getEmail());
        if(!existingUser.isEmpty()){
            result.rejectValue("email",null,"There is already user with same email!!!");
        }
        if(result.hasErrors()){
            model.addAttribute("patient", patientCommand);
            return "/patientEditForm";
        }
        PatientCommand savedCommand = patientService.saveNewPatient(patientCommand);
        model.addAttribute("patient", savedCommand);
        return "redirect:/patient?modified";
    }


//    @GetMapping(path = "/patientEditForm")
//    public String editPatient(Model model, @RequestParam("patientID") Long id) {
//        log.info("PatientService::patientEditForm patient ID = {}", id);
//        PatientCommand patientCommand = patientService.findById(id);
//        log.info("Retrieved command = {}", id);
//        model.addAttribute("patient", patientCommand);
//        return "patientEditForm";
//    }

    @GetMapping(path = "/patientEditForm")
    public String editPatient(Model model) {
        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        log.info("PatientService::patientEditForm patient ID = {}", currentUserId);
        PatientCommand patientCommand = patientService.findById(currentUserId);
        log.info("Retrieved command = {}", currentUserId);
        model.addAttribute("patient", patientCommand);
        return "patientEditForm";
    }


    @GetMapping(path = "/deletePatientAccount")
    public String deletePatient(Model model, @RequestParam("patientID") Long id) {
        log.info("PatientService::deletePatientAccount patient ID = {}", id);
        patientService.deletePatientAccount(id);
        return "redirect:/home";
    }



}



