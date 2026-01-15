import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class SharedTableModel extends DefaultTableModel {
    private static SharedTableModel instance;

    private SharedTableModel() {
        super(new Object[][]{}, new String[]{"Process ID", "Process Name", "Arrival Time", "Burst Time", "Status", "Priority"});
    }

    public static SharedTableModel getInstance() {
        if (instance == null) {
            instance = new SharedTableModel();
        }
        return instance;
    }
}

public class PHH2 extends javax.swing.JFrame {
private List<Integer> processIDorg = new ArrayList<>();
private List<String> processNamesorg = new ArrayList<>();
private List<Integer> arrivalTimesorg = new ArrayList<>();
private List<Integer> burstTimesorg = new ArrayList<>();
    DefaultTableModel model;
      private JTable table;

    public JTable getTable() {
        return table;
    }
    public PHH2() {
        initComponents();
         jTable2.setModel(SharedTableModel.getInstance());
         model = (DefaultTableModel) jTable2.getModel();
    }  

   public boolean containsProcessID(String processID) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (processID.equals(model.getValueAt(i, 0).toString())) {
                return true;
            }
        }
        return false;
    }
    public void changeProcessStatus(String processID, String status, String priority) {
    for (int i = 0; i < model.getRowCount(); i++) {
        String pid = model.getValueAt(i, 0).toString();
        if (pid.equals(processID)) {  
            model.setValueAt(status, i, 4);
            model.setValueAt(priority, i, 5);
            break;
        }
    }
}
public boolean isInterruptedIn(String processID) {
    for (int i = 0; i < model.getRowCount(); i++) {
        if (processID.equals(model.getValueAt(i, 0).toString())) {
            String status = model.getValueAt(i, 4).toString();
            if (status.equals("blocked")) {
                return true;
            }
        }
    }
    return false;
}
    
private List<String[]> getRunningProcessesData()
{
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int rowCount = model.getRowCount();
    List<String[]> runningProcessesData = new ArrayList<>();
    for (int i = 0; i < rowCount && runningProcessesData.size() < 10; i++) {
        String currentState = model.getValueAt(i, 4).toString();  
        if ("Ready".equals(currentState)) {
            try {
                int arrivalTime = Integer.parseInt(model.getValueAt(i, 2).toString());  
                int burstTime = Integer.parseInt(model.getValueAt(i, 3).toString());    
                int processID = Integer.parseInt(model.getValueAt(i, 0).toString());   
                String processName = model.getValueAt(i, 1).toString(); 
                processNamesorg.add(processName);
                processIDorg.add(processID);
                arrivalTimesorg.add(arrivalTime);
                burstTimesorg.add(burstTime);

                runningProcessesData.add(new String[]{String.valueOf(processID), processName, String.valueOf(arrivalTime), String.valueOf(burstTime)});
            } 
            catch (NumberFormatException e) 
            {
                e.printStackTrace(); 
            }
        }
    }
    return runningProcessesData;
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Resume");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("IO management");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Create");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setText("WakeUp");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton6.setText("Suspend");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton7.setText("Dispatch");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton8.setText("Scheduling");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton9.setText("ChangePriority");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton10.setText("Destroy");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton11.setText("Main Menu");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton12.setText("Block");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(63, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(102, 0, 102));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(102, 0, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("No. of Process");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ProcessName");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ArrivalTime");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BurstTime");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton5.setText("Created");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jTextField4))
                .addGap(17, 17, 17))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jButton5)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CreateProcess", jPanel4);

        jTable2.setBackground(new java.awt.Color(102, 0, 102));
        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTable2.setForeground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProcessID", "ProcessName", "ArrivalTime", "BurstTime", "Status", "Priority"
            }
        ));
        jTable2.setToolTipText("");
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Random random = new Random();
int processID = random.nextInt(101);
int numProcessesAllowed;
try {
    numProcessesAllowed = Integer.parseInt(jTextField2.getText());
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "Please enter a valid number of processes.");
    return;
}

