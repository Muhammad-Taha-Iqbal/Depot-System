package model;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static Log instance;
    private StringBuilder logBuffer;

    private Log() {
        logBuffer = new StringBuilder();
    }

    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void logEvent(String event) {
        logBuffer.append(event).append("\n");
    }

    public void writeToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(logBuffer.toString());
        }
    }
}
