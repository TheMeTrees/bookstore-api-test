package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import models.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateAuthorTests extends BaseTest {

    @Test
    public void updateExistingAuthor_shouldReturn200() {
        logger.info("Running happy path: update author");

        Author updated = new Author(1, 1, "Updated", "Name");
        Response response = AuthorEndpoints.updateAuthor(1, updated);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertEquals("Updated", response.jsonPath().getString("firstName"));
    }

    @Test
    public void updateNonExistentAuthor_shouldReturn404() {
        logger.info("Running edge case: update non-existent author");

        Author author = new Author(99999, 1, "Ghost", "User");
        Response response = AuthorEndpoints.updateAuthor(99999, author);

        logger.debug("Response code: " + response.getStatusCode());
        assertEquals(404, response.getStatusCode());
    }
}
