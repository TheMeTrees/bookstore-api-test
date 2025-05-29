package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateBookTests extends BaseTest {

    @Test
    public void createBook_shouldReturn200AndBookId() {
        logger.info("Running happy path: create book");

        Book book = new Book(0, "Test Title", "Test Desc", 100, "Excerpt",
                "2025-01-01T00:00:00");
        Response response = BookEndpoints.createBook(book);

        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getInt("id") > 0);
    }

    @Test
    public void createBook_missingTitle_shouldReturn400() {
        logger.info("Running edge case: create book with missing title");

        Book book = new Book(0, null, "Description", 100, "Excerpt",
                "2025-01-01T00:00:00");
        Response response = BookEndpoints.createBook(book);

        logger.debug("Response code: " + response.getStatusCode());
        assertTrue(response.getStatusCode() >= 400);
    }
}
