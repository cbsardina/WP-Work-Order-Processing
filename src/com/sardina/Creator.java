package com.sardina;

import java.util.Scanner;

public class Creator extends WorkOrder {

    public void createWorkOrders() {
        //TODO: read input, create work orders and write as json files

    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new 5 digit work order number # # # # # : ");
            int woId = scanner.nextInt();
        System.out.println("Enter a work order description: ");
            String woDescription = scanner.nextLine();
        System.out.println("Enter your name: ");
            String woSenderName = scanner.nextLine();

        creator.setId(woId);
        creator.setDescription(woDescription);
        creator.setSenderName(woSenderName);
        creator.setStatus(Status.INITIAL);

    }


}
//TODO:    ***** #1 *****
//TODO: In Creator have a public static void main that creates an instance of Creator and calls the instance method that loops to get the user input and create work order files. Set an id when the work order is created. Persist the work order to a file in JSON with the id as the file name.
