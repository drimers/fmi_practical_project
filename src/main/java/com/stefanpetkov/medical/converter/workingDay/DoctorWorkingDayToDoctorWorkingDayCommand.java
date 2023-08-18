package com.stefanpetkov.medical.converter.workingDay;

import com.stefanpetkov.medical.commands.DoctorWorkingDayCommand;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DoctorWorkingDayToDoctorWorkingDayCommand implements Converter<DoctorWorkingDay, DoctorWorkingDayCommand> {


    @Override
    public DoctorWorkingDayCommand convert(DoctorWorkingDay doctorWorkingDay) {
        log.info("Converting doctorWorkingDay to command = {}", doctorWorkingDay);


        validate(doctorWorkingDay);

        Doctor doctor = doctorWorkingDay.getDoctor();



        DoctorWorkingDayCommand command = new DoctorWorkingDayCommand();
        command.setWorkingDayId(doctorWorkingDay.getWorkingDayId());
       // command.setWorkingDay(command.getStartTimeOpeningHour().toString().substring(0,10));
        command.setWorkingDay(doctorWorkingDay.getWorkingDay().toString().substring(0,10));
        command.setStartTimeOpeningHour(doctorWorkingDay.getStartTimeOpeningHour());
        command.setEndTimeOfWorkingHour(doctorWorkingDay.getEndTimeOfWorkingHour());
        command.setExamDuration(doctorWorkingDay.getExamDuration());
        command.setExamHours(doctorWorkingDay.getExamHours());
        command.setBooked(doctorWorkingDay.isBooked());
        command.setDoctorId(doctor.getId());


        log.info("Command converted = {}", command);

        return command;
    }


    private void validate(DoctorWorkingDay doctorWorkingDay) {
        String errorMessage = "";
        if (doctorWorkingDay == null) {
            errorMessage = "Passed doctorWorkingDay is NULL!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
//        if (doctorWorkingDay.getDoctor() == null) {
//            errorMessage = "Passed doctorWorkingDay without doctor!";
//            log.error(errorMessage);
//            throw new RuntimeException(errorMessage);
//        }
    }
}
