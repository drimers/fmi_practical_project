package com.stefanpetkov.medical.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "doctor_working_day")
public class DoctorWorkingDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingDayId;

    @Column(length = 50, nullable = false)
    private String workingDay;

    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(length = 50, nullable = false)
    private LocalDateTime startTimeOpeningHour;

    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(length = 50, nullable = false)
    private LocalDateTime endTimeOfWorkingHour;

    @Column(length = 10)
    private int examDuration;

    @Column
    private String examHours;

    @Column(length = 5, nullable = false)
    private boolean isBooked;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.addWorkingDay(this);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorWorkingDay that = (DoctorWorkingDay) o;

        return Objects.equals(workingDayId, that.workingDayId);
    }

    @Override
    public int hashCode() {
        return workingDayId != null ? workingDayId.hashCode() + 7 : 31;
    }
}
