package com.stefanpetkov.medical.services;


import com.stefanpetkov.medical.commands.DoctorCommand;
import com.stefanpetkov.medical.converter.doctor.DoctorCommandToDoctor;
import com.stefanpetkov.medical.converter.doctor.DoctorToDoctorCommand;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorCommandToDoctor toDoctor;
    private final DoctorToDoctorCommand toDoctorCommand;

    public Iterable<Doctor> getAllDoctors(){
        return  doctorRepository.findAll();
    }


    public DoctorCommand saveNewDoctor(DoctorCommand command) {
        log.info("DoctorService::saveNewDoctor passed doctor command = {}", command);
        Doctor newDoctor = toDoctor.convert(command);
        //todo evaluate if credentials repo should be called too
        Doctor savedDoctor = doctorRepository.save(newDoctor);
        log.info("Saved doctor = {}", savedDoctor);
        DoctorCommand savedCommand = toDoctorCommand.convert(savedDoctor);
        return savedCommand;
    }

    public void deleteDoctorAccount(Long doctorId){
        log.info("DoctorService::deleteDoctorAccount passed doctorId = {}", doctorId);
        doctorRepository.deleteById(doctorId);
        log.info("DoctorService::deleteDoctorAccount , deletedAccount");
    }

    public DoctorCommand findById(Long doctorID) {
        log.info("DoctorService::findById = {}", doctorID);

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorID);
        if (doctorOptional.isEmpty()) {
            throw new RuntimeException("Appointment with ID=" + doctorID + " not found");
        }

        Doctor doctor = doctorOptional.get();
        log.info("AppointmentService::findById, id passed = {}, extracted appointment = {}", doctorID, doctor);

        DoctorCommand command = toDoctorCommand.convert(doctor);

        log.info("DoctorService::Converted command  = {}", command);

        return command;
    }

    public List<DoctorCommand> findWorkingDaysByKeyword(String keyword, String date) {
        List<Doctor> doctorEntities = doctorRepository.findWorkingDaysByKeyword(keyword, date);
        List<DoctorCommand> commands = new ArrayList<>();
         for (Doctor doctor : doctorEntities) {
            DoctorCommand command = toDoctorCommand.convert(doctor);
            commands.add(command);
        }
        return commands;
    }


}
