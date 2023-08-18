package com.stefanpetkov.medical.commands;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PatientCommand {

    private Long patientId;
    @NotEmpty(message = "First name should not be empty!")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty!")
    private String lastName;
    @NotEmpty(message = "Phone should not be empty!")
    private String phone;
    private String comment;
    @NotEmpty(message = "Email should not be empty!")
    private String email;
    @NotEmpty(message = "Password should not be empty!")
    private String password;

}