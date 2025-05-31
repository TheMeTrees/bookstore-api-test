package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for POST /Books endpoint
 * */

@ExtendWith(LogAssertionErrorsRule.class)
public class CreateBookTests extends BaseTest {

    @Test
    public void createBook_shouldReturn200AndNewBook() {
        logger.info("Scenario: Successfully add a new book with valid data");

        Book requestBody = new Book(
                8888838,
                "New book",
                "Good book",
                200,
                "What a great book",
                "1998-01-13T00:00:00"
        );

        Response response = BookEndpoints.createBook(requestBody);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());

        assertEquals(200, response.getStatusCode(), "Expected status code 200 for created resource");

        int createdId = response.jsonPath().getInt("id");
        assertEquals(8888838, createdId, "Returned book ID should be 8888838");

        String title = response.jsonPath().getString("title");
        assertEquals("New book", title, "Returned title should match request");

        logger.info("Book successfully created with ID: " + createdId);
    }

    @Test
    public void createBook_missingTitle_shouldReturn400() {
        logger.info("Scenario: Create book with missing title");

        Book book = new Book(
                8888838,
                null,
                "Description",
                100,
                "Excerpt",
                "2025-01-01T00:00:00"
        );

        Response response = BookEndpoints.createBook(book);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(),
                "Expected status code 400 for invalid request body");

        logger.info("Validation error correctly returned for invalid book creation request.");
    }
}
