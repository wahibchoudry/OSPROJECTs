import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Schedulingg extends javax.swing.JFrame {
     private List<JProgressBar> progressBarList = new ArrayList<>();
     private List<String[]> processData;
     
    public Schedulingg() {
        initComponents();
    }
    
        public String getSelectedAlgorithm() {
        return (String) jComboBox1.getSelectedItem();
    }
    
            
    private void populateTable() {
        if (processData != null && !processData.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < processData.size(); i++) {
                String[] data = processData.get(i);
                int processID = Integer.parseInt(data[0]);
                String processName = data[1];
                int arrivalTime = Integer.parseInt(data[2]);
                int burstTime = Integer.parseInt(data[3]);
                model.addRow(new Object[]{processID, processName, arrivalTime, burstTime});
            }
        }
    }
    
        
public Schedulingg(List<String[]> processData) {
    this.processData = processData;
    initComponents();

    if (processData != null && !processData.isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < processData.size(); i++) {
            String[] data = processData.get(i);
            int processID = Integer.parseInt(data[0]);
            String processName = data[1];
            int arrivalTime = Integer.parseInt(data[2]);
            int burstTime = Integer.parseInt(data[3]);

            model.addRow(new Object[]{processID, processName, arrivalTime, burstTime});
        }
    }
}

private void updateGanttChart(List<int[]> ganttChartData) {
    StringBuilder ganttChart = new StringBuilder();
    for (int[] data : ganttChartData) {
        int processId = data[0];
        int startTime = data[1];
        int endTime = data[2];
        ganttChart.append("| P").append(processId)
                  .append(" [").append(startTime).append(" - ").append(endTime).append("] ");
    }
    ganttChart.append("|");
    jTextField5.setText(ganttChart.toString());
}

private void performFCFSScheduling() {
    int n = processData.size();
    List<int[]> ganttChartData = new ArrayList<>();
    double avgCompletionTime = 0;
    double avgTurnaroundTime = 0;
    double avgWaitingTime = 0;
    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] waitingTime = new int[n];
    int[] turnaroundTime = new int[n];
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    // Extract arrival and burst times from processData
    for (int i = 0; i < n; i++) {
        arrivalTime[i] = Integer.parseInt(processData.get(i)[2]);
        burstTime[i] = Integer.parseInt(processData.get(i)[3]);
    }

    // Create process list for sorting by arrival time
    List<int[]> processList = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        processList.add(new int[]{arrivalTime[i], burstTime[i], i});
    }

    // Sort process list by arrival time
    Collections.sort(processList, new Comparator<int[]>() {
        public int compare(int[] a, int[] b) {
            return Integer.compare(a[0], b[0]);
        }
    });

    int[] completionTime = new int[n];
    int currentTime = 0;

    // Compute scheduling data
    for (int i = 0; i < n; ++i) {
        int index = processList.get(i)[2];

        // Handle idle time
        if (processList.get(i)[0] > currentTime) {
            currentTime++;
            i--; // Recheck the current process in the next iteration
            continue;
        }

        int startTime = currentTime;
        completionTime[index] = currentTime + processList.get(i)[1];
        currentTime = completionTime[index];
        int endTime = currentTime;
        turnaroundTime[index] = completionTime[index] - processList.get(i)[0];
        waitingTime[index] = turnaroundTime[index] - processList.get(i)[1];

        // Add row to the table
        model.addRow(new Object[]{
            processData.get(index)[0],  // Process ID
            processData.get(index)[1],  // Process Name
            processList.get(i)[0],      // Arrival Time
            processList.get(i)[1],      // Burst Time
            completionTime[index],      // Completion Time
            waitingTime[index],         // Waiting Time
            turnaroundTime[index]       // Turnaround Time
        });
    }

    // Calculate averages
    for (int j = 0; j < n; j++) {
        avgCompletionTime += completionTime[j];
        avgTurnaroundTime += turnaroundTime[j];
        avgWaitingTime += waitingTime[j];
    }
    avgCompletionTime /= n;
    avgTurnaroundTime /= n;
    avgWaitingTime /= n;

    // Update the UI with averages
    jTextField2.setText(String.valueOf(avgWaitingTime));
    jTextField3.setText(String.valueOf(avgTurnaroundTime));
    jTextField1.setText(String.valueOf(avgCompletionTime));

    // Update Gantt chart
    updateGanttChart(ganttChartData);
}

