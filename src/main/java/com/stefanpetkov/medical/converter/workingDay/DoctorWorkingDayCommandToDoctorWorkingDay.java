package com.stefanpetkov.medical.converter.workingDay;

import com.stefanpetkov.medical.commands.DoctorWorkingDayCommand;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import com.stefanpetkov.medical.exception.NotFoundException;
import com.stefanpetkov.medical.repositories.DoctorRepository;
import com.stefanpetkov.medical.repositories.DoctorWorkingDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DoctorWorkingDayCommandToDoctorWorkingDay implements Converter<DoctorWorkingDayCommand, DoctorWorkingDay> {


    private final DoctorWorkingDayRepository doctorWorkingDayRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public DoctorWorkingDay convert(DoctorWorkingDayCommand command) {
        log.info("Converting command to doctorWorkingDay = {}", command);
        validateCommand(command);

        Optional<DoctorWorkingDay> DoctorWorkingDayOptional = Optional.empty();
        if (command.getWorkingDayId() != null) {
            DoctorWorkingDayOptional = doctorWorkingDayRepository.findById(command.getWorkingDayId());
        }

        DoctorWorkingDay doctorWorkingDay;

        if (DoctorWorkingDayOptional.isPresent()) {
            doctorWorkingDay = DoctorWorkingDayOptional.get();
           // doctorWorkingDay.setDateTimeOfTheAppointment(command.getDateTimeOfTheAppointment());
            return doctorWorkingDay;
        }

        doctorWorkingDay = new DoctorWorkingDay();
        Doctor doctor = doctorRepository.findById(command.getDoctorId()).orElseThrow(() -> new NotFoundException("Doctor not found!"));

        System.out.println("test");

        doctorWorkingDay.setDoctor(doctor);
        doctorWorkingDay.setWorkingDayId(command.getWorkingDayId());
       // doctorWorkingDay.setWorkingDay(command.getStartTimeOpeningHour().toString().substring(0,10));
        doctorWorkingDay.setWorkingDay(command.getWorkingDay().toString().substring(0,10));
        doctorWorkingDay.setStartTimeOpeningHour(command.getStartTimeOpeningHour());
        doctorWorkingDay.setEndTimeOfWorkingHour(command.getEndTimeOfWorkingHour());
        doctorWorkingDay.setExamDuration(command.getExamDuration());
        doctorWorkingDay.setExamHours(command.getExamHours());
        doctorWorkingDay.setBooked(command.isBooked());

        log.info("doctorWorkingDay converted = {}", doctorWorkingDay);
        return doctorWorkingDay;
    }


    private void validateCommand(DoctorWorkingDayCommand command) {
        String errorMsg = "";
        if (command == null) {
            errorMsg += "Command is NULL!";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        //todo validate other fields too, create custom Exception class
    }
}
