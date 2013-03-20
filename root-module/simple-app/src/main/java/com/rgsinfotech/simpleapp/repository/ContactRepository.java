package com.rgsinfotech.simpleapp.repository;

import com.rgsinfotech.simpleapp.model.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides the repository methods for contacts.
 * @author Petri Kainulainen
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
