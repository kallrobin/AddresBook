package com.gmail.kallrobin92.addressBookFinal;

import java.util.logging.Logger;

/**
 * Created by Robin Gk on 2016-12-06 as a school project.
 * email kallrobin92@gmail.com
 */
class ConsoleCommunicator {
    private final static Logger log = Logger.getLogger(ConsoleCommunicator.class.getName());

    /**
     * Method to print the "help" line to the user
     */
    void printHelp() {
        printToUser("List of valid commands" +
                "\n------------------------------------------------------------------------" +
                "\nlist\tList all the contacts in the address book" +
                "\nadd\tAdd a contact to the address book \"add name lastname email@email.com\"" +
                "\nsearch\tSearch for contacts in the address book by \"Starts with\"" +
                "\nquit\tClose the program and save to file" +
                "\ndelete\tDeletes a contact with the given uuid");
    }

    /**
     * Method to print if the amount of parameters was incorrect
     */
    void invalidParameters() {
        printToUser("Invalid amount of parameters");
    }

    /**
     * Pints the given string to the console
     *
     * @param print String to print to console
     */
    void printToUser(String print) {
        log.fine("Printing: " + print + " to console");
        System.out.println(print);
    }

    /**
     * Reads the user input.
     *
     * @return returns the input as an array of Strings
     */
    String[] readInput(String input) {
        String commandArr[];
        commandArr = input.split(" ");            //Splits the string and puts it in an array
        log.info("Input: " + input + " registered");
        return commandArr;
    }
}
