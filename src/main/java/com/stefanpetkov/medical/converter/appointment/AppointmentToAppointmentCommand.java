package com.stefanpetkov.medical.converter.appointment;

import com.stefanpetkov.medical.commands.AppointmentCommand;
import com.stefanpetkov.medical.domain.Appointment;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppointmentToAppointmentCommand implements Converter<Appointment, AppointmentCommand> {


    @Override
    public AppointmentCommand convert(Appointment appointment) {
        log.info("Converting appointment to command = {}", appointment);

        validate(appointment);

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        AppointmentCommand command = new AppointmentCommand();
        command.setAppointmentId(appointment.getAppointmentId());
        command.setDateTimeOfTheAppointment(appointment.getDateTimeOfTheAppointment());

        command.setDoctorId(doctor.getId());
        command.setDoctorUniqueID(doctor.getUniqueId());
        command.setDoctorFirstName(doctor.getFirstName());
        command.setDoctorLastName(doctor.getLastName());
        command.setDoctorPhoneNumber(doctor.getPhone());

        command.setPatientId(patient.getId());
        command.setPatientFirstName(patient.getFirstName());
        command.setPatientLastName(patient.getLastName());
        command.setPatientPhoneNumber(patient.getPhone());
        command.setPatientComment(patient.getComment());

        log.info("Command converted = {}", command);

        return command;
    }


    private void validate(Appointment appointment) {
        String errorMessage = "";
        if (appointment == null) {
            errorMessage = "Passed appointment is NULL!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        if (appointment.getDoctor() == null) {
            errorMessage = "Passed appointment without doctor!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        if (appointment.getPatient() == null) {
            errorMessage = "Passed appointment without patient!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }


}
