package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends CrudRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByEmail(String email);



}
