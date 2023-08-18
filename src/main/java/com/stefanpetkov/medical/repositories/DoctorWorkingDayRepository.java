package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.DoctorWorkingDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorWorkingDayRepository extends CrudRepository<DoctorWorkingDay, Long> {


    List<DoctorWorkingDay> findAllByDoctorId(Long doctorId);




    @Query(value = "select * FROM DOCTOR_WORKING_DAY as W join Doctor as D on W.doctor_id=D.user_id where W.IS_BOOKED = false LIMIT 10", nativeQuery = true)
    List<DoctorWorkingDay> findAllDoctorsAndWorkingHours();

    @Query(value = "select  *  from DOCTOR_WORKING_DAY as W join Doctor as D on W.doctor_id=D.user_id  " +
            "where (lower(D.first_name) like  lower(CONCAT('%', :keyword, '%'))  or   D.SPECIALTY  like CONCAT('%', :keyword, '%')) " +
            "and W.WORKING_DAY= :date   and W.IS_BOOKED = false", nativeQuery = true)
    List<DoctorWorkingDay> findWorkingDaysByKeywords(@Param("keyword") String keyword, @Param("date") String date );

//    @Query(value = "select * from APPOINTMENT as A join doctor as D on A.doctor_id=D.user_id  where A.patient_id= :patient_id", nativeQuery = true)
//    List<Appointment> findAppointmentEntitiesByPatient_IdEqualUserIdInDoctorsEntity(Long patient_id);

}
