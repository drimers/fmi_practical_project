package com.stefanpetkov.medical.controllers;


import com.stefanpetkov.medical.commands.DoctorCommand;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final CredentialsRepository credentialsRepository;
    private final DoctorService doctorService;

    @RequestMapping(path = "/registerDoctor", method = RequestMethod.POST)
    public String registerNewDoctor(@Valid @ModelAttribute("doctor") DoctorCommand doctorCommand,
                                    BindingResult result,
                                    Model model) {
        log.info("DoctorController::registerDoctor doctor command = {}", doctorCommand);
        Optional<UserCredentials> existingUser = credentialsRepository.findByEmail(doctorCommand.getDoctorEmail());
        if (!existingUser.isEmpty()) {
            result.rejectValue("doctorEmail", null, "There is already doctor with same email!!!");
        }
        if (result.hasErrors()) {
            model.addAttribute("doctor", doctorCommand);
            return "/admin/registerDoctor";
        }

        DoctorCommand savedCommand = doctorService.saveNewDoctor(doctorCommand);
        model.addAttribute("doctor", savedCommand);
        return "redirect:/admin/registerDoctor?success";
    }

    @GetMapping(path = "/admin/doctorEditForm")
    public String editPatient(Model model, @RequestParam("doctorID") Long id) {

        log.info("DoctorService::doctorEditForm doctor ID = {}", id);
        DoctorCommand doctorCommand = doctorService.findById(id);
        log.info("Retrieved command = {}", id);
        model.addAttribute("doctor", doctorCommand);
        return "/admin/doctorEditForm";
    }

    @RequestMapping(path = "/saveEditDoctor", method = RequestMethod.POST)
    public String updateDoctor(@Valid @ModelAttribute("doctor") DoctorCommand doctorCommand,
                               BindingResult result,
                               Model model) {
        log.info("DoctorController::registerDoctor doctor command = {}", doctorCommand);
        Optional<UserCredentials> existingUser = credentialsRepository.findByEmail(doctorCommand.getDoctorEmail());
        if (!existingUser.isEmpty()) {
            result.rejectValue("doctorEmail", null, "There is already doctor with same email!!!");
        }
        if (result.hasErrors()) {
            model.addAttribute("doctor", doctorCommand);
            return "/admin/doctorEditForm";
        }

        DoctorCommand savedCommand = doctorService.saveNewDoctor(doctorCommand);
        model.addAttribute("doctor", savedCommand);
        return "redirect:/admin?updateDoctor";
    }

    @GetMapping(path = "deleteDoctorAccount")
    public String deleteDoctorAccount(Model model, @RequestParam("doctorID") Long id) {
        log.info("DoctorService::deleteDoctorAccount doctor ID = {}", id);
        doctorService.deleteDoctorAccount(id);
        return "redirect:/admin";
    }




}
