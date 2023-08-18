package com.stefanpetkov.medical.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class DoctorWorkingDayCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long workingDayId;
    private String workingDay;
    private LocalDateTime startTimeOpeningHour;
    private LocalDateTime endTimeOfWorkingHour;
    private int examDuration;
    private String examHours;
    private boolean isBooked;

    private Long doctorId;
}
