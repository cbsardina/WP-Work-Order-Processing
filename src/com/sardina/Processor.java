package com.sardina;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    } // ----- end processWorkOrders() -----

    private void clearIt() {
        initialSet.removeAll(initialSet);
        assignedSet.removeAll(assignedSet);
        in_progressSet.removeAll(in_progressSet);
        doneSet.removeAll(doneSet);
    } // ----- end clearIt() -----

    private void moveIt() {
        //puts sets in hashmap according to Status & prints to console
        HashMap<Status, HashSet> mapWorkOrders = new HashMap<>();

        mapWorkOrders.put(Status.INITIAL, (HashSet) initialSet);
        mapWorkOrders.put(Status.ASSIGNED, (HashSet) assignedSet);
        mapWorkOrders.put(Status.IN_PROGRESS, (HashSet) in_progressSet);
        mapWorkOrders.put(Status.DONE, (HashSet) doneSet);

        if (!(mapWorkOrders.get(Status.INITIAL).size() == 0)){
            System.out.println("--------------------------------------------------");
            System.out.println("INITIAL W/O's: " + mapWorkOrders.get(Status.INITIAL));
            System.out.println(".........................");
        }
        if (!(mapWorkOrders.get(Status.ASSIGNED).size() == 0)){
            System.out.println("ASSIGNED W/O's: " + mapWorkOrders.get(Status.ASSIGNED));
            System.out.println(".........................");
        }
        if (!(mapWorkOrders.get(Status.IN_PROGRESS).size() == 0)) {
            System.out.println("IN-PROGRESS W/O's: " + mapWorkOrders.get(Status.IN_PROGRESS));
            System.out.println(".........................");
        }
        if (!(mapWorkOrders.get(Status.DONE).size() == 0)) {
            System.out.println("DONE W/O's: " + mapWorkOrders.get(Status.DONE));
            System.out.println(".........................");
        }
        if (!(mapWorkOrders.get(Status.INITIAL).size() == 0) || !(mapWorkOrders.get(Status.ASSIGNED).size() == 0) || !(mapWorkOrders.get(Status.IN_PROGRESS).size() == 0) || !(mapWorkOrders.get(Status.DONE).size() == 0)) {
            System.out.println("Entire work order map: ");
            System.out.println(mapWorkOrders.entrySet());
            System.out.println("--------------------------------------------------");System.out.println("");System.out.println("");
        }
    } // ---- end moveIt() ----

    private void readIt() {
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
                            System.out.println("W/O#: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() + ".");System.out.println("");
                            initialSet.add(wo);
                            reWriteFile(wo, Status.ASSIGNED);
                        }
                        else if (wo.getStatus().equals(Status.ASSIGNED)) {
                            assignedSet.add(wo);
                            reWriteFile(wo, Status.IN_PROGRESS);
                        }
                        else if (wo.getStatus().equals(Status.IN_PROGRESS)) {
                            in_progressSet.add(wo);
                            reWriteFile(wo, Status.DONE);
                        }
                        //check if file currently Status.DONE, if True -> delete
                        else if (wo.getStatus().equals(Status.DONE)) {
                            doneSet.add(wo);
                            System.out.println("* * * * * COMPLETE WORK ORDER * * * * *");
                            System.out.println("W/O#: " + wo.getId() + ", Description: " + wo.getDescription() + ", Submitted by: " + wo.getSenderName() +".");System.out.println("");

                            f.delete();
                        }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                } //end try-catch
            } //end if
        } //end forEach loop
    } // ---- end readIt() ----

    public void reWriteFile (WorkOrder wo, Status status) {
        WorkOrder tempWO = new WorkOrder();
            tempWO.setStatus(status);
            tempWO.setSenderName(wo.getSenderName());
            tempWO.setDescription(wo.getDescription());
            tempWO.setId(wo.getId());
        String jsonWorkOrder;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonWorkOrder = mapper.writeValueAsString(tempWO);
            File jsonFile = new File(tempWO.getId() + ".json");
            FileWriter newFile = new FileWriter(jsonFile);

            newFile.write(jsonWorkOrder);
            newFile.close();
        }
            catch (IOException ex) {
            ex.printStackTrace();
        }
    }

} // ***** end 'Processor' class *****