// Get the number of rows in the table (number of inputs taken)
int numRows = jTable2.getRowCount();
if (numRows >= numProcessesAllowed) {
    JOptionPane.showMessageDialog(this, "You can't enter more than " + numProcessesAllowed + " inputs.");
    return;
}
String processName = jTextField3.getText();
String arrivalTime = jTextField1.getText();
String burstTime = jTextField4.getText();

System.out.println("Random Number: " + processID);
if (processName.isEmpty() || arrivalTime.isEmpty() || burstTime.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
    return;
}
String status="Ready";
String priority;
switch (status) {
    case "Running":
        priority = "High"; // Highest priority for running processes
        break;
    case "Ready":
        priority = "Medium"; // Medium priority for ready processes
        break;
    case "Blocked":
    case "Suspended":
    case "New":
    case "Terminated":
    default:
        priority = "Low"; 
        break;
}

DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
model.addRow(new Object[]{processID, processName, arrivalTime, burstTime, status, priority});

jTextField1.setText("");
jTextField3.setText("");
jTextField4.setText("");

JOptionPane.showMessageDialog(this, "Information saved successfully.");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
         PHH1 p=new PHH1();
        dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
                String processIdToDelete = JOptionPane.showInputDialog(this, "Enter Process ID to Destroy:");
    
    if (processIdToDelete != null && !processIdToDelete.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString();  // Assuming "process id" is at index 0
            
            if (currentProcessId.equals(processIdToDelete)) {
                // Remove the row from the table
                model.removeRow(i);
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToDelete + " has been destroyed.");
                return;  // Exit the method once the process is found and removed
            }
        }
        
        // If the process ID is not found
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToDelete + " not found.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         String processIdToSuspend = JOptionPane.showInputDialog(this, "Enter the Process ID you want to suspend:");

    // Check if a process ID was provided
    if (processIdToSuspend != null && !processIdToSuspend.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();

        // Loop through the table rows to find the specified process ID
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString();  // Assuming "P ID" is at index 0 in the table
            String currentState = model.getValueAt(i, 4).toString();      // Assuming "State" is at index 4 in the table

            // If the process ID matches and its state is "Running," update its state to "Suspended"
            if (currentProcessId.equals(processIdToSuspend) && currentState.equals("Running")) {
                model.setValueAt("Suspended", i, 4);
                model.setValueAt("Low", i, 5); 
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToSuspend + " has been SUSPENDED.");
                return;  // Exit the method once the process is found and updated
            } else if (currentProcessId.equals(processIdToSuspend) && !currentState.equals("Running")) {
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToSuspend + " cannot be suspended as it is not in the 'Running' state.");
                return;  // Exit the method if the process is found but not in the "Running" state
            }
        }

        // If the process ID is not found
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToSuspend + " not found.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
         String processIdToBlock = JOptionPane.showInputDialog(this, "Enter the Process ID to Block:");

    // Check if a process ID was provided
    if (processIdToBlock != null && !processIdToBlock.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();

        // Loop through the table rows to find the specified process ID
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString();  // Assuming "P ID" is at index 0 in the table
            String currentState = model.getValueAt(i, 4).toString();      // Assuming "State" is at index 4 in the table

            // If the process ID matches and its state is "Running," change its state to "Blocked"
            if (currentProcessId.equals(processIdToBlock) && currentState.equals("Running")) {
                model.setValueAt("Blocked", i, 4);  
                model.setValueAt("Low", i, 5); 
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToBlock + " has been blocked.");
                return;  // Exit the method once the process is found and blocked
            } else if (currentProcessId.equals(processIdToBlock) && !currentState.equals("Running")) {
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToBlock + " is not running. Cannot block.");
                return;  // Exit the method if the process is found but is not running
            }
        }

        // If the process ID is not found
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToBlock + " not found.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String processIdToResume = JOptionPane.showInputDialog(this, "Enter the Process ID to Resume:");

    // Check if a process ID was provided
    if (processIdToResume != null && !processIdToResume.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();

        // Loop through the table rows to find the specified process ID
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString();  // Assuming "P ID" is at index 0 in the table
            String currentState = model.getValueAt(i, 4).toString();      // Assuming "State" is at index 4 in the table

            // If the process ID matches and its state is "Suspended," change its state to "Ready"
            if (currentProcessId.equals(processIdToResume) && currentState.equals("Suspended")) {
                model.setValueAt("Ready", i, 4); 
                model.setValueAt("Medium", i, 5); 
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToResume + " has been resumed and is now READY.");
                return;  // Exit the method once the process is found and resumed
            } else if (currentProcessId.equals(processIdToResume) && !currentState.equals("Suspended")) {
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToResume + " is not suspended. Cannot resume.");
                return;  // Exit the method if the process is found but is not suspended
            }
        }

        // If the process ID is not found
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToResume + " not found.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
             String processIdToWakeUp = JOptionPane.showInputDialog(this, "Enter the Process ID to Wakeup:");

    // Check if a process ID was provided
    if (processIdToWakeUp != null && !processIdToWakeUp.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();

        // Loop through the table rows to find the specified process ID
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString();  // Assuming "P ID" is at index 0 in the table
            String currentState = model.getValueAt(i, 4).toString();     // Assuming "State" is at index 4 in the table

            // If the process ID matches and its current state is "Blocked"
            if (currentProcessId.equals(processIdToWakeUp) && currentState.equalsIgnoreCase("Blocked")) {
                // Update the state to "Ready" directly
                model.setValueAt("Ready", i, 4);
                model.setValueAt("Medium", i, 5); 
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToWakeUp + " is now READY.");
                return;  // Exit the method once the process is found and updated
            }
        }

        // If the process ID is not found or is not in "Blocked" state
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToWakeUp + " either not found or not in Blocked state.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String processIdToDispatch = JOptionPane.showInputDialog(this, "Enter the Process ID to Dispatch:");
    if (processIdToDispatch != null && !processIdToDispatch.trim().isEmpty()) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String currentProcessId = model.getValueAt(i, 0).toString(); 
            String currentState = model.getValueAt(i, 4).toString();      
            if (currentProcessId.equals(processIdToDispatch) && !currentState.equals("Suspended")) {
                model.setValueAt("Running", i, 4); 
                model.setValueAt("High", i, 5); 
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToDispatch + " is now RUNNING.");
                return;
            } else if (currentProcessId.equals(processIdToDispatch) && currentState.equals("Suspended")) {
                JOptionPane.showMessageDialog(this, "Process with ID " + processIdToDispatch + " is suspended. Cannot dispatch.");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToDispatch + " not found.");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
    }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
     List<String[]> runningProcessesData = getRunningProcessesData();

    // Check if the list is empty
    if (runningProcessesData.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Your table is empty. There are no processes to schedule.");
        return;  
    } else {
        Schedulingg s = new Schedulingg(runningProcessesData);
        s.setVisible(true);
        // this.dispose();  
    }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
