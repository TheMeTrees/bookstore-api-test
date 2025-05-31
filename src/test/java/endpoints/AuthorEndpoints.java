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

    public static Response getAllAuthors() {

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response getAuthorById(int id) {

        String url = ENDPOINT + "/" + id;

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response getAuthorById(String id) {

        String url = ENDPOINT + "/" + id;

        return given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response createAuthor(Author author) {

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

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }

    public static Response deleteAuthor(String id) {

        String url = ENDPOINT + "/" + id;

        return given()
                .when()
                .delete(url)
                .then()
                .extract().response();
    }
}
