package com.stefanpetkov.medical.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

   // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(length = 50, nullable = false)
    private LocalDateTime dateTimeOfTheAppointment;


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    private Patient patient;


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.addAnAppointment(this);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        patient.addAnAppointment(this);
    }



    @Override
    public String toString() {
        return "AppointmentEntity{" +
                "appointmentId=" + appointmentId +
                ", appointment='" + dateTimeOfTheAppointment + '\'' +
                ", patient=" + patient +
                ", doctor=" + doctor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        return Objects.equals(appointmentId, that.appointmentId);
    }

    @Override
    public int hashCode() {
        return appointmentId != null ? appointmentId.hashCode() + 17 : 53;
    }


}
