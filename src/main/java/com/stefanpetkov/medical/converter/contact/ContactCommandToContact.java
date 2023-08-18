package com.stefanpetkov.medical.converter.contact;

import com.stefanpetkov.medical.commands.ContactCommand;
import com.stefanpetkov.medical.domain.Contact;
import com.stefanpetkov.medical.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContactCommandToContact implements Converter<ContactCommand, Contact> {

    private final ContactRepository contactRepository;

    @Override
    public Contact convert(ContactCommand command) {
        log.info("Converting command to contact = {}", command);

        validateCommand(command);

        Optional<Contact> contactOptional = Optional.empty();
        if (command.getContactId() != null) {
            contactOptional = contactRepository.findById(command.getContactId());
        }

        Contact contact;

        if (contactOptional.isPresent()) {
            contact = contactOptional.get();
            contact.setId(command.getContactId());
            contact.setName(command.getContactName());
            contact.setEmail(command.getContactEmail());
            contact.setPhone((command.getContactPhone()));
            contact.setMessage(command.getContactMessage());
            contact.setCreatedMessageTimestamp(command.getContactCreatedMessageTimestamp());
            log.info("Contact converted = {}", contact);
            return contact;
        }
//


        contact = new Contact();
        contact.setId(command.getContactId());
        contact.setName(command.getContactName());
        contact.setEmail(command.getContactEmail());
        contact.setPhone(command.getContactPhone());
        contact.setMessage(command.getContactMessage());
        contact.setCreatedMessageTimestamp(command.getContactCreatedMessageTimestamp());


        log.info("Doctor converted = {}", contact);
        return contact;
    }


    private void validateCommand(ContactCommand contact) {
        String errorMsg = "";
        if (contact == null) {
            errorMsg += "Command is NULL!";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        //todo validate other fields too, create custom Exception class
    }
}
