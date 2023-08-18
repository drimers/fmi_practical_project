package com.stefanpetkov.medical.commands;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DoctorCommand {

    //doctor
    private Long doctorId;
    @NotEmpty(message = "Unique ID should not be empty!")
    private String doctorUniqueID;
    @NotEmpty(message = "First name should not be empty!")
    private String doctorFirstName;
    @NotEmpty(message = "Last name should not be empty!")
    private String doctorLastName;
    @NotEmpty(message = "Specialty should not be empty!")
    private String doctorSpecialty;
    @NotEmpty(message = "Phone should not be empty!")
    private String doctorPhone;
    @NotEmpty(message = "Email should not be empty!")
    private String doctorEmail;
    @NotEmpty(message = "Password should not be empty!")
    private String doctorPassword;
    private String doctorDescription;
    //patient
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private String patientPhone;
    private String patientComment;

    //appointment
    private Long appointmentId;
    private LocalDateTime dateTimeOfTheAppointment;

    // DoctorWorkingDay

    private long workingDayId;
    private String workingDay;
    private String examHours;
    private boolean isBooked;


}