String processIdToChangePriority = JOptionPane.showInputDialog(this, "Enter the Process ID to change its Priority:");

if (processIdToChangePriority != null && !processIdToChangePriority.trim().isEmpty()) {
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int rowCount = model.getRowCount();
    boolean processFound = false;
    
    for (int i = 0; i < rowCount; i++) {
        String currentProcessId = model.getValueAt(i, 0).toString();
        
        if (currentProcessId.equals(processIdToChangePriority)) {
            processFound = true;
            String newPriority = JOptionPane.showInputDialog(this, "Enter the new Priority for Process ID " + processIdToChangePriority + ":");
            
            if (newPriority != null && !newPriority.trim().isEmpty()) {
                model.setValueAt(newPriority, i, 5); // Assuming the priority is in the 7th column (index 6)
                JOptionPane.showMessageDialog(this, "Priority updated successfully for Process ID " + processIdToChangePriority + ".");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid Priority.");
            }
            break;
        }
    }
    
    if (!processFound) {
        JOptionPane.showMessageDialog(this, "Process with ID " + processIdToChangePriority + " not found.");
    }
} else {
    JOptionPane.showMessageDialog(this, "Please enter a valid Process ID.");
}

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        interrupt IMWindows=new interrupt();
        IMWindows.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PHH2().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