private void performSJFNonPreemptiveScheduling() {
    int n = processData.size();
    double avgCompletionTime = 0;
    double avgTurnaroundTime = 0;
    double avgWaitingTime = 0;

    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] completionTime = new int[n];
    int[] waitingTime = new int[n];
    int[] turnaroundTime = new int[n];
    List<int[]> ganttChartData = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    for (int i = 0; i < n; i++) {
        arrivalTime[i] = Integer.parseInt(processData.get(i)[2]);
        burstTime[i] = Integer.parseInt(processData.get(i)[3]);
    }

    int currentTime = 0;
    int completedProcesses = 0;
    boolean[] isCompleted = new boolean[n];
    
    while (completedProcesses < n) {
        int shortestJobIndex = -1;
        int shortestJobBurst = Integer.MAX_VALUE;

        // Find the process with the shortest burst time that has arrived
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] <= currentTime && !isCompleted[i] && burstTime[i] < shortestJobBurst) {
                shortestJobIndex = i;
                shortestJobBurst = burstTime[i];
            }
        }

        if (shortestJobIndex == -1) {
            // If no process is ready to execute, increment currentTime (idle time)
            currentTime++;
        } else {
            // Execute the shortest job found
            int index = shortestJobIndex;
            int startTime = currentTime;
            currentTime += burstTime[index];  // Process runs to completion
            completionTime[index] = currentTime;
            turnaroundTime[index] = completionTime[index] - arrivalTime[index];
            waitingTime[index] = turnaroundTime[index] - burstTime[index];
            isCompleted[index] = true;
            completedProcesses++;

            // Add row to the table
            model.addRow(new Object[]{
                processData.get(index)[0],  // Process ID
                processData.get(index)[1],  // Process Name
                arrivalTime[index],         // Arrival Time
                burstTime[index],           // Burst Time
                completionTime[index],      // Completion Time
                waitingTime[index],         // Waiting Time
                turnaroundTime[index]       // Turnaround Time
            });

            // Add data to Gantt chart
            ganttChartData.add(new int[]{index + 1, startTime, currentTime});
        }
    }

    // Calculate averages
    for (int j = 0; j < n; j++) {
        avgCompletionTime += completionTime[j];
        avgTurnaroundTime += turnaroundTime[j];
        avgWaitingTime += waitingTime[j];
    }
    avgCompletionTime /= n;
    avgTurnaroundTime /= n;
    avgWaitingTime /= n;

    // Update the UI with averages
    jTextField2.setText(String.valueOf(avgWaitingTime));
    jTextField3.setText(String.valueOf(avgTurnaroundTime));
    jTextField1.setText(String.valueOf(avgCompletionTime));

    // Update Gantt chart
    updateGanttChart(ganttChartData);
}
private void performSJFPreemptiveScheduling() {
    int n = processData.size();
    double avgCompletionTime = 0;
    double avgTurnaroundTime = 0;
    double avgWaitingTime = 0;

    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] remainingBurstTime = new int[n];
    int[] completionTime = new int[n];
    int[] waitingTime = new int[n];
    int[] turnaroundTime = new int[n];
    List<int[]> ganttChartData = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    // Initialize arrival time, burst time, and remaining burst time
    for (int i = 0; i < n; i++) {
        arrivalTime[i] = Integer.parseInt(processData.get(i)[2]);
        burstTime[i] = Integer.parseInt(processData.get(i)[3]);
        remainingBurstTime[i] = burstTime[i];
    }

    int currentTime = 0;
    int completedProcesses = 0;
    int previousProcess = -1;

    while (completedProcesses < n) {
        int shortestJobIndex = -1;
        int shortestJobBurst = Integer.MAX_VALUE;

        // Find the process with the shortest remaining burst time that has arrived
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] <= currentTime && remainingBurstTime[i] < shortestJobBurst && remainingBurstTime[i] > 0) {
                shortestJobIndex = i;
                shortestJobBurst = remainingBurstTime[i];
            }
        }

        if (shortestJobIndex == -1) {
            // If no process is ready to execute, increment currentTime (idle time)
            currentTime++;
        } else {
            // Execute the shortest job found
            int index = shortestJobIndex;
            int startTime = currentTime;

            // Increment time by 1 unit
            remainingBurstTime[index]--;
            currentTime++;

            // If the previous process is different, update the Gantt chart
            if (previousProcess != index) {
                ganttChartData.add(new int[]{index + 1, startTime, currentTime});
                previousProcess = index;
            } else {
                ganttChartData.get(ganttChartData.size() - 1)[2] = currentTime;
            }

            // If process is completed
            if (remainingBurstTime[index] == 0) {
                completedProcesses++;
                completionTime[index] = currentTime;
                turnaroundTime[index] = completionTime[index] - arrivalTime[index];
                waitingTime[index] = turnaroundTime[index] - burstTime[index];

                // Add row to the table
                model.addRow(new Object[]{
                    processData.get(index)[0],  // Process ID
                    processData.get(index)[1],  // Process Name
                    arrivalTime[index],         // Arrival Time
                    burstTime[index],           // Burst Time
                    completionTime[index],      // Completion Time
                    waitingTime[index],         // Waiting Time
                    turnaroundTime[index]       // Turnaround Time
                });
            }
        }
    }

    // Calculate averages
    for (int j = 0; j < n; j++) {
        avgCompletionTime += completionTime[j];
        avgTurnaroundTime += turnaroundTime[j];
        avgWaitingTime += waitingTime[j];
    }
    avgCompletionTime /= n;
    avgTurnaroundTime /= n;
    avgWaitingTime /= n;

    // Update the UI with averages
    jTextField2.setText(String.valueOf(avgWaitingTime));
    jTextField3.setText(String.valueOf(avgTurnaroundTime));
    jTextField1.setText(String.valueOf(avgCompletionTime));

    // Update Gantt chart
    updateGanttChart(ganttChartData);
}

 private void performRoundRobinScheduling(String timeQuantum) {
    int n = processData.size();
    int tQuantum = Integer.parseInt(timeQuantum);
    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] remainingBurstTime = new int[n];
    int[] waitingTime = new int[n];
    int[] turnaroundTime = new int[n];
    int[] completionTime = new int[n];

    double avgCompletionTime = 0;
    double avgTurnaroundTime = 0;
    double avgWaitingTime = 0;

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear previous data in the table

    for (int i = 0; i < n; i++) {
        arrivalTime[i] = Integer.parseInt(processData.get(i)[2]); 
        burstTime[i] = Integer.parseInt(processData.get(i)[3]); 
        remainingBurstTime[i] = burstTime[i];
    }

    int currentTime = 0;

    while (true) {
        boolean allProcessesCompleted = true;

        for (int i = 0; i < n; i++) {
            if (remainingBurstTime[i] > 0) {
                allProcessesCompleted = false;

                int executeTime = Math.min(remainingBurstTime[i], tQuantum);
                currentTime += executeTime;
                remainingBurstTime[i] -= executeTime;

                if (remainingBurstTime[i] == 0) {
                    completionTime[i] = currentTime;
                    turnaroundTime[i] = completionTime[i] - arrivalTime[i];
                    waitingTime[i] = turnaroundTime[i] - burstTime[i];

                    model.addRow(new Object[]{processData.get(i)[0], 
                    processData.get(i)[1], 
                    arrivalTime[i], 
                    burstTime[i], 
                    completionTime[i], 
                    waitingTime[i],  
                    turnaroundTime[i]});
                }
            }
        }

        if (allProcessesCompleted) {
            break;
        }
    }

    for (int j = 0; j < n; j++) {
        avgCompletionTime += completionTime[j];
        avgTurnaroundTime += turnaroundTime[j];
        avgWaitingTime += waitingTime[j];
    }

    avgCompletionTime /= n;
    avgTurnaroundTime /= n;
    avgWaitingTime /= n;

    jTextField2.setText(String.valueOf(avgWaitingTime));
    jTextField3.setText(String.valueOf(avgTurnaroundTime));
    jTextField1.setText(String.valueOf(avgCompletionTime));
}

     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Scheduling Algorithms");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose", "FCFS", "SJF(PREEMPTIVE)", "SJF(NON_PREEMPTIVE)", "ROUND ROBIN", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Average Completion Time");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Selected Algorithm");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Average Waiting Time");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Average Turnaround Time");

        jTable1.setBackground(new java.awt.Color(102, 0, 102));
        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "processID", "processName", "ArrivalTime", "BrustTime", "completionTime", "WaitingTime", "TurnaroundTime"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Scheduling");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Time Quantum");

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("MainMenu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jTextField5.setText("|");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("GanttChart");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(23, 23, 23)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton3)
                                    .addComponent(jButton1))))
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 String selectedAlgorithm = (String) jComboBox1.getSelectedItem();
    if (selectedAlgorithm == null) {
        JOptionPane.showMessageDialog(this, "Please select a scheduling algorithm.");
    } else {
        switch (selectedAlgorithm) {
            case "FCFS":
                performFCFSScheduling();
                break;
            case "ROUND ROBIN":
                String timeQuantum = JOptionPane.showInputDialog(this, "Enter Time Quantum:");
                if (timeQuantum != null) {
                    performRoundRobinScheduling(timeQuantum);
                    jTextField4.setText(String.valueOf(timeQuantum));
                }
                break;
            case "SJF(PREEMPTIVE)":
                performSJFPreemptiveScheduling();
                break;
            case "SJF(NON_PREEMPTIVE)": 
                performSJFNonPreemptiveScheduling(); 
                break;
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
           jTextField4.setText("");  
           jTextField1.setText("");  
           jTextField2.setText("");  
           jTextField3.setText(""); 
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        PHH2 P=new PHH2();
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Schedulingg().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
