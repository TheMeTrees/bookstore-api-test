package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Book;
import utils.ConfigManager;
import utils.TestLogger;

import static io.restassured.RestAssured.given;

/**
 * Handles all REST API interactions for the /Books endpoint.
 */
public class BookEndpoints {

    private static final String ENDPOINT = ConfigManager.getInstance().getProperty("books.endpoint");

    public static Response getAllBooks() {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Sending GET request to fetch all books");
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response getBookById(int id) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Fetching book by ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        String url = ENDPOINT + "/" + id;

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response getBookById(String id) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Fetching book by ID (string): " + id);

        String url = ENDPOINT + "/" + id;

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }


    public static Response createBook(Book book) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Creating book: " + book.getTitle());

        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post(ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response updateBook(int id, Book book) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Updating book with ID: " + id);
        logger.debug("Updating book: " + book.getTitle());

        String url = ENDPOINT + "/" + id;

        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .put(url)
                .then()
                .extract().response();
    }

    public static Response deleteBook(int id) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Deleting book with ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        String url = ENDPOINT + "/" + id;

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }

    public static Response deleteBook(String id) {
        TestLogger logger = TestLogger.getThreadLogger();
        logger.info("Deleting book with ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        String url = ENDPOINT + "/" + id;

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }
}
