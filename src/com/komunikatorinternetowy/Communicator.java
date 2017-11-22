package com.komunikatorinternetowy;

import com.komunikatorinternetowy.content.Strings;
import com.komunikatorinternetowy.utils.ChatFileIO;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Communicator panel used as chat window.
 *
 * Created on 2010-01-15, 13:48:59
 *
 * @author Maciej Chwaleba, Tomasz "Rzeźnik" Trzciński <ttrzcinski>
 */
public class Communicator extends JPanel implements Serializable {
    private final static String DEFAULT_FILENAME_SAVED_TALKS = "zapisaneRozmowy.txt";
    private final static String DEFAULT_SOCKET_IP = "127.0.0.1";
    private final static int DEFAULT_SOCKET_PORT = 5000;

    /**
     * Incoming messages stream.
     */
    private BufferedReader incomingMessagesStream;
    /**
     * Outgoing messages stream.
     */
    private PrintWriter outgoingMessagesStream;
    /**
     * Keeps IP Address and port fir communication,
     */
    private Socket socket;
    /**
     * User name, which can be seen on server.
     */
    private String userName;
    /**
     * File interface to chat file.
     */
    private ChatFileIO chatFile;

    /**
     * Creates new instance of Communicator.
     */
    public Communicator() {
        //Render the UI components.
        initComponents();
        //Obtain OS's username and set it as default
        userName = System.getProperty("user.name");
        configureCommunication(userName);

        //Creates new thread to read incoming messages
        Thread receiver = new Thread(new MessageReceiver());
        receiver.start();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setIncoming(BufferedReader incomingStream) {
        this.incomingMessagesStream = incomingStream;
    }

    public void setOutgoing(PrintWriter outgoingStream) {
        this.outgoingMessagesStream = outgoingStream;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns currently used socket.
     *
     * @return currently used socket
     */
    public Socket getSocket() {
        try {
            this.socket = new Socket(DEFAULT_SOCKET_IP, DEFAULT_SOCKET_PORT);
        } catch (IOException ioex1) {
            ioex1.printStackTrace();
            System.err.println(Strings.CM_MSG_NO_CONNECTION);
        }
        return this.socket;
    }

    /**
     * Returns stream to receive messages.
     *
     * @return incoming messages stream
     */
    public BufferedReader getIncoming() {
        try {
            //Create input stream based on read stream from socket
            InputStreamReader inputStream = new InputStreamReader(this.socket.getInputStream());
            this.incomingMessagesStream = new BufferedReader(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(Strings.CM_MSG_NO_CONNECTION);
        }
        return this.incomingMessagesStream;
    }

    /**
     * Returns stream to send message.
     *
     * @return outgoing messages stream
     */
    public PrintWriter getOutgoing() {
        try {
            //Set outgoing stream targeting socket
            this.outgoingMessagesStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(Strings.CM_MSG_NO_CONNECTION);
        }
        return this.outgoingMessagesStream;
    }

    /**
     * Returns kept user name.
     *
     * @return user name
     */
    public String getUserName() {
        return this.userName = jTextField1.getText();
    }

    /**
     * Sets params used in communication.
     *
     * @param username given user name
     */
    private void configureCommunication(String username) {
        //Set name only, if was given
        if (username != null && username.trim().length() > 0) {
            getUserName();
            setUserName(username);
        }
        getSocket();
        //Read and set socket (IP and port)
        setSocket(socket);

        //TODO NPE ON SOCKET - show dialog message and stop the application

        getIncoming();
        //Read and set incoming stream
        setIncoming(incomingMessagesStream);

        getOutgoing();
        //Read and set outgoing stream
        setOutgoing(outgoingMessagesStream);

        //Show on console message with ready state
        System.out.println(Strings.CM_MSG_READY);
    }

    private void assertChatFile() {
        //Assert presence of chatFile
        if (this.chatFile == null) {
            this.chatFile = new ChatFileIO(DEFAULT_FILENAME_SAVED_TALKS);
        }
    }

    /**
     * Loads content of messages and show them in chat text area.
     */
    public void loadMessages() {
        //Assert presence of chatFile
        this.assertChatFile();
        //Obtain file content to present
        String results = this.chatFile.load(DEFAULT_FILENAME_SAVED_TALKS);
        ChatTextArea.append(String.format("%s\n", results));
        //TODO CHANGE TO FOREACH ON SPLIT OR SOME LAM ONE-LINER
        //Read line by line message
        /*StringTokenizer strTok = new StringTokenizer("\n");
        while (strTok.hasMoreTokens()) {
            //Write this line on console
            System.out.println(strTok.nextToken());
            //Add to talk text area
            ChatTextArea.append(String.format("%s\n", line));
        }*/
    }

    /**
     * Saves messages to text file.
     */
    public void saveToTextFile() {
        //Assert presence of chatFile
        this.assertChatFile();
        //append to file
        this.chatFile.appendToFile(this.ChatTextArea.getText());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        ChatTextArea = new javax.swing.JTextArea();
        BtnSaveToFile = new javax.swing.JButton();
        BtnSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        BtnClear = new javax.swing.JButton();
        BtnLoadFromFile = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        ChatTextArea.setColumns(20);
        ChatTextArea.setEditable(false);
        ChatTextArea.setFont(new java.awt.Font("Dialog", 0, 12));
        ChatTextArea.setLineWrap(true);
        ChatTextArea.setRows(5);
        jScrollPane1.setViewportView(ChatTextArea);

        BtnSaveToFile.setText(Strings.CM_CAPTION_SAVE_CHAT);
        BtnSaveToFile.addActionListener(evt -> BtnSaveToFileActionPerformed(evt));

        BtnSend.setText(Strings.CM_CAPTION_SEND_MESSAGE);
        BtnSend.addActionListener(evt -> BtnSendActionPerformed(evt));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Dialog", 0, 12));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        BtnClear.setText(Strings.CM_CAPTION_CLEAR_WINDOW);
        BtnClear.addActionListener(evt -> BtnClearActionPerformed(evt));

        BtnLoadFromFile.setText(Strings.CM_CAPTION_LOAD_CHAT);
        BtnLoadFromFile.addActionListener(evt -> BtnLoadFromFileActionPerformed(evt));

        jLabel1.setText(Strings.CM_LABEL_USER_NAME);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(BtnSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                                        .addComponent(BtnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(40, 40, 40)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(BtnSaveToFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(BtnLoadFromFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(BtnSaveToFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(BtnLoadFromFile))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(BtnSend)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BtnClear)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSaveToFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSaveToFileActionPerformed
        saveToTextFile();
    }//GEN-LAST:event_BtnSaveToFileActionPerformed

    public void clearInputArea() {
        this.ChatTextArea.setText(" ");
    }

    private void BtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClearActionPerformed
        clearInputArea();
    }//GEN-LAST:event_BtnClearActionPerformed

    private void BtnLoadFromFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLoadFromFileActionPerformed
        loadMessages();
    }//GEN-LAST:event_BtnLoadFromFileActionPerformed

    /**
     * Sends messages.
     */
    public void send() {
        try {
            getUserName();
            setUserName(userName);
            //Add user's name as prefix to message
            outgoingMessagesStream.println(userName + ": " + jTextArea2.getText());
            //Send it away to destination and clear the stream
            outgoingMessagesStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Clear message in input text field and set focus there
        jTextArea2.setText(" ");
        jTextArea2.requestFocus();
    }

    private void BtnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSendActionPerformed
        send();
    }//GEN-LAST:event_BtnSendActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSend;
    private javax.swing.JButton BtnSaveToFile;
    private javax.swing.JButton BtnClear;
    private javax.swing.JButton BtnLoadFromFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ChatTextArea;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    /**
     * Interface responsible for reading incoming messages.
     */
    public class MessageReceiver implements Runnable {
        //Responsible for work of Thread
        public void run() {
            String messageReceived;
            try {
                //As long as there is a line to read in message
                while ((messageReceived = incomingMessagesStream.readLine()) != null) {
                    //Show received message on console
                    System.out.printf(Strings.CM_MSG_RECEIVED, messageReceived);
                    //Add received message to text field as new line
                    ChatTextArea.append(String.format("%s\n", messageReceived));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
