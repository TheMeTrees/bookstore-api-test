package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteBookTests extends BaseTest {

    @Test
    public void deleteBookById_shouldReturn204() {
        logger.info("Running happy path: delete book by ID");

        Response response = BookEndpoints.deleteBook(2); // use a valid test ID
        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void deleteBookWithInvalidId_shouldReturn404() {
        logger.info("Running edge case: delete non-existent book");

        Response response = BookEndpoints.deleteBook(99999);
        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(404, response.getStatusCode());
    }
}