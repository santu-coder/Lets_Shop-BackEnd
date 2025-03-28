package com.letsshop.services.impl;

import com.letsshop.entities.Contact;
import com.letsshop.repository.ContactRepository;
import com.letsshop.services.interfac.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

}
