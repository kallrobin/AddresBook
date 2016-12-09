package com.gmail.kallrobin92.addressBookFinal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.Arrays.asList;


/**
 * Created by Robin Gk on 2016-11-24 as a school project.
 * email kallrobin92@gmail.com
 */
class Application {

    private final static Logger log = Logger.getLogger(Application.class.getName());
    private ArrayList<Contact> addressBook = new ArrayList<>();
    static ArrayList<Contact> serverAddressBook = new ArrayList<>();

    private List<String> hosts = Arrays.asList("localhost", "localhost");
    private List<Integer> ports = Arrays.asList(61616, 60606);


    /**
     * Runs the address book program.
     */
    void run() {
        log.info("Program started");

        FileHandler io = new FileHandler(addressBook);

        addressBook = io.deserialize();

        Operations operations = new Operations(addressBook);

        ConsoleCommunicator consoleCommunicator = new ConsoleCommunicator();

        operations.fetchFromServer(hosts, ports);

        io.autoSave();


        consoleCommunicator.printToUser("Welcome\nTo show valid commands type \"help\"");
        while (true) {

            Scanner scan = new Scanner(System.in);

            String input[] = consoleCommunicator.readInput(scan.nextLine().toLowerCase());
            consoleCommunicator.printToUser("");

            switch (input[0]) {
                case ("add"):
                    if (input.length == 4) {
                        operations.add(input, addressBook);
                    } else {
                        log.info("User \"add\" input parameters incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                case ("list"):
                    if (input.length == 1) {
                        consoleCommunicator.printToUser(operations.formatAddressBook(operations.sort(addressBook)));
                        consoleCommunicator.printToUser(operations.formatAddressBook(operations.sort(serverAddressBook)));
                    } else {
                        log.info("User \"list\" input parameters incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                case ("search"):
                    if (input.length == 2) {
                        consoleCommunicator.printToUser(
                                operations.formatAddressBook(
                                        operations.sort(operations.search(input[1], addressBook))));

                        consoleCommunicator.printToUser(
                                operations.formatAddressBook(
                                        operations.sort(operations.search(input[1], serverAddressBook))));
                    } else {
                        log.info("User \"search\" input parameters incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                case ("delete"):
                    if (input.length == 2) {
                        operations.delete(input[1]);
                    } else {
                        log.info("User \"delete\" input parameters incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                case ("help"):
                    if (input.length == 1) {
                        consoleCommunicator.printHelp();
                    } else {
                        log.info("User \"help\" input parameters incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                case ("quit"):
                    if (input.length == 1) {
                        io.serialize(addressBook);
                        System.out.println("Address book saved to file" +
                                "\nClosing application");
                        log.info("Closing program");
                        scan.close();
                        System.exit(0);
                    } else {
                        log.info("User \"quit\" input incorrect");
                        consoleCommunicator.invalidParameters();
                    }
                    break;
                default:
                    System.out.println(input[0] + " is not a valid command" +
                            "\nTo show valid commands type \"help\"");
                    break;
            }
        }
    }

}
