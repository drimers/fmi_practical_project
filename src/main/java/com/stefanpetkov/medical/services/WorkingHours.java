package com.stefanpetkov.medical.services;

import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkingHours {
    private DoctorWorkingDay doctorWorkingDay;

    private String date;
    private int startTime;
    private int endTime;
    private int examDuration;

    public WorkingHours(String date, int startTime, int endTime, int examDuration) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examDuration = examDuration;
    }

    public String getDay() {
        return date;
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



//    public void workingDayList() {
//        ArrayList<DoctorWorkingDayService> doctorSchedule = new ArrayList<DoctorWorkingDayService>();
//        doctorSchedule.add(new DoctorWorkingDayService("Monday", 480, 960, 30)); // 8:00 AM to 4:00 PM
//        doctorSchedule.add(new DoctorWorkingDayService("Tuesday", 540, 1020, 30)); // 9:00 AM to 5:00 PM
//        doctorSchedule.add(new DoctorWorkingDayService("Wednesday", 480, 960, 30)); // 8:00 AM to 4:00 PM
//        doctorSchedule.add(new DoctorWorkingDayService("Thursday", 480, 960, 30));
//        doctorSchedule.add(new DoctorWorkingDayService("Friday", 480, 960, 30));
//        doctorSchedule.add(new DoctorWorkingDayService("Saturday", 480, 960, 30));
//        doctorSchedule.add(new DoctorWorkingDayService("Sunday", 480, 960, 30));

// Add more items as needed
//
//    public void getWorkingHours() {
//        ArrayList<DoctorWorkingDayService_old> doctorSchedule = new ArrayList<DoctorWorkingDayService_old>();
//        doctorSchedule.add(new DoctorWorkingDayService_old(this.date, this.startTime, this.endTime, this.examDuration)); // 8:00 AM to 4:00 PM
//
//// Add more items as needed
////
//        for (DoctorWorkingDayService_old item : doctorSchedule) {
//            int numExams = (item.getEndTime() - item.getStartTime()) / item.getExamDuration();
//            System.out.println(item.getDay() + " - " + formatTime(item.getStartTime()) + " to " + formatTime(item.getEndTime()) + " (" + numExams + " exams)");
//            int time = item.getStartTime();
//            System.out.println("hours " + "  " + formatTime(time));
//            for (int i = 0; i < numExams - 1; i++) {
//                System.out.println("hours : " + "  " + formatTime(time += this.examDuration));
//            }
//        }
//    }
//
//    // A helper method to format the time as "hh:mm AM/PM"
//    public  String formatTime(int minutes) {
//        int hour = minutes / 60;
//        int minute = minutes % 60;
//        String amPm = (hour < 12) ? "AM" : "PM";
//        if (hour == 0) {
//            hour = 12;
//        } else if (hour > 12) {
//            hour -= 12;
//        }
//        return String.format("%02d:%02d %s", hour, minute, amPm);
//    }
//
//    public List<LocalDateTime> getWorkingDays(
//            LocalDateTime startDate, LocalDateTime endDate) {
//
//        Predicate<LocalDateTime> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
//                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//
//        return Stream.iterate(startDate, date -> date.plusDays(1))
//                .limit(numOfDaysBetween + 1)
//                .filter(isWeekend.negate())
//                .collect(Collectors.toList());
//    }
//
//
//    public  List<LocalDateTime> getDatesBetweenUsingJava(
//            LocalDateTime startDate, LocalDateTime endDate) {
//
//
//        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
//                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//
//        return IntStream.iterate(0, i -> i + 1)
//                .limit(numOfDaysBetween + 1)
//                .filter((IntPredicate) isWeekend.negate())
//                .mapToObj(i -> startDate.plusDays(i))
//                .collect(Collectors.toList());
//    }
//
//
//    public boolean isWeekend(LocalDateTime day) {
//        if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) {
//            return false;
//        }
//        return true;

//    }


//    public  int calculateNumberOfAppointmentHours(final int startHours, final int endHours, final int duration){
//        return (endHours - startHours)/duration;
//    }


//    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
//        Calendar startCal;
//        Calendar endCal;
//        startCal = Calendar.getInstance();
//        startCal.setTime(startDate);
//        endCal = Calendar.getInstance();
//        endCal.setTime(endDate);
//        int workDays = 0;
//
//        //Return 0 if start and end are the same
//        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
//            return 0;
//        }
//
//        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
//            startCal.setTime(endDate);
//            endCal.setTime(startDate);
//        }
//
//        do {
//            startCal.add(Calendar.DAY_OF_MONTH, 1);
//            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
//                    && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
//                ++workDays;
//            }
//        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
//
//        return workDays;
//}


//    public static void main(String[] args) {
//
//        LocalDateTime startDateTime = LocalDateTime.parse("2023-05-08T09:00:00");
//        LocalDateTime endDateTime = LocalDateTime.parse("2023-05-29T17:00:00");
//
//        List<LocalDateTime> localDateList = getWorkingDays(startDateTime, endDateTime);
//
//        DoctorWorkingDayService_old doctorWorkingDayService = new DoctorWorkingDayService_old("Work time : ",
//                startDateTime.get(ChronoField.MINUTE_OF_DAY),
//                endDateTime.get(ChronoField.MINUTE_OF_DAY),
//                30);
////       doctorWorkingDayService.getWorkingHours();
////
////        //System.out.println(localDateList.get(ChronoField.MINUTE_OF_DAY.ordinal()));
////
////
//        for (int i = 0; i < localDateList.size(); i++) {
//
//            // Using get() method to
//            // access particular element
//            System.out.println("date : " + localDateList.get(i).toString().substring(0, 10));
//            doctorWorkingDayService.getWorkingHours();
//            // System.out.print("minutes from LocalDateTime  =  " + localDateList.get(i).get(ChronoField.MINUTE_OF_DAY) + " ");
//            System.out.println();
//        }
//
//
//        //    localDateList.forEach(System.out::println);
////        DoctorWorkingDayService doctorWorkingDayService = new DoctorWorkingDayService("test",480,960,30);
////
////        doctorWorkingDayService.workingDayList();
//    }


}
