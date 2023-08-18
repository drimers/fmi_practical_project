package com.stefanpetkov.medical.services;

import com.stefanpetkov.medical.commands.DoctorWorkingDayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorWorkingDayService_13_05_23 {

    private DoctorWorkingDayCommand doctorWorkingDayCommand;


    private String startDate;
    private int startTime;
    private int endTime;
    private int examDuration;

    public DoctorWorkingDayService_13_05_23(String startDate, int startTime, int endTime, int examDuration) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examDuration = examDuration;
    }

    public void workingDayList() {
        ArrayList<DoctorWorkingDayService_13_05_23> doctorSchedule = new ArrayList<DoctorWorkingDayService_13_05_23>();
        doctorSchedule.add(new DoctorWorkingDayService_13_05_23(this.startDate, this.startTime, this.endTime, this.examDuration)); // 8:00 AM to 4:00 PM

        for (DoctorWorkingDayService_13_05_23 item : doctorSchedule) {
            int numExams = (item.getEndTime() - item.getStartTime()) / item.getExamDuration();
            System.out.println(item.getDay() + " - " + formatTime(item.getStartTime()) + " to " + formatTime(item.getEndTime()) + " (" + numExams + " exams)");
            int time = item.getStartTime();
            for (int i = 0; i < numExams; i++) {
                System.out.println(formatTime(time += this.examDuration));
            }
        }
    }

    // A helper method to format the time as "hh:mm AM/PM"
    public static String formatTime(int minutes) {
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

    private static void extracted(List<LocalDateTime> localDateList, DoctorWorkingDayService_13_05_23 doctorWorkingDayService) {
        for (int i = 0; i < localDateList.size(); i++) {

            // Using get() method to
            // access particular element
            System.out.println(localDateList.get(i).toString().substring(0, 10));
            doctorWorkingDayService.workingDayList();
            // System.out.print("minutes from LocalDateTime  =  " + localDateList.get(i).get(ChronoField.MINUTE_OF_DAY) + " ");
            System.out.println();
        }
    }


    public static List<LocalDateTime> getDatesBetweenUsingJava8(
            LocalDateTime startDate, LocalDateTime endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    public int calculateNumberOfAppointmentHours(final int startHours, final int endHours, final int duration) {
        return (endHours - startHours) / duration;
    }

    public String getDay() {
        return startDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getExamDuration() {
        return examDuration;
    }

    //
//


    public static void main(String[] args) {

        LocalDateTime startDateTime = LocalDateTime.parse("2023-04-01T09:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2023-04-07T17:00:00");

        List<LocalDateTime> localDateList = getDatesBetweenUsingJava8(startDateTime, endDateTime);

        DoctorWorkingDayService_13_05_23 doctorWorkingDayService =
                new DoctorWorkingDayService_13_05_23("test", startDateTime.get(ChronoField.MINUTE_OF_DAY),
                                            endDateTime.get(ChronoField.MINUTE_OF_DAY), 30);

        extracted(localDateList, doctorWorkingDayService);
    }

//    private static void extracted1(List<LocalDateTime> localDateList, DoctorWorkingDayService doctorWorkingDayService) {
//        for (int i = 0; i < localDateList.size(); i++) {
//
//            // Using get() method to
//            // access particular element
//            System.out.println(localDateList.get(i).toString().substring(0,10));
//            doctorWorkingDayService.workingDayList();
//           // System.out.print("minutes from LocalDateTime  =  " + localDateList.get(i).get(ChronoField.MINUTE_OF_DAY) + " ");
//            System.out.println();
//        }
//    }


}
