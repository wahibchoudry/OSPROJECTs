/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author user
 */
public class Job implements Runnable {
    
    private Printer printer;
	private String jobName;  
        MainSyncGui main;
	public Job(Printer printer, String jobName, MainSyncGui gui) {
		this.printer = printer;
		this.jobName = jobName;
                this.main=gui;
	}
	@Override
	public void run() {
		//System.out.println("Job sent to printer:"+ jobName);
                main.readyQueue.add(jobName);
                main.updateGui();
		printer.print(jobName,main);
	}
}
