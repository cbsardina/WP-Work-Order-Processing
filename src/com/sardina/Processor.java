package com.sardina;

public class Processor {

    public void processWorkOrders() {
        moveIt();
        readIt();
    }

    private void moveIt() {
        //TODO: move work orders in map from one state to another
    }

    private void readIt() {
        //TODO:  read the json files into WorkOrders and put in map
    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }


}
//TODO:    ***** #2 *****
//TODO: In Processor have a public static void main that creates an instance of Processor and calls the instance method to that loops to process the work order files.

//TODO: The processWorkOrders method should ||(1).Run forever in a loop. Have the loop sleep for a five seconds (or longer). ||(2). Have a map with Status as the key and a Set of work orders for the value |=> print out the map |=>  move work orders from one map entry to the next (i.e., From IN_PROGRESS to DONE; from ASSIGNED TO IN_PROGRESS; from INITIAL to ASSIGNED). A work order should only transitioned once per loop. |=> print out the map || (3). Check for new work orders (files). For each new work order => reads the file into a WorkOrder object |=> removes the file |=> print out the work order |=> puts the work order in the INITIAL map entry.

//TODO: Here's the code to sleep for 5 seconds (5000 miliseconds)

//    try {
//            Thread.sleep(5000l);
//            } catch (InterruptedException e) {
//            e.printStackTrace();
//            }

//TODO: Here's the code to read files with a "json" extension

//    File currentDirectory = new File(".");
//    File files[] = currentDirectory.listFiles();
//    for (File f : files) {
//            if (f.getName().endsWith(".json")) {
//            // f is a reference to a json file
//
//            // f.delete(); will delete the file
//            }
//            }
