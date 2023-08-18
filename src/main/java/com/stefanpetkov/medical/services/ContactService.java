package com.stefanpetkov.medical.services;

import com.stefanpetkov.medical.commands.ContactCommand;
import com.stefanpetkov.medical.converter.contact.ContactCommandToContact;
import com.stefanpetkov.medical.converter.contact.ContactToContactCommand;
import com.stefanpetkov.medical.domain.Contact;
import com.stefanpetkov.medical.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactToContactCommand  toContactCommand;
    private final ContactCommandToContact  toContact;

    public Iterable<Contact> getAllContacts(){
        return  contactRepository.findAll();
    }

    public void saveContact(ContactCommand contactCommand){
        log.info("ContactService::saveContact saving command = {}", contactCommand);
        Contact newContact = toContact.convert(contactCommand);
        Contact savedContact = contactRepository.save(newContact);
        log.info("Saved command = {}", savedContact);
    }

    public void deleteById(Long contactId) {
        log.info("ContactService::deleteById, id passed = {}", contactId);
        contactRepository.deleteById(contactId);
        log.info("ContactService::deleteById, deleted");
    }
}

