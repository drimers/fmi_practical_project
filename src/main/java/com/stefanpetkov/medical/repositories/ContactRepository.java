package com.stefanpetkov.medical.repositories;

import com.stefanpetkov.medical.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {


}
