package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.commands.AppointmentCommand;
import com.stefanpetkov.medical.domain.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findAppointmentEntitiesByPatient_Id(Long patientId);

    List<Appointment> findAppointmentEntitiesByDoctor_Id(Long doctorId);

    List<Appointment> findAllByDoctor_FirstNameOrDoctor_LastName(String firstName, String lastName);

    //todo test
    List<Appointment> findAllByPatientIdAndDoctor_FirstNameOrDoctor_LastName(Long patientId, String docFirstName, String docLastNme);



    //  public List<AppointmentEntity> findAppointmentEntitiesByPatient_IdEqualUserIdInDoctorsEntity(Long patientID) {
    @Query(value = "select * from APPOINTMENT as A join doctor as D on A.doctor_id=D.user_id  where A.patient_id= :patient_id", nativeQuery = true)
    List<Appointment> findAppointmentEntitiesByPatient_IdEqualUserIdInDoctorsEntity(Long patient_id);
    //Custom query
    // "select * from shop s where s.owner_name like %:keyword% or s.shop_type like %:keyword%"
    //select  *  from APPOINTMENT as A join PATIENT as P on A.patient_id=P.user_id  where P.first_name like  %:keyword% or A.date_time_of_the_appointment like  %:keyword%


    //select  *  from APPOINTMENT as A join Doctor as D on A.doctor_id=D.user_id  where D.first_name like  'Ivan'   or   A.date_time_of_the_appointment like '% :keyword %';
    //select  *  from APPOINTMENT as A join Doctor as D on A.doctor_id=D.user_id  where D.first_name='Ivan'
    @Query(value = "select  *  from APPOINTMENT as A join Doctor as D on A.doctor_id=D.user_id  where lower(D.first_name) like  lower(CONCAT('%', :keyword, '%'))  or   A.date_time_of_the_appointment like CONCAT('%', :keyword, '%');", nativeQuery = true)
    List<Appointment> findByKeyword(@Param("keyword") String keyword);



}
