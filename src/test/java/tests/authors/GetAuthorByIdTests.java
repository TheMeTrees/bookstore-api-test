package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetAuthorByIdTests extends BaseTest {

    @Test
    public void getAuthorByValidId_shouldReturn200() {
        logger.info("Running happy path: get author by ID");

        Response response = AuthorEndpoints.getAuthorById(1);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("firstName"));
    }

    @Test
    public void getAuthorByInvalidId_shouldReturn404() {
        logger.info("Running edge case: get author by invalid ID");

        Response response = AuthorEndpoints.getAuthorById(-999);

        logger.debug("Response code: " + response.getStatusCode());
        assertEquals(404, response.getStatusCode());
    }
}
