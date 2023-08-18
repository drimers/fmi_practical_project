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
public class ContactCommand  {

    private Long contactId;
    @NotEmpty(message = "Name should not be empty or null")
    private String contactName;
    @NotEmpty(message = "Email should not be empty or null")
    private String contactEmail;
    @NotEmpty(message = "Phone should not be empty or null")
    private String contactPhone;
    @NotEmpty(message = "Message should not be empty or null")
    private String contactMessage;
    private LocalDateTime contactCreatedMessageTimestamp;

}
