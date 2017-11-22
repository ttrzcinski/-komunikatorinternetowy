package com.komunikatorinternetowy.utils;

import java.io.*;

/**
 * Responsible for input/output operations on Chat files.
 */
public class ChatFileIO {
    //TODO Change this into lambdas

    /**
     * File to transcript conversations.
     */
    private File transcriptFile;

    /**
     * Creates new instance of ChatFileIO with given file name.
     *
     * @param name given file name
     */
    public ChatFileIO(String name) {
        this.createTranscriptFileName(name);
    }

    private void createTranscriptFileName(String name) {
        this.transcriptFile = new File(name);
    }

    /**
     * Loads data from file.
     *
     * @param name file name
     * @return content of file in array
     */
    public String load(String name) {
        StringBuffer result = new StringBuffer("");
        BufferedReader readSource = null;
        try {
            readSource = new BufferedReader(new FileReader(name));
            String line;
            //Read line by line message
            while ((line = readSource.readLine()) != null) {
                //Write this line on console
                System.out.println(line);
                //Add to talk text area
                result = result.append(line).append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseSafe.close(readSource);
        }
        return result.toString();
    }
    /**
     * Loads data from file.
     *
     * @return content of file in array
     */
    public String load() {
        return this.load(transcriptFile.getName());
    }

    /**
     * Adds at the end of file given content. If file doesn't exists, creates new.
     *
     * @param name file name
     * @param content content to add
     * @return true, if it worked, false otherwise
     */
    public boolean appendToFile(String name, String content) {
        BufferedWriter fileOutput = null;
        //Flag marking no error
        boolean smoothFlow = true;

        this.setTranscriptFile(name);

        try {
            //Open writer to transcript file
            fileOutput = new BufferedWriter(new FileWriter(transcriptFile));
            //Obtain chat's content and write it to file output
            fileOutput.write(String.format("%s\n", content));
        } catch (IOException ex) {
            ex.printStackTrace();
            smoothFlow = false;
        } finally {
            CloseSafe.close(fileOutput);
        }
        return smoothFlow;
    }

    /**
     * Adds at the end of file given content. If file doesn't exists, creates new.
     *
     * @param content content to add
     * @return true, if it worked, false otherwise
     */
    public boolean appendToFile(String content) {
        return this.appendToFile(null, content);
    }

    /**
     * Returns name of transcript file.
     *
     * @return transcript file
     */
    public String getTranscriptFile() {
        return this.transcriptFile == null ? this.transcriptFile.getName() : null;
    }

    /**
     * Sets new chat file, if filename is different.
     *
     * @param name given file name
     */
    public void setTranscriptFile(String name) {
        if (this.transcriptFile == null || !name.equals(this.transcriptFile.getName())) {
            this.createTranscriptFileName(name);
        }
    }
}
