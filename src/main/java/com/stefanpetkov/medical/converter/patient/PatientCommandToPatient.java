package com.stefanpetkov.medical.converter.patient;

import com.stefanpetkov.medical.commands.PatientCommand;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.security.ApplicationUserRole;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.exception.UserNameExistsException;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PatientCommandToPatient implements Converter<PatientCommand, Patient> {


    private final PatientRepository patientRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Patient convert(PatientCommand command) {
        log.info("Converting command to patient = {}", command);
        validateCommand(command);

        Optional<Patient> patientOptional = Optional.empty();
        if (command.getPatientId() != null) {
            patientOptional = patientRepository.findById(command.getPatientId());
        }

        Patient patient;
        if (patientOptional.isPresent()) {
            patient = patientOptional.get();
            patient.setFirstName(command.getFirstName());
            patient.setLastName(command.getLastName());
            patient.setPhone(command.getPhone());
            patient.setComment(command.getComment());
            return patient;
        }

        patient = new Patient();
        patient.setFirstName(command.getFirstName());
        patient.setLastName(command.getLastName());
        patient.setPhone(command.getPhone());
        patient.setComment(command.getComment());

        UserCredentials patientCredentials = new UserCredentials();
        patientCredentials.setEmail(command.getEmail());
        patientCredentials.setPassword(passwordEncoder.encode(command.getPassword()));
        patientCredentials.setApplicationUserRole(ApplicationUserRole.PATIENT);
        validateCredentials(patientCredentials);

        patient.setCredentials(patientCredentials);
        patientCredentials.setBaseUser(patient);


        log.info("Patient converted = {}", patient);
        return patient;
    }

    private void validateCommand(PatientCommand command) {
        String errorMessage = "";
        if (command == null) {
            errorMessage += "Command is NULL!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        //todo validate other fields too, create custom Exception class
    }// end of method validateCommand


    public void validateCredentials(UserCredentials credentials) {
        Optional<UserCredentials> existingCredentials = credentialsRepository.findByEmail(credentials.getEmail());
        if (existingCredentials.isPresent()) {
            String msg = "Email " + credentials.getEmail() + " is taken";
            log.error(msg);
            // TODO: Testing the 403 error page,better render back the registration form with needed explanations, or appropriate command
            throw new UserNameExistsException(msg);
        }

    }// end of method validateCredentials


}
