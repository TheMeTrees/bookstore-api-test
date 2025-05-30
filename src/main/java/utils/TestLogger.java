package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * TestLogger is a lightweight logging utility that supports per-class log files,
 * dynamic log level filtering, and timestamped log entries.
 *
 * <p>Logs are stored in {@code target/logs/<ClassName>.log} and can be
 * configured to show INFO, DEBUG, or ERROR levels via the JVM property:
 * {@code -Dlog.level=INFO}</p>
 */
public class TestLogger {

    private static final ThreadLocal<TestLogger> threadLocalLogger = new ThreadLocal<>();

    /**
     * Sets the TestLogger for the current thread.
     * Called from BaseTest to initialize a logger per test class.
     */
    public static void setThreadLogger(TestLogger logger) {
        threadLocalLogger.set(logger);
    }

    /**
     * Gets the TestLogger instance associated with the current thread.
     * Used from anywhere (e.g., BookEndpoints).
     */
    public static TestLogger getThreadLogger() {
        return threadLocalLogger.get();
    }

    /**
     * Enumeration of supported log levels.
     * Ordered by verbosity: ERROR < INFO < DEBUG
     */
    public enum LogLevel {
        ERROR, INFO, DEBUG
    }

    private final String className;
    private final PrintWriter writer;
    private final LogLevel currentLevel;

    /**
     * Constructs a TestLogger for the specified class.
     * Initializes the writer and determines the log level from system property {@code log.level}.
     *
     * @param className The class this logger is associated with
     */
    public TestLogger(Class<?> className) {
        this.className = className.getSimpleName();
        this.currentLevel = resolveLogLevel();

        try {
            File logDir = new File("target/logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            File logFile = new File(logDir, this.className + ".log");
            this.writer = new PrintWriter(new FileWriter(logFile, false));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logger for " + className, e);
        }
    }

    /**
     * Resolves the current log level from the system property {@code log.level}.
     * Defaults to {@code INFO} if unspecified or invalid.
     *
     * @return The effective LogLevel
     */
    private LogLevel resolveLogLevel() {
        String level = ConfigManager.getInstance().getResolvedProperty("log.level");

        try {
            return LogLevel.valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("[TestLogger] Invalid log level: '" + level + "'. Defaulting to INFO.");
            return LogLevel.INFO;
        }
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param msg The message to log
     */
    public void info(String msg) {
        log(LogLevel.INFO, msg);
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param msg The message to log
     */
    public void debug(String msg) {
        log(LogLevel.DEBUG, msg);
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param msg The message to log
     */
    public void error(String msg) {
        log(LogLevel.ERROR, msg);
    }

    /**
     * Writes the log entry if the specified level is equal to or higher than the current log level.
     *
     * @param level The log level of the message
     * @param msg   The message to log
     */
    private void log(LogLevel level, String msg) {
        if (level.ordinal() <= currentLevel.ordinal()) {
            String line = String.format("%s [%s] %s - %s",
                    LocalDateTime.now(), level, className, msg);
            writer.println(line);
            writer.flush();
        }
    }

    /**
     * Closes the underlying writer and releases the file handle.
     * Should be called at the end of each test.
     */
    public void close() {
        writer.close();
    }
}
