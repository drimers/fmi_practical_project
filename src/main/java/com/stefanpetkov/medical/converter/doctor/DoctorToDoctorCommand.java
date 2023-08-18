package com.stefanpetkov.medical.converter.doctor;

import com.stefanpetkov.medical.commands.DoctorCommand;
import com.stefanpetkov.medical.domain.*;
import com.stefanpetkov.medical.exception.NotFoundException;
import com.stefanpetkov.medical.repositories.DoctorWorkingDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class DoctorToDoctorCommand implements Converter<Doctor, DoctorCommand> {

    private final DoctorWorkingDayRepository doctorWorkingDayRepository;

    @Override
    public DoctorCommand convert(Doctor doctor) {
        log.info("Converting Doctor to command = {}", doctor);

        validateDoctor(doctor);


        Set<Appointment> appointment = doctor.getAppointments();

        Patient patient = new Patient();

        List<DoctorWorkingDay> doctorWorkingDay = doctorWorkingDayRepository.findAllByDoctorId(doctor.getId());



        DoctorCommand command = new DoctorCommand();
        command.setDoctorId(doctor.getId());
        command.setDoctorUniqueID(doctor.getUniqueId());
        command.setDoctorFirstName(doctor.getFirstName());
        command.setDoctorLastName(doctor.getLastName());
        command.setDoctorSpecialty(doctor.getSpecialty());
        command.setDoctorPhone(doctor.getPhone());
        command.setDoctorDescription(doctor.getDescription());

        command.setWorkingDay(doctorWorkingDay.get(0).getWorkingDay());


        command.setPatientId(patient.getId());
        command.setPatientFirstName(patient.getFirstName());
        command.setPatientLastName(patient.getLastName());
        command.setPatientPhone(patient.getPhone());
        command.setPatientComment(patient.getComment());

        UserCredentials doctorCredentials = doctor.getCredentials();
        command.setDoctorEmail(doctorCredentials.getEmail());


        log.info("Command converted = {}", command);

        return command;

    }

    public void validateDoctor(Doctor doctor) {
        String errorMessage = "";
        if (doctor == null) {
            errorMessage = "Doctor not found";
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }
}