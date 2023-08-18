package com.stefanpetkov.medical.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AppointmentCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    //== appointment related fields ==
    private Long appointmentId;
    private LocalDateTime dateTimeOfTheAppointment;

    //== doctor related fields == todo replace with the corresponding commands when available
    private Long doctorId;
    private String doctorUniqueID;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorPhoneNumber;

    //== patient related fields == todo replace with the corresponding commands when available
    private Long patientId;


    private String patientFirstName;
    private String patientLastName;
    private String patientPhoneNumber;
    private String patientComment;

}
