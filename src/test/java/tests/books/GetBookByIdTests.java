package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetBookByIdTests extends BaseTest {

    @Test
    public void getBookByValidId_shouldReturn200() {
        logger.info("Running happy path: get book by ID");

        Response response = BookEndpoints.getBookById(1);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    public void getBookByInvalidId_shouldReturn404() {
        logger.info("Running edge case: get book by invalid ID");

        Response response = BookEndpoints.getBookById(-999);

        logger.debug("Response code: " + response.getStatusCode());
        assertEquals(404, response.getStatusCode());
    }
}
