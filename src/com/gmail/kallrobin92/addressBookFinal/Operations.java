package com.gmail.kallrobin92.addressBookFinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.gmail.kallrobin92.addressBookFinal.Application.serverAddressBook;
import static com.gmail.kallrobin92.addressBookFinal.Application.mergedAddressBook;

/**
 * All the logical operations for the address book application
 * <p>
 * Created by Robin Gk on 2016-11-25 as a school project.
 * email kallrobin92@gmail.com
 */
class Operations {
    private final static Logger log = Logger.getLogger(Operations.class.getName());
    private ArrayList<Contact> addressBook;

    /**
     * A constructor to be able to use the same address book object as in the application class
     *
     * @param addressBook The input address book
     */
    public Operations(ArrayList<Contact> addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a contact to the address book.
     *
     * @param commandArr Parameters to be input to the address book
     */
    void add(String commandArr[], ArrayList<Contact> addressBook) {
        addressBook.add(new Contact(commandArr[1], commandArr[2], commandArr[3]));
        System.out.println(commandArr[1].substring(0, 1).toUpperCase() +
                commandArr[1].substring(1).toLowerCase() + " added to the address book");
        log.info(commandArr[1] + " " + commandArr[2] + " added to the address book");
    }

    /**
     * Method to search for objects in addressBook
     *
     * @param search term to search for contacts that starts with the search term
     * @return Returns the search results as an ArrayList
     */
    ArrayList<Contact> search(String search, ArrayList<Contact> addressBook) {
        ArrayList<Contact> searchResults = new ArrayList<>();
        for (Contact contact : addressBook) {
            if (contact.getFirstName().toLowerCase().startsWith(search) ||
                    contact.getLastName().toLowerCase().startsWith(search)) {
                searchResults.add(contact);
            }
        }
        if (searchResults.isEmpty()) {
            if (addressBook == serverAddressBook) {
                System.out.println("Couldn't find any results for " + search + " in server address book");
                log.info("No results found for " + search);
            } else {
                System.out.println("Couldn't find any results for " + search + " in local address book");
            }
        }
        log.info(searchResults.size() + " results found for " + search);
        return searchResults;
    }

    /**
     * Deletes a contact with the given UUID
     * Contact that where fetched form the server can't be deleted.
     * If the user tries to do so, they will get a message.
     *
     * @param uuid The unique id for the contact of which to be deleted
     */
    void delete(String uuid) {
        boolean resultFound = false;
        for (int i = 0; i < addressBook.size(); i++) {
            if (uuid.equals(addressBook.get(i).getUuid())) {
                System.out.println("Deleting " + addressBook.get(i).getFirstName());
                log.info(addressBook.get(i).getFirstName() + " deleted from the address book");
                addressBook.remove(i);
                resultFound = true;
                break;
            }
        }

        if (!resultFound) {
            for (int i = 0; i < serverAddressBook.size(); i++) {
                if (uuid.equals(serverAddressBook.get(i).getUuid())) {
                    System.out.println("Can't delete contacts from the server.");
                    return;
                }
            }
            System.out.println("Could not find a contact with uuid: " + uuid);
            log.info("Could not find a contact with uuid: " + uuid);
        }

    }

    /**
     * Formats an address book for printing
     *
     * @param addressBook The address book to be formatted
     * @return A formatted String of the full contact list
     */
    String formatAddressBook(ArrayList<Contact> addressBook) {
        String formattedString = "";
        for (int i = 0; i < addressBook.size(); i++) {
            formattedString += formatContact(i, addressBook);
        }
        log.info("Address book formatted to string");
        return formattedString;
    }

    /**
     * Formats a Contact on the index of the input in the address book
     *
     * @param index       The index of the contact to be returned
     * @param addressBook The address book to pick a contact out of
     * @return A formatted String of the contact at the index
     */
    private String formatContact(int index, ArrayList<Contact> addressBook) {
        return "-------------------------------------" +
                "\nId: " + addressBook.get(index).getUuid() +
                "\nFirst name: " + addressBook.get(index).getFirstName() +
                "\nLast name: " + addressBook.get(index).getLastName() +
                "\nEmail: " + addressBook.get(index).geteMail() + "\n\n";
    }

    void mergeLocalAndCentral(){
            mergedAddressBook.clear();
            mergedAddressBook.addAll(addressBook);
            mergedAddressBook.addAll(serverAddressBook);
        }

    /**
     * Takes an ArrayList and returns a sorted version of it.
     *
     * @param toSort ArrayList to sort
     * @return Returns an sorted ArrayList of the given array list
     */
    ArrayList<Contact> sort(ArrayList<Contact> toSort) {
        ArrayList<Contact> sortedAddressBook = new ArrayList<>(toSort);
        try {
            if (toSort.isEmpty()) {
                if (toSort == addressBook) System.out.println("Local address book is empty");
                if (toSort == serverAddressBook) System.out.println("Central address book is empty");
                log.info("An address book was empty when trying to sort");
            } else {
                Collections.sort(sortedAddressBook);
                log.info("Address book sorted");
            }
        } catch (NullPointerException e) {
            System.out.println("Address Book is empty");
            log.log(Level.SEVERE, "Address book was empty when trying to sort");
        }
        return sortedAddressBook;
    }

    /**
     * A method for the fetching of central databases
     *
     * @param hostName What host name or ip to connect to
     * @param port     What port to go trough
     */
    void fetchFromServer(List<String> hostName, List<Integer> port) {
        for (int i = 0; i < hostName.size(); i++) {
            new Client(hostName.get(i), port.get(i)).tryToConnect();
        }
    }
}
