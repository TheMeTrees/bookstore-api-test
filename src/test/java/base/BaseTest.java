package base;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.*;
import utils.ConfigManager;
import utils.TestLogger;

/**
 * BaseTest is the parent class for all test classes.
 * It sets up the testing environment using RestAssured,
 * initializes configuration and logging, and attaches logs to Allure reports.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected TestLogger logger;

    /**
     * Runs once before all tests in the class.
     * Sets the RestAssured base URI and timeout using ConfigManager.
     */
    @BeforeAll
    public void setupFramework() {
        ConfigManager config = ConfigManager.getInstance();

        String baseUrl = config.getProperty("base.url");
        String versionFromCLI = System.getProperty("api.version");
        String version = (versionFromCLI != null && !versionFromCLI.isBlank())
                ? versionFromCLI
                : config.getProperty("api.version");
        String fullURL = baseUrl + version;
        int connectionTimeout = config.getIntProperty("connection.timeout");
        int socketTimeout = config.getIntProperty("socket.timeout");
        int connectionManagerTimeout = config.getIntProperty("connection.manager.timeout");

        RestAssured.baseURI = fullURL;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config =  RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", connectionTimeout)
                        .setParam("http.socket.timeout", socketTimeout)
                        .setParam("http.connection-manager.timeout", (long) connectionManagerTimeout));

        System.out.printf("[BaseTest] Using base URI: %s and version: %s ", baseUrl, version);
    }

    /**
     * Runs before each individual test.
     * Initializes the class-level logger and logs the test start.
     *
     * @param testInfo JUnit-provided metadata about the test
     */
    @BeforeEach
    public void logTestStart(TestInfo testInfo) {
        logger = new TestLogger(this.getClass());
        logger.info("========== Starting Test: " + testInfo.getDisplayName() + " ==========");
    }

    /**
     * Runs after each individual test.
     * Logs the test end, closes the logger, and attaches the log file to the Allure report.
     *
     * @param testInfo JUnit-provided metadata about the test
     */
    @AfterEach
    public void logTestEnd(TestInfo testInfo) {
        logger.info("========== Ending Test: " + testInfo.getDisplayName() + " ==========");
        logger.close();
    }
}