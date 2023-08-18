package com.stefanpetkov.medical.controllers;

import com.stefanpetkov.medical.commands.ContactCommand;
import com.stefanpetkov.medical.repositories.ContactRepository;
import com.stefanpetkov.medical.services.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ContactController {

    private final ContactService contactService;

    private final ContactRepository contactRepository;

    public ContactController(ContactService contactService, ContactRepository contactRepository) {
        this.contactService = contactService;
        this.contactRepository = contactRepository;
    }


    @RequestMapping({"/contact"})
    public String contact(Model model) {
        model.addAttribute("contact", new ContactCommand());
        return "contact/contact";
    }

    @RequestMapping({"/saveContactForm"})
    public String saveContactForm(@Valid @ModelAttribute("contact") ContactCommand contactCommand,
                                  BindingResult result,
                                  Model model) {
        log.info("ContactController::saveContactForm, command = {}", contactCommand);

        if (result.hasErrors()) {
            model.addAttribute("contact", contactCommand);
            return "contact/contact";
        }
        contactService.saveContact(contactCommand);
        log.info("ContactController::saveContactForm, command saved = {}", contactCommand);
        return "redirect:/contact?success";
    }

    @GetMapping("/deleteContact")
    public String deleteAppointment(@RequestParam Long contactId) {
        log.info("ContactController::deleteAppointment contact ID = {}", contactId);
        contactService.deleteById(contactId);
        return "redirect:/admin";
    }


}
