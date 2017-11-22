package com.komunikatorinternetowy.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Closes safely any given closable in order to write less code in finally block.
 */
public class CloseSafe {
    /**
     * Safely closes given resource.
     *
     * @param closeable given resource
     */
    public static void close(Closeable closeable) {
        close(closeable, null, null);
    }

    /**
     * Safely closes given resource.
     *
     * @param closeable closeable given resource
     * @param className
     * @param methodName
     */
    public static void close(Closeable closeable, String className, String methodName) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                //Prepare console message
                className = className != null ? className.trim() : "";
                methodName = methodName != null ? methodName.trim() : "";
                if (className.length() + methodName.length() == 0) {
                    System.err.printf("Couldn't close given resource.%n");
                } else {
                    System.err.printf("Couldn't close given resource of class %s.%s.%n",
                            className, methodName);
                }
            }
        }
    }
}
