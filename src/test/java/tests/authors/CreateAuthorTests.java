package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import models.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAuthorTests extends BaseTest {

    @Test
    public void createAuthor_shouldReturn200AndAuthorId() {
        logger.info("Scenario: Successfully add a new author with valid data");

        Author author = new Author(0, 1, "Dimitris", "Karakasidis");
        Response response = AuthorEndpoints.createAuthor(author);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getInt("id") > 0);
    }

    @Test
    public void createAuthor_missingFirstName_shouldReturn400() {
        logger.info("Running edge case: create author with missing first name");

        Author author = new Author(0, 1, null, "Tester");
        Response response = AuthorEndpoints.createAuthor(author);

        logger.debug("Response code: " + response.getStatusCode());
        assertTrue(response.getStatusCode() >= 400);
    }
}
