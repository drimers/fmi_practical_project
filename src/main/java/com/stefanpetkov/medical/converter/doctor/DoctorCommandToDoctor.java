package com.stefanpetkov.medical.converter.doctor;

import com.stefanpetkov.medical.commands.DoctorCommand;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.repositories.AppointmentRepository;
import com.stefanpetkov.medical.repositories.DoctorRepository;
import com.stefanpetkov.medical.repositories.PatientRepository;
import com.stefanpetkov.medical.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DoctorCommandToDoctor implements Converter<DoctorCommand, Doctor> {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Doctor convert(DoctorCommand command) {
        log.info("Converting command to doctor = {}", command);
        validateCommand(command);

        Optional<Doctor> doctorOptional = Optional.empty();
        if (command.getDoctorId() != null) {
            doctorOptional = doctorRepository.findById(command.getDoctorId());
        }

        Doctor doctor;

        if (doctorOptional.isPresent()) {
            if (doctorOptional.isPresent()) {
                doctor = doctorOptional.get();
                doctor.setId(command.getDoctorId());
                doctor.setUniqueId(command.getDoctorUniqueID());
                doctor.setFirstName(command.getDoctorFirstName());
                doctor.setLastName(command.getDoctorLastName());
                doctor.setSpecialty((command.getDoctorSpecialty()));
                doctor.setPhone(command.getDoctorPhone());
                doctor.setDescription(command.getDoctorDescription());

                UserCredentials doctorCredentials = new UserCredentials();
                doctorCredentials.setEmail(command.getDoctorEmail());
                doctorCredentials.setPassword(passwordEncoder.encode(command.getDoctorPassword()));
                doctorCredentials.setApplicationUserRole(ApplicationUserRole.DOCTOR);
                //validateCredentials(doctorCredentials);

                doctor.setCredentials(doctorCredentials);
                doctorCredentials.setBaseUser(doctor);

                return doctor;
            }
        }

        doctor = new Doctor();
        doctor.setUniqueId(command.getDoctorUniqueID());
        doctor.setFirstName(command.getDoctorFirstName());
        doctor.setLastName(command.getDoctorLastName());
        doctor.setSpecialty(command.getDoctorSpecialty());
        doctor.setPhone(command.getDoctorPhone());
        doctor.setDescription(command.getDoctorDescription());


        UserCredentials doctorCredentials = new UserCredentials();
        doctorCredentials.setEmail(command.getDoctorEmail());
        doctorCredentials.setPassword(passwordEncoder.encode(command.getDoctorPassword()));
        doctorCredentials.setApplicationUserRole(ApplicationUserRole.DOCTOR);
        //validateCredentials(doctorCredentials);

        doctor.setCredentials(doctorCredentials);
        doctorCredentials.setBaseUser(doctor);





        log.info("Doctor converted = {}", doctor);
        return doctor;

    }

    private void validateCommand(DoctorCommand doctor) {
        String errorMsg = "";
        if (doctor == null) {
            errorMsg += "Command is NULL!";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        //todo validate other fields too, create custom Exception class
    }


}
