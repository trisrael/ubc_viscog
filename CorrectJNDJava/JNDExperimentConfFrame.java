/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package deploy.JND;

import experiment.Experiment;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Will
 */
public class JNDExperimentConfFrame extends javax.swing.JFrame {

    Experiment myExperiment = null;
    JNDExperimentConfFrameTableModel tableModel = new JNDExperimentConfFrameTableModel();
    private final int[] columnWidths = {95, 15, 15, 33, 12, 12};

    public void addConfRow(String title, Object value, Class type, String hiddenTitle, Object hiddenMin, Object hiddenMax){
        this.tableModel.addConfRow(title, value, type, hiddenTitle, hiddenMin, hiddenMax);
    }

    private void setColumnWidths(){
        int numCols = this.jTable1.getColumnCount();

        for(int i=0; i<numCols; i++){
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    public void clearTable(){
        tableModel.clear();
    }


    private void setLookAndFeel(){
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** Creates new form JNDExperimentConfFrame */
    public JNDExperimentConfFrame() {
        setLookAndFeel();
        initComponents();
        this.setLocationRelativeTo(null); // center the frame
        setColumnWidths();
    }

    public JNDExperimentConfFrame(Experiment exp){
        this();
        myExperiment = exp;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {


        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(tableModel);
        tableModel.addTableModelListener(jTable1);
        jScrollPane1.setViewportView(jTable1);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Double-click a value to edit...");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 660, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 357, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(saveButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .add(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 364, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 265, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(saveButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        //System.out.println("-------------");
        //System.out.println(this.tableModel.getProposedConf());
        boolean isConfirm = false;
        int result;

        try{
            result = JOptionPane.showConfirmDialog(this, "Confirm overriding experiment configuration?", "Confirm save", JOptionPane.OK_CANCEL_OPTION);
            isConfirm = (result == JOptionPane.OK_OPTION);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(myExperiment != null && isConfirm){
            myExperiment.loadConfigurationFromString(this.tableModel.getProposedConf());
            myExperiment.saveConfiguration();
            this.setVisible(false);
        }
        
}//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JNDExperimentConfFrame myFrame = new JNDExperimentConfFrame();
                myFrame.setVisible(true);
                myFrame.addConfRow("Number of points on left", new Integer(500), Integer.class, "iPointsLeft", new Integer(1), new Integer(5000));
                myFrame.addConfRow("Number of points on right", new Integer(500), Integer.class, "iPointsRight", new Integer(1), new Integer(5000));
                myFrame.addConfRow("Max trials per condition", new Integer(30), Integer.class, "iCapComplete", new Integer(1), new Integer(1000));
                myFrame.addConfRow("Horizontal resolution", new Integer(1200), Integer.class, "iScreenResX", new Integer(1), new Integer(50000));
                myFrame.addConfRow("Vertical resolution", new Integer(800), Integer.class, "iScreenResY", new Integer(1), new Integer(50000));
                myFrame.addConfRow("Number of test trials", new Integer(30), Integer.class, "iTestNumTrials", new Integer(1), new Integer(1000));
                myFrame.addConfRow("Left R error", new Double(0.0001), Double.class, "dErrLeft", new Double(0.0), new Double(1.0));
                myFrame.addConfRow("Right R error", new Double(0.0001), Double.class, "dErrRight", new Double(0.0), new Double(1.0));
                myFrame.addConfRow("Step size", new Double(0.01), Double.class, "dStep", new Double(0.000001), new Double(1.0));
                myFrame.addConfRow("Axis on", new Boolean(false), Boolean.class, "bIsAxisOn", new Boolean(false), new Boolean(true));
                myFrame.addConfRow("Labels on", new Boolean(false), Boolean.class, "bIsLabelsOn", new Boolean(false), new Boolean(true));
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

}
