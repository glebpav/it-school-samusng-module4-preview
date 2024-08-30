package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFileManager {
    private static LogFileManager instance;
    private final PrintWriter logWriter;

    private LogFileManager() {
        try {
            File logFile = new File(Gdx.files.getExternalStoragePath(), "game-log.txt");

            logWriter = new PrintWriter(new FileWriter(logFile, true), true);

            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.setApplicationLogger(new ApplicationLogger() {
                @Override
                public void log(String tag, String message) {
                    writeLog("[LOG]", tag, message);
                }

                @Override
                public void log(String tag, String message, Throwable exception) {
                    writeLog("[LOG]", tag, message);
                    exception.printStackTrace(logWriter);
                }

                @Override
                public void error(String tag, String message) {
                    writeLog("[ERROR]", tag, message);
                }

                @Override
                public void error(String tag, String message, Throwable exception) {
                    writeLog("[ERROR]", tag, message);
                    exception.printStackTrace(logWriter);
                }

                @Override
                public void debug(String tag, String message) {
                    writeLog("[DEBUG]", tag, message);
                }

                @Override
                public void debug(String tag, String message, Throwable exception) {
                    writeLog("[DEBUG]", tag, message);
                    exception.printStackTrace(logWriter);
                }
            });
        } catch (IOException e) {
            throw new GdxRuntimeException("Failed to initialize log file", e);
        }
    }

    public static LogFileManager getInstance() {
        if (instance == null) {
            instance = new LogFileManager();
        }
        return instance;
    }

    public void writeLog(String level, String tag, String message) {
        logWriter.println(level + " " + tag + ": " + message);
        logWriter.flush();
    }

    public void dispose() {
        if (logWriter != null) {
            logWriter.close();
        }
    }
}
