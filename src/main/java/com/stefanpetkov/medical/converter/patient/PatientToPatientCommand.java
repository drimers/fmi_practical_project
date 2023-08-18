package com.stefanpetkov.medical.converter.patient;

import com.stefanpetkov.medical.commands.PatientCommand;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatientToPatientCommand implements Converter<Patient, PatientCommand> {


    @Override
    public PatientCommand convert(Patient patient) {
        log.info("Converting patient to patient command = {}", patient);
        validatePatient(patient);

        PatientCommand command = new PatientCommand();
        command.setPatientId(patient.getId());
        command.setFirstName(patient.getFirstName());
        command.setLastName(patient.getLastName());
        command.setPhone(patient.getPhone());

        UserCredentials patientCredentials = patient.getCredentials();
        command.setEmail(patientCredentials.getEmail());

        log.info("Patient command converted = {}", command);
        return command;
    }


    public void validatePatient(Patient patient) {
        String errorMessage = "";
        if (patient == null) {
            errorMessage = "Patient not found";
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }


    }


}
