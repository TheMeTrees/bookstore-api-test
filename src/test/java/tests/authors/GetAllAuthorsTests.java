package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllAuthorsTests extends BaseTest {

    @Test
    public void getAllAuthors_shouldReturn200AndList() {
        logger.info("Running happy path: get all authors");

        Response response = AuthorEndpoints.getAllAuthors();

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("$").size() > 0);
    }
}
