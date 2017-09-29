package com.sardina;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator extends WorkOrder{

    public void createWorkOrders() {
        //TODO: read input, create work orders and write as json files
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

    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }


}
//TODO:    ***** #1 *****
//TODO: In Creator have a public static void main that creates an instance of Creator and calls the instance method that loops to get the user input and create work order files. Set an id when the work order is created. Persist the work order to a file in JSON with the id as the file name.
