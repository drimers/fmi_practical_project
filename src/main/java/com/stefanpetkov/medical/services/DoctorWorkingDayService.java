package com.stefanpetkov.medical.services;

import com.stefanpetkov.medical.commands.DoctorWorkingDayCommand;
import com.stefanpetkov.medical.converter.workingDay.DoctorWorkingDayCommandToDoctorWorkingDay;
import com.stefanpetkov.medical.converter.workingDay.DoctorWorkingDayToDoctorWorkingDayCommand;
import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import com.stefanpetkov.medical.repositories.DoctorWorkingDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorWorkingDayService {

    private final DoctorWorkingDayRepository doctorWorkingDayRepository;
    private final DoctorWorkingDayCommandToDoctorWorkingDay toDoctorWorkingDay;
    private final DoctorWorkingDayToDoctorWorkingDayCommand toDoctorWorkingDayCommand;


    public List<DoctorWorkingDayCommand> findAllWorkingDays(Long doctorId) {
        List<DoctorWorkingDay> doctorWorkingDays = doctorWorkingDayRepository.findAllByDoctorId(doctorId);
        List<DoctorWorkingDayCommand> commands = new ArrayList<>();
        for (DoctorWorkingDay day : doctorWorkingDays) {
            DoctorWorkingDayCommand command = toDoctorWorkingDayCommand.convert(day);
            commands.add(command);
        }
        return commands;
    }


    public DoctorWorkingDayCommand saveWorkingDay(DoctorWorkingDayCommand command){
        DoctorWorkingDay day = toDoctorWorkingDay.convert(command);
        DoctorWorkingDay savedDay = doctorWorkingDayRepository.save(day);
        DoctorWorkingDayCommand savedCommand = toDoctorWorkingDayCommand.convert(savedDay);
        return savedCommand;
    }


    public List<DoctorWorkingDayCommand> saveWorkingDay(DoctorWorkingDayCommand command, Long doctorId) {
        List<LocalDateTime> days = new ArrayList<>();
        List<String> hours = new ArrayList<>();

        days.addAll(getWorkingDays(command.getStartTimeOpeningHour(), command.getEndTimeOfWorkingHour()));
        hours.addAll(getWorkingHours(command));

        for (int j = 0; j < days.size(); j++) {
            command.setWorkingDay(days.get(j).toString());
            for (int i = 0; i < hours.size(); i++) {
                command.setExamHours(hours.get(i));
                DoctorWorkingDay day = toDoctorWorkingDay.convert(command);
                DoctorWorkingDay savedDay = doctorWorkingDayRepository.save(day);
            }
        }
        return findAllWorkingDays(doctorId);
    }

//    public  void getWorkingHours() {
//        ArrayList<DoctorWorkingDayService> doctorSchedule = new ArrayList<DoctorWorkingDayService>();
//        doctorSchedule.add(new DoctorWorkingDayService(this.date, this.startTime, this.endTime, this.examDuration)); // 8:00 AM to 4:00 PM
//
//// Add more items as needed
////
//        for (DoctorWorkingDayService item : doctorSchedule) {
//            int numExams = (item.getEndTime() - item.getStartTime()) / item.getExamDuration();
//            System.out.println(item.getDay() + " - " + formatTime(item.getStartTime()) + " to " + formatTime(item.getEndTime()) + " (" + numExams + " exams)");
//            int time = item.getStartTime();
//            System.out.println("hours : " + "  " + formatTime(time));
//            for (int i = 0; i < numExams - 1; i++) {
//                System.out.println("hours : " + "  " + formatTime(time += this.examDuration));
//            }
//        }
//    }


//    public void getWorkingHours() {
//        ArrayList<DoctorWorkingDayService> doctorSchedule = new ArrayList<DoctorWorkingDayService>();
//        doctorSchedule.add(new DoctorWorkingDayService(doctorWorkingDayCommand.getStartTimeOpeningHour().toString().substring(0, 10),
//                doctorWorkingDayCommand.getStartTimeOpeningHour().get(ChronoField.MINUTE_OF_DAY),
//                doctorWorkingDayCommand.getEndTimeOfWorkingHour().get(ChronoField.MINUTE_OF_DAY),
//                doctorWorkingDayCommand.getExamDuration())
//        ); // 8:00 AM to 4:00 PM
//
//        for (DoctorWorkingDayService item : doctorSchedule) {
//            int numExams = (item.getEndTime() - item.getStartTime()) / item.getExamDuration();
//            System.out.println(item.getDay() + " - " + formatTime(item.getStartTime()) + " to " + formatTime(item.getEndTime()) + " (" + numExams + " exams)");
//            int time = item.getStartTime();
//            System.out.println("hours " + "  " + formatTime(time));
//            for (int i = 0; i < numExams - 1; i++) {
//                System.out.println("hours : " + formatTime(time += this.examDuration));
//            }
//        }
//    }


//    public List<DoctorWorkingDayCommand> listOfDoctorWorkingDayCommand() {
//        ArrayList<DoctorWorkingDayCommand> listOfDoctorWorkingDayCommand = new ArrayList<>();
//        DoctorWorkingDayCommand command = null;
//        listOfDoctorWorkingDayCommand.add((DoctorWorkingDayCommand) getWorkingDays(command.getStartTimeOpeningHour(), command.getEndTimeOfWorkingHour()));
//        return listOfDoctorWorkingDayCommand;
//    }


    public List<String> getWorkingHours(DoctorWorkingDayCommand doctorWorkingDayCommand) {
        ArrayList<WorkingHours> doctorSchedule = new ArrayList<>();
       // DoctorWorkingDayCommand doctorWorkingDayCommand;
        doctorSchedule.add(new WorkingHours(doctorWorkingDayCommand.getStartTimeOpeningHour().toString().substring(0, 10),
                doctorWorkingDayCommand.getStartTimeOpeningHour().get(ChronoField.MINUTE_OF_DAY),
                doctorWorkingDayCommand.getEndTimeOfWorkingHour().get(ChronoField.MINUTE_OF_DAY),
                doctorWorkingDayCommand.getExamDuration())
        ); // 8:00 AM to 4:00 PM
        List<String> hours = new ArrayList<>();
        for (WorkingHours item : doctorSchedule) {
            int numExams = (item.getEndTime() - item.getStartTime()) / item.getExamDuration();
         //   System.out.println(item.getDay() + " - " + formatTime(item.getStartTime()) + " to " + formatTime(item.getEndTime()) + " (" + numExams + " exams)");
            int time = item.getStartTime();
         //   System.out.println("hours " + "  " + formatTime(time));
            for (int i = 0; i < numExams - 1; i++) {
               // System.out.println("hours : " + formatTime(time += doctorWorkingDayCommand.getExamDuration()));
                time += (doctorWorkingDayCommand.getExamDuration());
                hours.add(formatTime(time));
            }
        }
        return hours;
    }

    // A helper method to format the time as "hh:mm AM/PM"
    public String formatTime(int minutes) {
        int hour = minutes / 60;
        int minute = minutes % 60;
        String amPm = (hour < 12) ? "AM" : "PM";
        if (hour == 0) {
            hour = 12;
        } else if (hour > 12) {
            hour -= 12;
        }
        return String.format("%02d:%02d %s", hour, minute, amPm);
    }

//    public void extracted(List<LocalDateTime> localDateList) {
//        for (int i = 0; i < localDateList.size(); i++) {
//
//            // Using get() method to
//            // access particular element
//            System.out.println("date : " + localDateList.get(i).toString().substring(0, 10));
//            // System.out.print("minutes from LocalDateTime  =  " + localDateList.get(i).get(ChronoField.MINUTE_OF_DAY) + " ");
//            System.out.println();
//        }
//    }


    public List<LocalDateTime> getWorkingDays(
            LocalDateTime startDate, LocalDateTime endDate) {

        Predicate<LocalDateTime> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDaysBetween + 1)
                .filter(isWeekend.negate())
                .collect(Collectors.toList());
    }

    public void changeExamHourToTaken(Long id) {
        DoctorWorkingDay doctorWorkingDay = new DoctorWorkingDay();
        doctorWorkingDay = doctorWorkingDayRepository.findById(id).get();
        doctorWorkingDay.setBooked(true);
        doctorWorkingDayRepository.save(doctorWorkingDay);
    }

//    public static void main(String[] args) {
//
////        LocalDateTime startDateTime = LocalDateTime.parse("2023-04-01T09:00:00");
////        LocalDateTime endDateTime = LocalDateTime.parse("2023-04-07T17:00:00");
////
////        List<LocalDateTime> localDateList = getWorkingDays(startDateTime, endDateTime);
////
////        DoctorWorkingDayService doctorWorkingDayService =
////                new DoctorWorkingDayService("test",
////                        startDateTime.get(ChronoField.MINUTE_OF_DAY),
////                        endDateTime.get(ChronoField.MINUTE_OF_DAY),
////                        30);
////
////        extracted(localDateList, doctorWorkingDayService);
//
//
//    }

}
