package com.sardina;

public class Creator {

    public void createWorkOrders() {
        //TODO: read input, create work orders and write as json files

    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }


}
//TODO:    ***** #1 *****
//TODO: In Creator have a public static void main that creates an instance of Creator and calls the instance method that loops to get the user input and create work order files. Set an id when the work order is created. Persist the work order to a file in JSON with the id as the file name.
