package com.sardina;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Processor {

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.processWorkOrders();

    } // ++++++++++ end Processor.MAIN ++++++++++

    Set<WorkOrder> initialSet = new HashSet<>();
    Set<WorkOrder> assignedSet = new HashSet<>();
    Set<WorkOrder> in_progressSet = new HashSet<>();
    Set<WorkOrder> doneSet = new HashSet<>();

    HashMap<Status, HashSet> mapWorkOrders = new HashMap<>();

    public void processWorkOrders() {
       Thread runProcessor = new Thread();
       while (true) {
           try {
               runProcessor.start();
               readIt();
               moveIt();
               runProcessor.sleep(5000l);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    } // ===== end processWorkOrders() fn =====

    private void moveIt() {
        //TODO: move work orders in map from one state to another

        in_progressSet.stream()
                .forEach(workOrder -> doneSet.add(workOrder));
        assignedSet.stream()
                .forEach(workOrder -> in_progressSet.add(workOrder));
        initialSet.stream()
                .forEach(workOrder -> assignedSet.add(workOrder));

        //puts sets in hashmap according to Status
        mapWorkOrders.put(Status.INITIAL, (HashSet) initialSet);
        mapWorkOrders.put(Status.ASSIGNED, (HashSet) assignedSet);
        mapWorkOrders.put(Status.IN_PROGRESS, (HashSet) in_progressSet);
        mapWorkOrders.put(Status.DONE, (HashSet) doneSet);

    } // ---- end moveIt() method ----

    private void readIt() {
        //TODO:  read the json files into WorkOrders and put in map
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                try {
                    //JSON to Java Object
                    ObjectMapper mapper = new ObjectMapper();
                    WorkOrder wo = mapper.readValue(f, WorkOrder.class);
                    //check if file currently Status.DONE, if True -> delete
                    if (wo.getStatus().equals(Status.DONE)) {
                        System.out.println("Work order #: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() + ", is COMPLETE.");

                        f.delete();
                    }
                    //if file is not Status.DONE, add wo to correct set
                    else {
                        if (wo.getStatus().equals(Status.INITIAL)) {
                            System.out.println("NEW WORK ORDER// Order #: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() + ".");
                            initialSet.add(wo);
                        }
                        else if (wo.getStatus().equals(Status.ASSIGNED)) {
                            assignedSet.add(wo);
                        }
                        else if (wo.getStatus().equals(Status.IN_PROGRESS)) {
                            in_progressSet.add(wo);
                        }
                        else doneSet.add(wo);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                } //end try-catch
            } //end if
        } //end forEach loop
    } // ---- end readIt() method ----

} // ***** end 'Processor' class *****

//TODO:    ***** #2 *****
//TODO: In Processor have a public static void main that creates an instance of Processor and calls the instance method to that loops to process the work order files. [included]

//TODO: The processWorkOrders method should :                                                                                                  ✅(1).Run forever in a loop. Have the loop sleep for a five seconds (or longer).                                                           (2). Have a map with Status as the key and a Set of work orders for the value                                                                 => print out the map                                                                                                                     =>  move work orders from one map entry to the next (i.e., From IN_PROGRESS to DONE; from ASSIGNED TO IN_PROGRESS; from                      INITIAL to ASSIGNED). A work order should only transitioned once per loop.                                                           => print out the map                                                                                                                (3). Check for new work orders (files). For each new work order                                                                               => reads the file into a WorkOrder object                                                                                                => removes the file                                                                                                                      => print out the work order                                                                                                              => puts the work order in the INITIAL map entry.

//TODO: Here's the code to sleep for 5 seconds (5000 milliseconds) ✅

//    try {
//            Thread.sleep(5000l);
//            } catch (InterruptedException e) {
//            e.printStackTrace();
//            }

//TODO: Here's the code to read files with a "json" extension ✅

//    File currentDirectory = new File(".");
//    File files[] = currentDirectory.listFiles();
//    for (File f : files) {
//            if (f.getName().endsWith(".json")) {
//            // f is a reference to a json file
//
//            // f.delete(); will delete the file
//            }
//            }


