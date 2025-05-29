package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Author;
import utils.ConfigManager;
import utils.TestLogger;

import static io.restassured.RestAssured.given;

/**
 * Handles all REST API interactions for the /Authors endpoint.
 */
public class AuthorEndpoints {

    private static final String ENDPOINT = ConfigManager.getInstance().getProperty("authors.endpoint");
    private static final TestLogger logger = new TestLogger(AuthorEndpoints.class);

    public static Response getAllAuthors() {
        logger.info("Sending GET request to fetch all authors");

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response getAuthorById(int id) {
        String url = ENDPOINT + "/" + id;
        logger.info("Fetching author by ID: " + id);

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response createAuthor(Author author) {
        logger.info("Creating author: " + author.getFirstName() + " " + author.getLastName());

        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .post(ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response updateAuthor(int id, Author author) {
        String url = ENDPOINT + "/" + id;
        logger.info("Updating author with ID: " + id);

        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .put(url)
                .then()
                .extract().response();
    }

    public static Response deleteAuthor(int id) {
        String url = ENDPOINT + "/" + id;
        logger.info("Deleting author with ID: " + id);

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }

}
