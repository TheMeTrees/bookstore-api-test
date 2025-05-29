package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateBookTests extends BaseTest {

    @Test
    public void updateExistingBook_shouldReturn200() {
        logger.info("Running happy path: update book");

        Book updated = new Book(1, "Updated Title", "Updated Desc", 120, "Updated",
                "2025-12-31T00:00:00");
        Response response = BookEndpoints.updateBook(1, updated);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertEquals("Updated Title", response.jsonPath().getString("title"));
    }

    @Test
    public void updateNonExistentBook_shouldReturn404() {
        logger.info("Running edge case: update non-existent book");

        Book book = new Book(99999, "Fake", "Fake", 50, "Fake",
                "2025-01-01T00:00:00");
        Response response = BookEndpoints.updateBook(99999, book);

        logger.debug("Response code: " + response.getStatusCode());
        assertEquals(404, response.getStatusCode());
    }
}
