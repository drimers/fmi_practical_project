package com.stefanpetkov.medical.converter.appointment;

import com.stefanpetkov.medical.commands.AppointmentCommand;
import com.stefanpetkov.medical.domain.Appointment;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.exception.NotFoundException;
import com.stefanpetkov.medical.repositories.AppointmentRepository;
import com.stefanpetkov.medical.repositories.DoctorRepository;
import com.stefanpetkov.medical.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppointmentCommandToAppointment implements Converter<AppointmentCommand, Appointment> {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public Appointment convert(AppointmentCommand command) {
        log.info("Converting command to appointment = {}", command);
        validateCommand(command);

        Optional<Appointment> appointmentOptional = Optional.empty();
        if (command.getAppointmentId() != null) {
            appointmentOptional = appointmentRepository.findById(command.getAppointmentId());
        }

        Appointment appointment;

        if (appointmentOptional.isPresent()) {
            appointment = appointmentOptional.get();
            appointment.setDateTimeOfTheAppointment(command.getDateTimeOfTheAppointment());
            return appointment;
        }

        appointment = new Appointment();
        Doctor doctor = doctorRepository.findById(command.getDoctorId()).orElseThrow(() -> new NotFoundException("Doctor not found!"));
        Patient patient = patientRepository.findById(command.getPatientId()).orElseThrow(() -> new NotFoundException("Patient not found!"));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTimeOfTheAppointment(command.getDateTimeOfTheAppointment());

        log.info("Appointment converted = {}", appointment);
        return appointment;
    }


    private void validateCommand(AppointmentCommand command) {
        String errorMsg = "";
        if (command == null) {
            errorMsg += "Command is NULL!";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        //todo validate other fields too, create custom Exception class
    }


}
