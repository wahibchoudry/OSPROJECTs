/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */
class Printer {

    private static final int MAX_PERMIT = 1;
    private final Semaphore semaphore = new Semaphore(MAX_PERMIT, true);
    MainSyncGui main;
    

    public void print(String jobName, MainSyncGui main) {
        this.main = main;
        try {

            semaphore.acquire();

            //System.out.println("Printing Job: "+ jobName);	
            // main.runningProcess.append(jobName);
            main.runningProcess = jobName;
            main.readyQueue.poll();
            main.updateGui();

            Thread.sleep(4000);

            //System.out.println("Finished Job: "+ jobName);
            //main.finishedProcess.append(jobName);
            main.finishedQueue.add(jobName);
            main.updateGui();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    
    
    

}

}
