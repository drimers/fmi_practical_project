package com.stefanpetkov.medical.controllers;


import com.stefanpetkov.medical.commands.AppointmentCommand;
import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import com.stefanpetkov.medical.repositories.AppointmentRepository;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.repositories.DoctorWorkingDayRepository;
import com.stefanpetkov.medical.services.AppointmentService;
import com.stefanpetkov.medical.services.DoctorService;
import com.stefanpetkov.medical.services.DoctorWorkingDayService;
import com.stefanpetkov.medical.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class AppointmentController {
    private final CredentialsRepository credentialsRepository;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final AppointmentRepository appointmentRepository;
    private final DoctorWorkingDayRepository doctorWorkingDayRepository;

    private final DoctorWorkingDayService doctorWorkingDayService;


    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, AppointmentRepository appointmentRepository,
                                 CredentialsRepository credentialsRepository, DoctorWorkingDayRepository doctorWorkingDayRepository, DoctorWorkingDayService doctorWorkingDayService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.appointmentRepository = appointmentRepository;
        this.credentialsRepository = credentialsRepository;
        this.doctorWorkingDayRepository = doctorWorkingDayRepository;
        this.doctorWorkingDayService = doctorWorkingDayService;
    }

    @RequestMapping(path = {"/searchByDoctorName"})
    public String searchAppointmentByDoctorName(AppointmentCommand command, Model model, @RequestParam String name) {
        log.info("AppointmentController::searchAppointmentByDoctorName name passed = {}", name);
        List<AppointmentCommand> commands = appointmentService.searchAppointmentByDoctorName(name);
        model.addAttribute("appointments", commands);
        return "patient/patientAppointments";
    }


    @RequestMapping(path = {"/search"})
    public String search(AppointmentCommand appointment, Model model, String keyword) {
        // List<AppointmentEntity> list = new ArrayList<>();
        if (keyword != null) {
            List<AppointmentCommand> list = appointmentService.getByKeyword(keyword);
            model.addAttribute("appointments", list);
//        } else {
//            List<AppointmentCommand> list = appointmentRepository.findAppointmentEntitiesByPatient_IdEqualUserIdInDoctorsEntity(2L);
//            model.addAttribute("appointments", list);
        }
        return "patient/patientAppointments";
    }

    @RequestMapping(path = "/patient")
    public String findAllAppointmentsForPatient(Model model) {
        log.info("AppointmentController::findAllAppointmentsForPatient");
        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        String role = String.valueOf(SecurityUtil.getInstance().getLoggedUserRole());
        if ("ADMIN".equals(role)) {
            List<AppointmentCommand> patientAppointments = appointmentService.findAllPatientAppointment();
            model.addAttribute("appointments", patientAppointments);
            return "patient/patientAppointments";
        }

        List<AppointmentCommand> patientAppointments = appointmentService.appointmentsForPatient(currentUserId);
        model.addAttribute("appointments", patientAppointments);
        return "patient/patientAppointments";
    }

    @RequestMapping(path = "/doctor")
    public String findAllAppointmentsForDoctor(Model model) {
        log.info("AppointmentController::findAllAppointmentsForDoctor");
        String role = String.valueOf(SecurityUtil.getInstance().getLoggedUserRole());
        if ("ADMIN".equals(role)) {
            List<AppointmentCommand> doctorAppointments = appointmentService.findAllPatientAppointment();
            model.addAttribute("appointments", doctorAppointments);
            return "doctor/doctorAppointments";
        }

        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        List<AppointmentCommand> doctorAppointments = appointmentService.appointmentsForDoctor(currentUserId);
        model.addAttribute("appointments", doctorAppointments);
        return "doctor/doctorAppointments";
    }


//    @RequestMapping(path = "/addAppointment")
//    public String showAppointmentForm(@ModelAttribute("appointment") AppointmentCommand appointment, Model model) {
//        log.info("AppointmentController::showAppointmentForm, command = {}", appointment);
//        model.addAttribute("appointment", appointment);
//       return "appointment";
//    }

    @RequestMapping(path = {"/searchDoctorWorkingDays"})
    public String searchDoctorWorkingDays(AppointmentCommand appointment, Model model, String keyword, String date) {
        // List<AppointmentEntity> list = new ArrayList<>();
        if (keyword != null && date != null) {
            date = date.substring(0, 10);
            List<DoctorWorkingDay> list = doctorWorkingDayRepository.findWorkingDaysByKeywords(keyword, date);
            model.addAttribute("doctors", list);
//        } else {
//            List<AppointmentCommand> list = appointmentRepository.findAppointmentEntitiesByPatient_IdEqualUserIdInDoctorsEntity(2L);
//            model.addAttribute("appointments", list);
        }
        return "appointment";
    }


    @RequestMapping(path = "/addAppointment")
    public String showAppointmentForm(@ModelAttribute("appointment") AppointmentCommand appointment, Model model) {
        log.info("AppointmentController::showAppointmentForm, command = {}", appointment);
        //Iterable<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorWorkingDay> doctors = doctorWorkingDayRepository.findAllDoctorsAndWorkingHours();
        // Iterable<DoctorWorkingDay> doctorWorkingDayCommand = doctorWorkingDayRepository.findAll();
        // model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);
        return "appointment";
    }

