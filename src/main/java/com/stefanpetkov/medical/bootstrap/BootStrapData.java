package com.stefanpetkov.medical.bootstrap;

import com.stefanpetkov.medical.domain.Appointment;
import com.stefanpetkov.medical.domain.Doctor;
import com.stefanpetkov.medical.domain.Patient;
import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.repositories.AppointmentRepository;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import com.stefanpetkov.medical.repositories.DoctorRepository;
import com.stefanpetkov.medical.repositories.PatientRepository;
import com.stefanpetkov.medical.security.ApplicationUserRole;
import com.stefanpetkov.medical.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final CredentialsRepository credentialsRepository;

    private final AppointmentRepository appointmentRepository;

    private final PasswordEncoder passwordEncoder;


    //Patients
    Patient patient1 = new Patient();
    Patient patient2 = new Patient();
    Patient patient3 = new Patient();


    //Doctors
    Doctor doctor = new Doctor();
    Doctor doctor1 = new Doctor();
    Doctor doctor2 = new Doctor();
    Doctor doctor3 = new Doctor();

    //Credentials
    UserCredentials credentials = new UserCredentials();
    UserCredentials credential = new UserCredentials();
    UserCredentials credentials1 = new UserCredentials();
    UserCredentials credentials2 = new UserCredentials();
    UserCredentials credentials3 = new UserCredentials();
    UserCredentials credentials4 = new UserCredentials();
    UserCredentials credentials5 = new UserCredentials();
    UserCredentials credentials6 = new UserCredentials();

    // Appointment
    Appointment appointment = new Appointment();
    Appointment appointment1 = new Appointment();
    Appointment appointment2 = new Appointment();
    Appointment appointment3 = new Appointment();
    Appointment appointment4 = new Appointment();
    Appointment appointment5 = new Appointment();


    @Override
    public void run(String... args) throws Exception {
        log.info("Executing Bootstrap!");
//        loadData();
//        createPatients();
//        createDoctors();
//        createCredentials();
//        makeAppointment();

        log.info("Bootstrap completed!");
    }

    Patient patient = new Patient();
    private void loadData() {

        // doctor
        doctor.setUniqueId("1000000000");
        doctor.setFirstName("Ivan");
        doctor.setLastName("Ivanov");
        doctor.setSpecialty("Nephrologists");
        doctor.setPhone("08987777777");
        doctor.setDescription("Some doctor description");
        log.info("Doctor created = {}", doctor);

        log.info("Doctors");
        log.info("Number of doctors = {}", doctorRepository.count());

        //Credential4
        credential.setEmail("doctor.bg@abv.bg");
        credential.setPassword(passwordEncoder.encode("pass"));
        credential.setApplicationUserRole(ApplicationUserRole.DOCTOR);
        doctor.setCredentials(credential);
        credential.setBaseUser(doctor);
        doctorRepository.save(doctor);
        credentialsRepository.save(credential);

        ////////////////////////////////////////////////

        //patient
        patient.setFirstName("Stefan");
        patient.setLastName("Stefanov");
        patient.setPhone("0893343333");
        patient.setComment("Any comments!!!");
        log.info("Patient created = {}", patient);
        //patientRepository.save(patient);

        log.info("Patients");
        log.info("Number of patients = {}", patientRepository.count());


        // registration account
        credentials.setEmail("spp@abv.bg");
        credentials.setPassword(passwordEncoder.encode("pass"));
        credentials.setApplicationUserRole(ApplicationUserRole.ADMIN);
        patient.setCredentials(credentials);
        credentials.setBaseUser(patient);
        patientRepository.save(patient);
        credentialsRepository.save(credentials);

        log.info("Credentials");
        log.info("Number of credential  = {}", credentialsRepository.count());


        // appointment
        LocalDateTime ldt = LocalDateTime.of(2023, 1, 9, 13, 5);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf.toString());
        appointment.setDateTimeOfTheAppointment(ldt);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        log.info("Appointment = {}", appointment);
        appointmentRepository.save(appointment);

        log.info("Appointments");
        log.info("Number of appointments = {}", appointmentRepository.count());
    }



    private void createPatients() {
        // patient1
        patient1.setFirstName("Jon");
        patient1.setLastName("Brown");
        patient1.setPhone("089333");
        patient1.setComment("Any comments!!!");
        log.info("Patient created = {}", patient1);

        // patient 2
        patient2.setFirstName("Goro");
        patient2.setLastName("Stoyanov");
        patient2.setPhone("63463563");
        patient2.setComment("Any comments!!!");
        log.info("Patient created = {}", patient2);

        // patient3
        patient3.setFirstName("Nikolai");
        patient3.setLastName("Petrov");
        patient3.setPhone("436354");
        patient3.setComment("Any comments!!!");
        log.info("Patient created = {}", patient3);

        log.info("Patients");
        log.info("Number of patients = {}", patientRepository.count());
    }


    private void createDoctors() {

        doctor1.setUniqueId("1000000001");
        doctor1.setFirstName("Stefan");
        doctor1.setLastName("Ivanov");
        doctor1.setSpecialty("Pediatricians");
        doctor1.setPhone("634653465");
        doctor1.setDescription("Some doctor description");
        log.info("Doctor created = {}", doctor1);

        doctor2.setUniqueId("1000000002");
        doctor2.setFirstName("Goro");
        doctor2.setLastName("Petkov");
        doctor2.setSpecialty("Physiatrists");
        doctor2.setPhone("6534673432");
        doctor2.setDescription("Some doctor description");
        log.info("Doctor created = {}", doctor2);

        doctor3.setUniqueId("1000000003");
        doctor3.setFirstName("John");
        doctor3.setLastName("Doe");
        doctor3.setSpecialty("Sports Medicine Specialists");
        doctor3.setPhone("08987777777");
        doctor3.setDescription("Some doctor description");
        log.info("Doctor created = {}", doctor3);

        log.info("Doctors");
        log.info("Number of doctors = {}", doctorRepository.count());
    }

    private void createCredentials() {
        //Patients
        //credential1
        credentials1.setEmail("patient1@abv.bg");
        credentials1.setPassword(passwordEncoder.encode("pass"));
        credentials1.setApplicationUserRole(ApplicationUserRole.PATIENT);
        patient1.setCredentials(credentials1);
        credentials1.setBaseUser(patient1);
        patientRepository.save(patient1);
        credentialsRepository.save(credentials1);

        //credential2
        credentials2.setEmail("patient2@abv.bg");
        credentials2.setPassword(passwordEncoder.encode("pass"));
        credentials2.setApplicationUserRole(ApplicationUserRole.PATIENT);
        patient2.setCredentials(credentials2);
        credentials2.setBaseUser(patient2);
        patientRepository.save(patient2);
        credentialsRepository.save(credentials2);

        //credential3
        credentials3.setEmail("patient3@abv.bg");
        credentials3.setPassword(passwordEncoder.encode("pass"));
        credentials3.setApplicationUserRole(ApplicationUserRole.PATIENT);
        patient3.setCredentials(credentials3);
        credentials3.setBaseUser(patient3);
        patientRepository.save(patient3);
        credentialsRepository.save(credentials3);

        log.info("Credentials");
        log.info("Number of credential  = {}", credentialsRepository.count());

        //Credential4
        credentials4.setEmail("doctor1@abv.bg");
        credentials4.setPassword(passwordEncoder.encode("pass"));
        credentials4.setApplicationUserRole(ApplicationUserRole.DOCTOR);
        doctor1.setCredentials(credentials4);
        credentials4.setBaseUser(doctor1);
        doctorRepository.save(doctor1);
        credentialsRepository.save(credentials4);

        //credential5
        credentials5.setEmail("doctor2@abv.bg");
        credentials5.setPassword(passwordEncoder.encode("pass"));
        credentials5.setApplicationUserRole(ApplicationUserRole.DOCTOR);
        doctor2.setCredentials(credentials5);
        credentials5.setBaseUser(doctor2);
        doctorRepository.save(doctor2);
        credentialsRepository.save(credentials5);

        //credential6
        credentials6.setEmail("doctor3@abv.bg");
        credentials6.setPassword(passwordEncoder.encode("pass"));
        credentials6.setApplicationUserRole(ApplicationUserRole.DOCTOR);
        doctor3.setCredentials(credentials6);
        credentials6.setBaseUser(doctor3);
        doctorRepository.save(doctor3);
        credentialsRepository.save(credentials6);

    }

    public void makeAppointment(){

        LocalDateTime ldt1 = LocalDateTime.of(2023, 2, 10, 13, 5);
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf1.toString());
        appointment1.setDateTimeOfTheAppointment(ldt1);
        appointment1.setPatient(patient);
        appointment1.setDoctor(doctor2);
        log.info("Appointment = {}", appointment1);
        appointmentRepository.save(appointment1);

        log.info("Appointments");
        log.info("Number of appointments = {}", appointmentRepository.count());


        LocalDateTime ldt2 = LocalDateTime.of(2023, 3, 11, 13, 5);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf2.toString());
        appointment2.setDateTimeOfTheAppointment(ldt2);
        appointment2.setPatient(patient3);
        appointment2.setDoctor(doctor3);
        log.info("Appointment = {}", appointment2);
        appointmentRepository.save(appointment2);

        log.info("Appointments");
        log.info("Number of appointments = {}", appointmentRepository.count());


        LocalDateTime ldt3 = LocalDateTime.of(2024, 12, 11, 13, 5);
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf3.toString());
        appointment3.setDateTimeOfTheAppointment(ldt3);
        appointment3.setPatient(patient3);
        appointment3.setDoctor(doctor);
        log.info("Appointment = {}", appointment3);
        appointmentRepository.save(appointment3);


        LocalDateTime ldt4 = LocalDateTime.of(2025, 8, 10, 13, 5);
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf4.toString());
        appointment4.setDateTimeOfTheAppointment(ldt4);
        appointment4.setPatient(patient);
        appointment4.setDoctor(doctor3);
        log.info("Appointment = {}", appointment4);
        appointmentRepository.save(appointment4);


        LocalDateTime ldt5 = LocalDateTime.of(2024, 3, 10, 13, 5);
        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern(Constants.DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT);
        log.info("Formatted date = {}", dtf5.toString());
        appointment5.setDateTimeOfTheAppointment(ldt5);
        appointment5.setPatient(patient);
        appointment5.setDoctor(doctor1);
        log.info("Appointment = {}", appointment5);
        appointmentRepository.save(appointment5);

        log.info("Appointments");
        log.info("Number of appointments = {}", appointmentRepository.count());

    }

    //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    private LocalDateTime formatDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeFormatter) {
        String dateTimeString = dateTimeFormatter.format(dateTime);
        LocalDateTime formattedDateTime = LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        return formattedDateTime;
    }

}
