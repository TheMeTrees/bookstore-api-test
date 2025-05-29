package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteAuthorTests extends BaseTest {

    @Test
    public void deleteAuthorById_shouldReturn204() {
        logger.info("Running happy path: delete author by ID");

        Response response = AuthorEndpoints.deleteAuthor(2); // use a valid ID
        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void deleteAuthorWithInvalidId_shouldReturn404() {
        logger.info("Running edge case: delete non-existent author");

        Response response = AuthorEndpoints.deleteAuthor(99999);
        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(404, response.getStatusCode());
    }
}
