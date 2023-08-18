package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.Appointment;
import com.stefanpetkov.medical.domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@Disabled
@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    private Patient patient;
    private Appointment appointment;

    @BeforeEach
    public void setup(){
        patient = Patient.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("123456789")
                .comment("some comments")
                .appointments((Set<Appointment>) appointment)
                .build();
    }

    //JUnit test get patient by id operation
    @DisplayName("JUnit test get patient by id")
    @Test
    public void givenPatientObject_whenFindById_thenReturnPatientObject() {
        //given - precondition or setup

        patientRepository.save(patient);
        //when - action or the behaviour that we are going to test
        Patient patientDB = patientRepository.findById(patient.getId()).get();
        //then - verify the output
        assertThat(patientDB).isNotNull();
        //assertThat(employeeDB.getId()).isEqualTo(employee.getId());
    }

}