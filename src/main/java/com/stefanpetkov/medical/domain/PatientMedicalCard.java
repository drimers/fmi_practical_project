package com.stefanpetkov.medical.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "medical_card")
public class PatientMedicalCard  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalCardId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;




}
