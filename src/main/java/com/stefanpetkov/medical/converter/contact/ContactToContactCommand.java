package com.stefanpetkov.medical.converter.contact;

import com.stefanpetkov.medical.commands.ContactCommand;
import com.stefanpetkov.medical.domain.Contact;
import com.stefanpetkov.medical.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContactToContactCommand implements Converter<Contact, ContactCommand> {

    @Override
    public ContactCommand convert(Contact contact) {

        log.info("Converting Doctor to command = {}", contact);

        validateContact(contact);


        ContactCommand command = new ContactCommand();
        command.setContactId(contact.getId());
        command.setContactName(contact.getName());
        command.setContactEmail(contact.getEmail());
        command.setContactPhone(contact.getPhone());
        command.setContactMessage(contact.getMessage());
        command.setContactCreatedMessageTimestamp(contact.getCreatedMessageTimestamp());
        log.info("Command converted = {}", command);

        return command;
    }

    public void validateContact(Contact contact) {
        String errorMessage = "";
        if (contact == null) {
            errorMessage = "Contact not found";
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }
}
