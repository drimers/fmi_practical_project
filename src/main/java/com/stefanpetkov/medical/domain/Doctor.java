package com.stefanpetkov.medical.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor extends BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 10, nullable = false)
    private String uniqueId;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false)
    private String specialty;

    @Column(length = 50)
    private String phone;

    @Column(length = 2500)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<DoctorWorkingDay> workingDays = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();

//    @ManyToOne(cascade = {CascadeType.MERGE})
//    @JoinColumn(name = "working_day_id")
//    private DoctorWorkingDay doctorWorkingDay;


    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void addAnAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void addWorkingDay(DoctorWorkingDay workingDay) {
        this.workingDays.add(workingDay);
    }


    @Override
    public String toString() {
        return "DoctorEntity{" +
                "doctorId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", workingDay=" + workingDays +
                ", credentials='" + credentials +
                '}';
    }

}
