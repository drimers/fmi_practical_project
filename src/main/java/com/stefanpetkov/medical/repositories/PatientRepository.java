package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

}
