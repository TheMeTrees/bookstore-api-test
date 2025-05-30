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
    private static final TestLogger logger = new TestLogger(BookEndpoints.class);

    public static Response getAllBooks() {
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
        String url = ENDPOINT + "/" + id;
        logger.info("Fetching book by ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response getBookById(String id) {
        String url = ENDPOINT + "/" + id;
        logger.info("Fetching book by ID (string): " + id);

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }


    public static Response createBook(Book book) {
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
        String url = ENDPOINT + "/" + id;
        logger.info("Updating book with ID: " + id);
        logger.debug("Updating book: " + book.getTitle());

        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .put(url)
                .then()
                .extract().response();
    }

    public static Response deleteBook(int id) {
        String url = ENDPOINT + "/" + id;
        logger.info("Deleting book with ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }

    public static Response deleteBook(String id) {
        String url = ENDPOINT + "/" + id;
        logger.info("Deleting book with ID: " + id);
        logger.info("Request URI: " + RestAssured.baseURI + ENDPOINT);

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }
}
