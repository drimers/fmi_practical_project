package com.stefanpetkov.medical.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "patient")
public class Patient extends BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "First name should not be empty")
    @Column(length = 50, nullable = false)
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Column(length = 50, nullable = false)
    private String lastName;

    @NotEmpty(message = "Phone name should not be empty")
    @Column(length = 50)
    private String phone;

    @Column(length = 2500)
    private String comment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

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

    @Override
    public String toString() {
        return "PatientEntity{" +
                "patientId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", credentials='" + credentials +
                '}';
    }

}
