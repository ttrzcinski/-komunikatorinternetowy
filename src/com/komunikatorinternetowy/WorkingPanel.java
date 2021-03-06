package com.komunikatorinternetowy;

import com.komunikatorinternetowy.content.Strings;
import com.komunikatorinternetowy.utils.FilesIO;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.TextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel responsible for construction of UI and communication with log.
 * <p>
 * Created on 2010-04-15, 09:13:18
 *
 * @author Tomasz "Rzeźnik" Trzciński <ttrzcinski>
 */
public class WorkingPanel extends JPanel {
    //States of component
    private final int AWAITING = 0;
    private final int SAVING = 1;
    private final int TURNED_OFF = 2;
    /**
     * File handle to log file.
     */
    private FilesIO log = new FilesIO();
    /**
     * Flag of saving to file.
     */
    private boolean flagSaving = true;

    /**
     * Creates new instance of WorkingPanel.
     */
    public WorkingPanel() {
        //Render UI components
        initComponents();
        this.setState(AWAITING);
    }

    /**
     * Sets component to given state, changes its color, caption and state of flagSaving.
     *
     * @param stateIndex wanted state
     */
    private void setState(int stateIndex) {
        switch (stateIndex) {
            case AWAITING:
                this.BtnCommSettings.setText(Strings.WP_STATE_AWAITING);
                this.BtnCommSettings.setBackground(Color.YELLOW);
                this.flagSaving = true;
                break;
            case SAVING:
                this.BtnCommSettings.setText(Strings.WP_STATE_SAVING);
                this.BtnCommSettings.setBackground(Color.RED);
                this.flagSaving = true;
                break;
            case TURNED_OFF:
                this.BtnCommSettings.setText(Strings.WP_STATE_TURNED_OFF);
                this.BtnCommSettings.setBackground(Color.GRAY);
                this.flagSaving = false;
                break;
        }
    }

    /**
     * Appends given content to log file.
     *
     * @param content given content
     */
    public void logDown(String content) {
        //If flag of saving is set
        if (this.flagSaving) {
            this.setState(SAVING);
            this.log.logDown(content);
            this.setState(AWAITING);
        }
    }

    /**
     * Shows dialog with settings.
     */
    private void showSettingsDialog() {
        //Create UI Components to set log file
        TextField TexFilePath = new TextField(this.log.getFilePath(), 30);
        TextField TexFileName = new TextField(this.log.getFileName(), 30);
        Checkbox ChkSave = new Checkbox(Strings.WP_LABEL_SAVE_TO_LOG, this.flagSaving);

        //Set of components to present in dialog
        Object[] dialogsComponentsSet = {Strings.WP_LABEL_LOG_FILE_PATH, TexFilePath,
                Strings.WP_LABEL_LOG_FILE_NAME, TexFileName, ChkSave};

        //Keep code of pressed button
        int pressedButton = JOptionPane.showConfirmDialog(this,
                dialogsComponentsSet,
                Strings.WP_TITLE_LOG_SAVING_PARAMS,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        //If user confirmed
        if (pressedButton == JOptionPane.OK_OPTION) {
            //If any content was set in file name
            if (TexFileName.getText().isEmpty() == false) {
                //Set log fine name and path to given values
                this.log.setFilePath(TexFilePath.getText().trim());
                this.log.setFileName(TexFileName.getText().trim());
                this.flagSaving = ChkSave.getState();

                this.setState(this.flagSaving ? AWAITING : TURNED_OFF);

                //Show message about changed values
                JOptionPane.showMessageDialog(this,
                        new StringBuffer().append(Strings.WP_MSG_CHANGED_FILE_PATH_TO).append(this.log.getFilePath())
                                .append(this.log.getFileName()).append(".").toString(),
                        Strings.WP_TITLE_SUCCESS, JOptionPane.INFORMATION_MESSAGE);
            } else {
                //Show message about wrongly inputed values
                JOptionPane.showMessageDialog(this,
                        Strings.WP_MSG_CHANGED_ERROR, Strings.WP_TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Accessors
    public void setFilePath(String sciezka) {
        this.log.setFilePath(sciezka.trim());
    }

    public void setFileName(String fileName) {
        this.log.setFileName(fileName);
    }

    public void setFlagSaving(boolean value) {
        this.flagSaving = value;
    }

    public String getFilePath() {
        return this.log.getFilePath();
    }

    public String getFileName() {
        return this.log.getFileName();
    }

    public boolean isFlagSaving() {
        return this.flagSaving;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnCommSettings = new javax.swing.JButton();

        BtnCommSettings.setBackground(new java.awt.Color(0, 204, 0));
        BtnCommSettings.setText(Strings.WP_STATE_AWAITING);
        BtnCommSettings.setToolTipText(Strings.WP_HINT_BUTTON_SETTINGS);
        BtnCommSettings.setBorder(null);
        BtnCommSettings.setBorderPainted(false);
        BtnCommSettings.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnCommSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCommSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BtnCommSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BtnCommSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCommSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCommSettingsActionPerformed
        this.showSettingsDialog();
    }//GEN-LAST:event_BtnCommSettingsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCommSettings;
    // End of variables declaration//GEN-END:variables

}
