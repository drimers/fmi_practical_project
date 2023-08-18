package com.stefanpetkov.medical.controllers;

import com.stefanpetkov.medical.domain.Contact;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.services.ContactService;
import com.stefanpetkov.medical.services.DoctorService;
import com.stefanpetkov.medical.services.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private final DoctorService doctorService;

    private final PatientService patientService;

    private final ContactService contactService;

    public AdminController(DoctorService doctorService, PatientService patientService, ContactService contactService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.contactService = contactService;
    }

    @RequestMapping("/admin")
    public String adminView(Model model) {
        Iterable<Doctor> doctors =  doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        Iterable<Patient> patients =  patientService.getAllPatients();
        model.addAttribute("patients", patients);
        Iterable<Contact> contacts =  contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "admin/admin";
    }


}
