
package deploy.common;

import deploy.JND.GUIGlobals;
import deploy.JND.SimpleHtmlHelpFrame;
import experiment.Experiment;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLEditorKit;

public abstract class AbstractMainMenuFrame extends javax.swing.JFrame {

    
    /**
     * Subclasses should call this method from within their main methods.
     * 
     * 
     * Example:
     * public static void main(String[] args){
     *      new ClassConstant().execute();
     * }
     * 
     */
    public void execute(){
        final AbstractMainMenuFrame frm  = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm.setExperiment(frm.constructExperiment());
                frm.setVisible(true);
            }
        });
    }
   
    SimpleHtmlHelpFrame helpFrame = new SimpleHtmlHelpFrame();
    
    
    protected Experiment myExperiment = null;
    
    /**
     * Function to populate myExperiment with an Experiment to run.
     * @return 
     */
    protected abstract Experiment constructExperiment();
    
    protected Experiment getExperiment(){
        return myExperiment;
    }
    
    /** Creates new form MainMenu */
    public AbstractMainMenuFrame() {
        GUIGlobals.setLookAndFeel();
        initComponents();

        this.setLocationRelativeTo(null);
        htmlPane.setEditorKit(new HTMLEditorKit());
        
        try {
            String filename = "./html/Welcome.html";
            FileReader reader = new FileReader(filename);
            htmlPane.read(reader, filename);
        } catch (IOException ex) {
            System.err.println("Could not open welcome page.");
            ex.printStackTrace();
        }

        helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	testButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        htmlPane = new javax.swing.JEditorPane();
        quitButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel(){
            BufferedImage bi;
            boolean isLoadFailed = false;

            public void paintComponent(Graphics g){
                if(bi == null && isLoadFailed == false){
                    try{
                        bi = ImageIO.read(new File("./img/header.png"));
                    }catch(Exception e){
                        isLoadFailed = true;
                    }
                }
                if(bi != null){
                    g.drawImage(bi, 0, 0, null);
                }
            }
        };
        runButton = new javax.swing.JButton();
        configureButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        testButton.setText("Test Experiment");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        htmlPane.setEditable(false);
        jScrollPane1.setViewportView(htmlPane);

        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 165, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 54, Short.MAX_VALUE)
        );

        runButton.setText("Run Experiment");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        configureButton.setText("Configure Experiment");
        configureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureButtonActionPerformed(evt);
            }
        });

        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 510, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(runButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(testButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(configureButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(helpButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(quitButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(configureButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(runButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 80, Short.MAX_VALUE)
                .add(helpButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(quitButton))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        //Get subject number and initials for next subject
        StringBuffer initials = new StringBuffer(ExperimentStartDialogue.MAX_CHARS);
        StringBuffer subjectNumber = new StringBuffer(ExperimentStartDialogue.MAX_DIGITS);

        boolean isStart = ExperimentStartDialogue.showDialogue(this, initials, subjectNumber);

        // don't start experiment if we hit 'cancel'
        if (isStart == false) {
            return;
        }
        
        initials.trimToSize();
        subjectNumber.trimToSize();
        getExperiment().run(Integer.parseInt(subjectNumber.toString()),initials.toString());
        this.setVisible(false);
}//GEN-LAST:event_runButtonActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        System.exit(0);
}//GEN-LAST:event_quitButtonActionPerformed

    private void configureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureButtonActionPerformed
        getExperiment().showConfigureFrame(true);
}//GEN-LAST:event_configureButtonActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        getExperiment().test();
        this.setVisible(false);
    }//GEN-LAST:event_testButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        helpFrame.setVisible(true);
    }//GEN-LAST:event_helpButtonActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton configureButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JEditorPane htmlPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton runButton;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables

    private void setExperiment(Experiment constructExperiment) {
       this.myExperiment = constructExperiment;
    }

}
