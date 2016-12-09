package com.gmail.kallrobin92.addressBookFinal;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Robin Gk on 2016-11-24 as a school project.
 * email kallrobin92@gmail.com
 */
class FileHandler {
    private final static Logger log = Logger.getLogger(FileHandler.class.getName());
    private File saveSer = new File("save.ser");
    private ArrayList<Contact> addressBook;

    public FileHandler(ArrayList<Contact> addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Method to serialize the address book to saveSer file
     */
    synchronized void serialize(ArrayList<Contact> toSave) {
        try (FileOutputStream fos = new FileOutputStream(saveSer);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(toSave);
            log.info("Address book serialized");

            oos.flush();
            fos.flush();
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Savefile not found at " + saveSer + ". No file loaded");
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOExeption: ");
            e.printStackTrace();
        }
    }

    /**
     * A method to deserialize an ArrayList
     *
     * @return load the deserialized array from File save
     */
    ArrayList<Contact> deserialize() {
        ArrayList<Contact> load = null;
        try (FileInputStream is = new FileInputStream(saveSer);
             ObjectInputStream ois = new ObjectInputStream(is)) {

            load = (ArrayList<Contact>) ois.readObject();
            if (load != null) {
                System.out.println(load.size() + " contacts added from previous session.");
                log.info("Address book deserialized with " + load.size() + " contacts.");
            }

            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException eof) {
            System.out.println("EOFEXEPTION");
        } catch (IOException e) {
            System.out.println("Loadfile not found");
            log.log(Level.SEVERE, "Loadfile not found at " + saveSer);
        }

        if (load == null) {
            System.out.println("File empty, adding array list");
            return new ArrayList<>();
        } else {
            return load;
        }

    }


    void autoSave() {
        Thread saveThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5 * 1000);
                    serialize(addressBook);
                    log.fine("Address book saved by auto save");
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "Sleep interrupted ");
                    e.printStackTrace();
                }
            }
        });
        saveThread.start();
    }

}
