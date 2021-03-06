package com.komunikatorinternetowy.utils;

import com.komunikatorinternetowy.utils.CloseSafe;

import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Responsible for proper operating on files and variables.
 *
 * @author Tomasz "Rzeźnik" Trzciński <ttrzcinski>
 */
public class FilesIO {
    private final static String DEFAULT_LOG_FILE_NAME = "Log.txt";

    /**
     * Name of currently processed file
     */
    private String fileName;
    /**
     * Absolute path to file.
     */
    private String filePath;
    /**
     * content of the last error, that occur.
     */
    private String lastError;

    FilesIO() {
        this.zeroStates();
    }

    FilesIO(String fileName) {
        this.zeroStates();
        this.fileName = fileName;
    }

    /**
     * Set variables in their default start states.
     */
    private void zeroStates() {
        //Set the same catalog with default name of log.
        this.filePath = "";
        this.fileName = DEFAULT_LOG_FILE_NAME;
        this.lastError = "No error recorded.";
    }

    /**
     * Sets last known error to given.
     *
     * @param errorContent error's content
     */
    private void markError(String errorContent) {
        this.lastError = errorContent;
    }

    /**
     * Obtains current date and time in predefined format.
     *
     * @return date in format "HH:mm dd-MM-yyyy"
     */
    private String now() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return dateFormatter.format(now);
    }

    /**
     * Adds date and time to given line of content.
     *
     * @param content given content
     * @return given content with actual date and time
     */
    private String convertToLogEntry(String content) {
        return String.format("[%s] %s", this.now(), content);
    }

    /**
     * Procedura tworzy nowy plik logu.
     */
    private void createNewFile() {
        PrintWriter outputWriter = null;
        try {
            //utworz strumien do zapisu odnoszacy sie do pliku
            //o zadanych parametrach w zmiennych prywatnych
            outputWriter = new PrintWriter(new FileWriter(this.filePath + this.fileName));

            //dadaj do pliku naglowek
            outputWriter.println(String.format("<<<Log programu utworzony %s >>>", this.now()));
            //logDown powód utworzenia nowego pliku
            outputWriter.println(this.convertToLogEntry("Nie można odczytać pliku logu. Utworzono nowy."));
        } catch (IOException ex1) {
            //jeśli coś nie wyszło, pokaz komunikat błędu
            this.markError(MessageFormat.format("Nie mozna utworzyc pliku {0}{1}", this.filePath, this.fileName));
        } finally {
            CloseSafe.close(outputWriter, "FilesIO", "createNewFile");
        }
    }//

    /**
     * Reads content of log from file.
     *
     * @return content of log
     */
    public String readLogContent() {
        StringBuffer readLines;
        BufferedReader inputBuffer = null;

        try {
            inputBuffer = new BufferedReader(new FileReader(this.filePath + this.fileName));
            //Read line by line
            String line;
            readLines = new StringBuffer("");
            while ((line = inputBuffer.readLine()) != null) {
                //Append read line as new line
                readLines = readLines.append(line).append("\r\n");
            }
        } catch (IOException ex3) {
            readLines = null;
            this.markError("Couldn't read log.");
        } finally {
            CloseSafe.close(inputBuffer, "FilesIO", "readLogContent");
        }

        return readLines != null ? readLines.toString() : null;
    }

    /**
     * Adds log entry to file.
     *
     * @param content entry's content
     */
    public void logDown(String content) {
        PrintWriter outputWriter = null;
        try {
            //Obtain read log's content
            String readLog = this.readLogContent();
            //Open stream to log file
            outputWriter = new PrintWriter(new FileWriter(this.filePath + this.fileName));
            //Write to file lines with count of notes
            outputWriter.print(readLog);
            outputWriter.println(this.convertToLogEntry(content));
        } catch (IOException ioex2) {
            this.markError(ioex2.toString());
        } finally {
            CloseSafe.close(outputWriter, "FilesIO", "logDown");
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDefaults() {
        this.zeroStates();
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getLastError() {
        return this.lastError;
    }
}
