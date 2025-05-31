package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * A JUnit 5 extension that logs assertion error messages to the TestLogger
 * when a test fails.
 */
public class LogAssertionErrorsRule implements TestWatcher {

    /**
     * This method is called when a test fails.
     * It logs the error message from the failed assertion to the TestLogger.
     *
     * @param context The context of the test
     * @param cause   The Throwable cause of the failure
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String errorMessage = cause.getMessage();
        TestLogger.getThreadLogger().error(errorMessage);
    }
}
