package com.stefanpetkov.medical.services;


import com.stefanpetkov.medical.commands.PatientCommand;
import com.stefanpetkov.medical.converter.patient.PatientCommandToPatient;
import com.stefanpetkov.medical.converter.patient.PatientToPatientCommand;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientCommandToPatient toPatient;
    private final PatientToPatientCommand toPatientCommand;


    public Iterable<Patient> getAllPatients(){
        return  patientRepository.findAll();
    }

    public PatientCommand saveNewPatient(PatientCommand command) {
        log.info("PatientService::saveNewPatient passed patient command = {}", command);
        Patient newPatient = toPatient.convert(command);
        //todo evaluate if credentials repo should be called too
        Patient savedPatient = patientRepository.save(newPatient);
        log.info("Saved patient = {}", savedPatient);
        PatientCommand savedCommand = toPatientCommand.convert(savedPatient);
        return savedCommand;
    }

    public void deletePatientAccount(Long patientId){
        log.info("PatientService::deletePatientAccount passed patientId = {}", patientId);
         patientRepository.deleteById(patientId);
        log.info("PatientService::deletePatientAccount , deletedAccount");
    }


    public PatientCommand findById(Long patientID) {
        log.info("PatientService::findById = {}", patientID);

        Optional<Patient> patientOptional = patientRepository.findById(patientID);
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("Appointment with ID=" + patientID + " not found");
        }

        Patient patient = patientOptional.get();
        log.info("AppointmentService::findById, id passed = {}, extracted appointment = {}", patientID, patient);

        PatientCommand command = toPatientCommand.convert(patient);

        log.info("PatientService::Converted command  = {}", command);

        return command;
    }



}
