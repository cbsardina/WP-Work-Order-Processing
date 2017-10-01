package com.sardina;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.io.File;
import java.io.FileWriter;
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

    public void processWorkOrders() {

        while (true) {
            try {
                readIt();
                moveIt();
                clearIt();
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // ===== end processWorkOrders() fn =====

    private void clearIt() {
        initialSet.removeAll(initialSet);
        assignedSet.removeAll(assignedSet);
        in_progressSet.removeAll(in_progressSet);
        doneSet.removeAll(doneSet);
    }

    private void moveIt() {
        //puts sets in hashmap according to Status
        HashMap<Status, HashSet> mapWorkOrders = new HashMap<>();

        mapWorkOrders.put(Status.INITIAL, (HashSet) initialSet);
        mapWorkOrders.put(Status.ASSIGNED, (HashSet) assignedSet);
        mapWorkOrders.put(Status.IN_PROGRESS, (HashSet) in_progressSet);
        mapWorkOrders.put(Status.DONE, (HashSet) doneSet);

        if (!initialSet.isEmpty()){
            System.out.println("--------------------------------------------------");
            System.out.println("INITIAL work orders: ");
            System.out.println(mapWorkOrders.get(initialSet));
            System.out.println(".........................");
        }
        if (!assignedSet.isEmpty()){
            System.out.println("ASSIGNED work orders: ");
            System.out.println(mapWorkOrders.get(assignedSet));
            System.out.println(".........................");
        }
        if (!in_progressSet.isEmpty()) {
            System.out.println("IN-PROGRESS work orders: ");
            System.out.println(mapWorkOrders.get(in_progressSet));
            System.out.println(".........................");
        }
        if (!in_progressSet.isEmpty()) {
            System.out.println("DONE work orders: ");
            System.out.println(mapWorkOrders.get(doneSet));
            System.out.println(".........................");
        }
        if (initialSet.isEmpty() && assignedSet.isEmpty() && in_progressSet.isEmpty() && doneSet.isEmpty()) {

        }
        else {
            System.out.println("Entire work order map: ");
            System.out.println(mapWorkOrders.entrySet());
            System.out.println("--------------------------------------------------");System.out.println("");
        }
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
                    //if file is not Status.DONE, add wo to correct set
                        if (wo.getStatus().equals(Status.INITIAL)) {
                            System.out.println("* * * * * NEW WORK ORDER * * * * *");
                            System.out.println("NEW WORK ORDER ENTERED// Order #: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() + ".");System.out.println("");
                            initialSet.add(wo);
                            wo.setStatus(Status.ASSIGNED);
                            reWriteFile(wo);
                        }
                        else if (wo.getStatus().equals(Status.ASSIGNED)) {
                            assignedSet.add(wo);
                            wo.setStatus(Status.IN_PROGRESS);
                            reWriteFile(wo);
                        }
                        else if (wo.getStatus().equals(Status.IN_PROGRESS)) {
                            in_progressSet.add(wo);
                            wo.setStatus(Status.DONE);
                            reWriteFile(wo);
                        }
                        //check if file currently Status.DONE, if True -> delete
                        else if (wo.getStatus().equals(Status.DONE)) {
                            doneSet.add(wo);
                            System.out.println("* * * * * COMPLETE WORK ORDER * * * * *");
                            System.out.println("Work order #: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() + ", is COMPLETE.");System.out.println("");

                            f.delete();
                        }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                } //end try-catch
            } //end if
        } //end forEach loop
    } // ---- end readIt() method ----

    public void reWriteFile (WorkOrder wo) {
        String jsonWorkOrder;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonWorkOrder = mapper.writeValueAsString(wo);
            File jsonFile = new File(wo.getId() + ".json");
            FileWriter newFile = new FileWriter(jsonFile);

            newFile.write(jsonWorkOrder);
            newFile.close();
        }
            catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

    //TODO: probably don't need function if reader is updating the files.
//    Set<WorkOrder> tempSet1 = new HashSet<>();
//    Set<WorkOrder> tempSet2 = new HashSet<>();
//
//        tempSet1.addAll(assignedSet);
//                assignedSet.removeAll(assignedSet);
//                assignedSet.addAll(initialSet);
//                initialSet.removeAll(initialSet);
//                tempSet2.addAll(in_progressSet);
//                in_progressSet.removeAll(in_progressSet);
//                in_progressSet.addAll(tempSet1);
//                tempSet1.removeAll(tempSet1);
//                doneSet.removeAll(doneSet);
//                doneSet.addAll(tempSet2);




