package com.sardina;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator extends WorkOrder{

    public static void main(String args[]) {

        runCreator();

    } // ++++++++++ end Creator.MAIN ++++++++++

    public static void runCreator () {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter (N) for a new work order: ");
                String woN = scanner.nextLine().toUpperCase();

            if (woN.contentEquals("N")) {
                Creator creator = new Creator();
                creator.createWorkOrders();
            }
        }
    } // ----- end runCreator () -----

    public void createWorkOrders() {
            Scanner scanner = new Scanner(System.in);

            //ask for input, read, set as variables
            System.out.println("Enter your name: ");
                String woSenderName = scanner.nextLine();
            System.out.println("Enter a work order description: ");
                String woDescription = scanner.nextLine();
            System.out.println("Enter a new 5 digit work order number # # # # # : ");
                int woId = scanner.nextInt();

            //create w/o
            WorkOrder createWrkOrder = new WorkOrder();
                createWrkOrder.setId(woId);
                createWrkOrder.setDescription(woDescription);
                createWrkOrder.setSenderName(woSenderName);
                createWrkOrder.setStatus(Status.INITIAL);

            //write w/o as .json
            String jsonWorkOrder = "";
            try {
                ObjectMapper mapper = new ObjectMapper();
                jsonWorkOrder = mapper.writeValueAsString(createWrkOrder);
            } catch (IOException ex) {
                ex.printStackTrace(); }

            //Create VIN.json file for the new instance of vehicleInfo
            try {
                File jsonFile = new File(createWrkOrder.getId() + ".json");
                FileWriter newFile = new FileWriter(jsonFile);

                newFile.write(jsonWorkOrder);
                newFile.close();
            } catch (IOException ex) {
                ex.printStackTrace(); }
    }  // ----- end createOrders() fn -----

} // ***** end 'Creator' class *****