//    @RequestMapping(path = "/addAppointment")
//    public String showAppointmentForm(@ModelAttribute("appointment") AppointmentCommand appointment, Model model) {
//        log.info("AppointmentController::showAppointmentForm, command = {}", appointment);
//       List <DoctorWorkingDay> doctors = doctorWorkingDayRepository.findAllByDoctorId(1L);
//        model.addAttribute("appointment", appointment);
//        model.addAttribute("doctors", doctors);
//        return "appointment";
//    }

//    @RequestMapping(path = "/addAppointment")
//    public String addAppointment(@ModelAttribute("appointment") AppointmentCommand appointment, Model model) {
//        log.info("AppointmentController::addAppointment, command = {}", appointment);
//        model.addAttribute("appointment", appointment);
//        //appointmentService.save(appointment);
//        // throw new RuntimeException("NOT IMPLEMENTED");
//        return "appointment";

//    }


    @RequestMapping(value = "/saveAppointment", method = RequestMethod.POST)
    public String saveAppointment(@ModelAttribute("appointment") AppointmentCommand appointment,
                                  @RequestParam("doctor.id") Long id,
                                  @RequestParam("workingDay") String workingDay,
                                  @RequestParam("examHours") String examHours,
                                  @RequestParam("workingDayId") Long workingDayId
                                  )
    {
        log.info("AppointmentController :: updateAppointment  appointment = {} ", appointment);
        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        LocalDateTime parsedDateTime = appointmentService.getLocalDateTime(workingDay, examHours);

        appointment.setDoctorId(id);
        appointment.setPatientId(currentUserId);
        appointment.setDateTimeOfTheAppointment(parsedDateTime);
        appointmentService.save(appointment);// not ready
        doctorWorkingDayService.changeExamHourToTaken(workingDayId);
        return "redirect:/patient?saveAppointment";
    }

    @GetMapping(path = "saveAppointmentForm")
    public String showSaveAppointmentForm(@ModelAttribute("appointment") DoctorWorkingDay appointment, Model model, @RequestParam Long id) {
        log.info("AppointmentController::saveAppointmentForm appointments ID = {}", id);
        Optional<DoctorWorkingDay> appointments = doctorWorkingDayRepository.findById(id);

        log.info("Retrieved command = {}", appointments);
        model.addAttribute("appointment", appointments);
        return "saveAppointmentForm";
    }
//
//
//    @RequestMapping(value = "/saveAppointment", method = RequestMethod.POST)
//    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment,
//                                  @ModelAttribute("doctors") Doctor doctor, Model model) {
//        log.info("AppointmentController :: saveAppointment  appointment = {} ", appointment);
//        model.addAttribute("doctors", doctor);
//        model.addAttribute("appointment", appointment);
//        appointmentRepository.save(appointment);
//        return "redirect:/patient";
//    }


    @GetMapping(path = "updateAppointmentForm")
    public String showUpdateAppointmentForm(Model model, @RequestParam Long appointment_id) {
        log.info("AppointmentController::updateAppointmentForm appointment ID = {}", appointment_id);
        AppointmentCommand appointment = appointmentService.findById(appointment_id);
        log.info("Retrieved command = {}", appointment);
        model.addAttribute("appointment", appointment);
        return "updateAppointment";
    }


    @RequestMapping(value = "/updateAppointment", method = RequestMethod.POST)
    public String updateAppointment(@ModelAttribute("appointment") AppointmentCommand appointment) {
        log.info("AppointmentController :: updateAppointment  appointment = {} ", appointment);
        appointmentService.update(appointment);
        return "redirect:/patient";
    }


    @GetMapping("/deleteAppointment")
    public String deleteAppointment(@RequestParam Long appointment_id) {
        log.info("AppointmentController::deleteAppointment appointment ID = {}", appointment_id);
        appointmentService.deleteById(appointment_id);
        return "redirect:/patient";
    }


}
