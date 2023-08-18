package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    @Query(value = "select  *  from DOCTOR_WORKING_DAY as W join Doctor as D on W.doctor_id=D.user_id  " +
            "where lower(D.first_name) like  lower(CONCAT('%', :keyword, '%'))  or   D.SPECIALTY  like CONCAT('%', :keyword, '%') " +
            "and W.WORKING_DAY like CONCAT('%', :date, '%');", nativeQuery = true)
    List<Doctor> findWorkingDaysByKeyword(@Param("keyword") String keyword, @Param("date") String date );



}
