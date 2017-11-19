package com.komunikatorinternetowy;


import com.komunikatorinternetowy.Content.Strings;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * Represents broadcast server.
 *
 * @author Maciej Chwaleba, Tomasz "Rzeźnik" Trzciński <ttrzcinski>
 */
public class Server {
    /**
     * Represents default port of communication with client.
     */
    private final static int DEFAULT_PORT = 5000;

    /**
     * Vector of output streams.
     */
    private ArrayList outStreams;
    /**
     * Interface to log file.
     */
    private FilesIO log;

    /**
     * Inner class responsible for serving individual customers.
     */
    public class ClientServer implements Runnable {
        //Stream used to read messages
        private BufferedReader readMessages;

        /**
         * Creates new web interface to operate individual clients.
         *
         * @param clientSocket individual client's socket
         */
        public ClientServer(Socket clientSocket) {
            try {
                //Read input stream from the side of pointed customer
                InputStreamReader isReader =
                        new InputStreamReader(clientSocket.getInputStream());
                readMessages = new BufferedReader(isReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Runs in thread.
         */
        public void run() {
            try {
                String message;
                //Read line by line read messages
                while ((message = readMessages.readLine()) != null) {
                    //Show on console read line
                    System.out.println(
                            MessageFormat.format(Strings.SV_CONSOLE_MSG_READ, message)
                    );
                    //Send further read line
                    send(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Main method calling work order on server.
     * @param args environment run arguments
     */
    public static void main(String[] args) {
        new Server().work();
    }

    /**
     * Represents basic server's workflow.
     */
    public void work() {

        outStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(DEFAULT_PORT);

            while (true) {
                //Accept port of communication
                Socket clientSocket = serverSock.accept();
                //Create new output stream to send messages
                PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream());
                outStreams.add(outWriter);
                //Create new thread to serve clients
                Thread clientServerTread = new Thread(new ClientServer(clientSocket));
                clientServerTread.start();
                //Show on console information about connection
                System.out.println(Strings.SV_CONSOLE_MSG_CONNECTED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends given massage further as broadcast.
     *
     * @param message given content
     */
    public void send(String message) {
        //Obtain iterator serving streams one by one
        Iterator it = outStreams.iterator();
        while (it.hasNext()) {
            try {
                //Pick another stream
                PrintWriter printWriter = (PrintWriter) it.next();
                //Send customer a message
                printWriter.println(message);
                //Flush the stream
                printWriter.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
