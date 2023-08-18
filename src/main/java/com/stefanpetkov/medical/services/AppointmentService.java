package com.stefanpetkov.medical.services;


import com.stefanpetkov.medical.commands.AppointmentCommand;
import com.stefanpetkov.medical.converter.appointment.AppointmentCommandToAppointment;
import com.stefanpetkov.medical.converter.appointment.AppointmentToAppointmentCommand;
import com.stefanpetkov.medical.domain.Appointment;
import com.stefanpetkov.medical.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {


    private final AppointmentRepository appointmentRepository;
    private final AppointmentToAppointmentCommand toAppointmentCommand;
    private final AppointmentCommandToAppointment toAppointment;


    public List<AppointmentCommand> searchAppointmentByDoctorName(String name) {
        log.info("AppointmentService::searchAppointmentByDoctorName, name passed = {}", name);
        String[] docName = name.split(" ");
        String firstName = docName[0] != null ? docName[0] : "";
        String lastName = "";
        if (docName.length > 1) {
            lastName = docName[1] != null ? docName[1] : "";
        }
        List<Appointment> appointments = appointmentRepository.findAllByDoctor_FirstNameOrDoctor_LastName(firstName, lastName);
        List<AppointmentCommand> commands = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentCommand command = toAppointmentCommand.convert(appointment);
            commands.add(command);
        }
        return commands;
    }


    public List<AppointmentCommand> getByKeyword(String keyword) {
        List<Appointment> appointmentEntities = appointmentRepository.findByKeyword(keyword);
        List<AppointmentCommand> commands = new ArrayList<>();
        for (Appointment appointment : appointmentEntities) {
            AppointmentCommand command = toAppointmentCommand.convert(appointment);
            commands.add(command);
        }
        return commands;
    }




    public List<AppointmentCommand> appointmentsForPatient(Long patientId) {
        log.info("AppointmentService::appointmentsForPatient, patientId passed = {}", patientId);

        List<Appointment> appointments = appointmentRepository.findAppointmentEntitiesByPatient_Id(patientId);
        List<AppointmentCommand> commands = new ArrayList<>();
        for (Appointment entity : appointments) {
            AppointmentCommand command = toAppointmentCommand.convert(entity);
            commands.add(command);
        }

        return commands;
    }

    public List<AppointmentCommand> findAllPatientAppointment(){
        log.info("AppointmentService::findAllPatientAppointment");

        Iterable<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentCommand> commands = new ArrayList<>();
        for(Appointment entity : appointments){
            AppointmentCommand command = toAppointmentCommand.convert(entity);
            commands.add(command);
        }
        return commands;
    }

    public List<AppointmentCommand> appointmentsForDoctor(Long doctorId) {
        log.info("AppointmentService::appointmentsForDoctor, doctorId passed = {}", doctorId);
        List<Appointment> appointments = appointmentRepository.findAppointmentEntitiesByDoctor_Id(doctorId);
        List<AppointmentCommand> commands = new ArrayList<>();
        for (Appointment entity : appointments) {
            AppointmentCommand command = toAppointmentCommand.convert(entity);
            commands.add(command);
        }
        return commands;
    }


    public AppointmentCommand findById(Long appointmentId) {
        log.info("AppointmentService::findById, id passed = {}", appointmentId);

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isEmpty()) {
            throw new RuntimeException("Appointment with ID=" + appointmentId + " not found");
        }
        Appointment appointment = appointmentOptional.get();
        log.info("AppointmentService::findById, id passed = {}, extracted appointment = {}", appointmentId, appointment);

        AppointmentCommand command = toAppointmentCommand.convert(appointment);

        log.info("AppointmentService::Converted command  = {}", command);

        return command;
    }

    public void save(AppointmentCommand appointmentCommand) {
        log.info("AppointmentService::save saving command = {}", appointmentCommand);
        Appointment newAppointment = toAppointment.convert(appointmentCommand);
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        log.info("Saved command = {}", savedAppointment);
    }


    public void update(AppointmentCommand appointmentCommand) {
        log.info("AppointmentService::update saving command = {}", appointmentCommand);
        Appointment updatedAppointment = toAppointment.convert(appointmentCommand);
        Appointment savedAppointment = appointmentRepository.save(updatedAppointment);
        log.info("Saved command = {}", savedAppointment);
    }


    public void deleteById(Long appointmentId) {
        log.info("AppointmentService::deleteById, id passed = {}", appointmentId);
        appointmentRepository.deleteById(appointmentId);
        log.info("AppointmentService::deleteById, deleted");
    }

    public LocalDateTime getLocalDateTime(String workingDay, String examHours) {
        String date = workingDay + ' ' + examHours;
        date = date.substring(0, 16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime parsedDateTime = LocalDateTime.parse(date, formatter);
        return parsedDateTime;
    }


}