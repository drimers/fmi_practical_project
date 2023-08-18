package com.stefanpetkov.medical.controllers;

import com.stefanpetkov.medical.commands.DoctorWorkingDayCommand;
import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.services.DoctorWorkingDayService;
import com.stefanpetkov.medical.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorWorkingDaysController {

    private final CredentialsRepository credentialsRepository;
    private final DoctorWorkingDayService doctorWorkingDayService;




    @GetMapping(path = "/doctor/doctorWorkingDays")
    public String doctorWorkingDaysForm(Model model, DoctorWorkingDay doctorWorkingDay) {
        log.info("DoctorWorkingDaysController::doctorWorkingDaysForm");
        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("doctor", currentUserId);
        model.addAttribute("workingDays", doctorWorkingDay);
        // doctorService.deleteDoctorAccount(id);
        return "/doctor/doctorWorkingDays";
    }

    @RequestMapping(path = "/doctor/saveWorkingDays", method = RequestMethod.POST)
    public String saveDoctorWorkingDays(DoctorWorkingDayCommand doctorWorkingDayCommand) {
        log.info("DoctorWorkingDaysController:: saveDoctorWorkingDays");
        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
        doctorWorkingDayCommand.setDoctorId(currentUserId);
        // doctorWorkingDayService.saveWorkingDay(doctorWorkingDayCommand);
         List<DoctorWorkingDayCommand> commands = doctorWorkingDayService.saveWorkingDay(doctorWorkingDayCommand, currentUserId);





       //Todo Implement doctorWorkingDayCommand command
       // doctorWorkingDayCommand.setDoctorId();



//
//       doctorWorkingDayCommand.setStartTimeOpeningHour(startTimeOpeningHour);
//       doctorWorkingDayCommand.setEndTimeOfWorkingHour(endTimeOfWorkingHour);
//       doctorWorkingDayCommand.setBooked(false);
//       doctorWorkingDayCommand.setExamDuration(examDuration);
//       doctorWorkingDayCommand.setDoctorId(currentUserId);

//        List<LocalDateTime> localDateList = doctorWorkingDayService.getWorkingDays(doctorWorkingDayCommand.getStartTimeOpeningHour(),
//                doctorWorkingDayCommand.getEndTimeOfWorkingHour());
//
//
//        DoctorWorkingDayService doctorWorkingDayService =  new DoctorWorkingDayService(
//                startTimeOpeningHour.toString().substring(0,10),
//                startTimeOpeningHour.get(ChronoField.MINUTE_OF_DAY),
//                endTimeOfWorkingHour.get(ChronoField.MINUTE_OF_DAY),
//                examDuration);
//        doctorWorkingDayService.getWorkingHours();
//
//        doctorWorkingDayService.extracted(localDateList);


//        DoctorWorkingDayService_old doctorWorkingDayService_old =  new DoctorWorkingDayService_old(startTimeOpeningHour.toString().substring(0,10) ,startTimeOpeningHour.get(ChronoField.MINUTE_OF_DAY) ,endTimeOfWorkingHour.get(ChronoField.MINUTE_OF_DAY) , examDuration);
//        doctorWorkingDayService_old.getWorkingHours();
       //Todo implement save method and
       // doctorWorkingDayService.save(doctorWorkingDayCommand);

        return "redirect:/doctor";
    }
}
