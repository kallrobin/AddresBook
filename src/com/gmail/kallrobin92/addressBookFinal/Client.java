package com.gmail.kallrobin92.addressBookFinal;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Logger;

import static com.gmail.kallrobin92.addressBookFinal.Application.serverAddressBook;

/**
 * Created by Robin Gk on 2016-12-02 as a school project.
 * email kallrobin92@gmail.com
 */
class Client {
    private int contactCount = 0;


    private final static Logger log = Logger.getLogger(Client.class.getName());

    private String hostName;
    private int portNumber;


    Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    void tryToConnect() {
        new Thread(() -> {
            try {
                addressBookClient();
                log.info("Connected to server.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void addressBookClient() {
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;

            toServer.println("getall");

            while ((inputLine = fromServer.readLine()) != null) {
                if (inputLine.equals("End of file")) break;
                String[] newContact = inputLine.split(" ");
                serverAddressBook.add(new Contact(newContact[1], newContact[2], newContact[3], newContact[0]));
                contactCount++;
            }
            if (serverAddressBook != null) {
                System.out.println(contactCount + " contacts added from " + hostName + " on port " + portNumber);
            }

            toServer.println("exit");
        } catch (ConnectException con) {
            System.out.println("Connection could not be established to: " + hostName + " on port: " + portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
