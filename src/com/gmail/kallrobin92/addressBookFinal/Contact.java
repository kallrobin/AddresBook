package com.gmail.kallrobin92.addressBookFinal;

import java.io.Serializable;
import java.util.UUID;

/**
 * A contact class for each contact in the address book.
 * Created by Robin Gk on 2016-11-24 as a school project.
 * email kallrobin92@gmail.com
 */
class Contact implements Serializable, Comparable<Contact> {

    private String firstName;
    private String lastName;
    private String eMail;
    private String uuid;

    /**
     * Constructor for the contact class, adds a UUID to each contact.
     * Since the chance for a UUID collision is almost zero i didn't implement a try for that.
     *
     * @param firstName The contacts first name
     * @param lastName  The contacts last name
     * @param eMail     The contacts E-mail
     */
    Contact(String firstName, String lastName, String eMail) {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.eMail = eMail;
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    /**
     * Constructor for the contact class for contacts from an external source with UUID already given
     *
     * @param firstName The contacts first name
     * @param lastName  The contacts last name
     * @param eMail     The contacts E-mail
     * @param uuid      The contacts UUID
     */
    Contact(String firstName, String lastName, String eMail, String uuid) {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.eMail = eMail;
        this.uuid = uuid;
    }

    String geteMail() {
        return eMail;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getUuid() {
        return uuid;
    }

    @Override
    public int compareTo(Contact o) {
        return this.getFirstName().compareTo(o.getFirstName());
    }
}
