package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteBookTests extends BaseTest {

    @Test
    public void deleteBook_withValidId_shouldReturn204AndBeGone() {
        int bookId = 15;
        logger.info("Scenario: Successfully delete a book by ID " + bookId);

        Response response = BookEndpoints.deleteBook(bookId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(200, response.getStatusCode(), "Expected 200 No Content after deletion");

        Response getResponse = BookEndpoints.getBookById(bookId);
        logger.debug("GET response code after deletion: " + getResponse.getStatusCode());
        assertEquals(404, getResponse.getStatusCode(), "Expected 404 for deleted book");

        logger.info("Book ID " + bookId + " was successfully deleted");
    }

    @Test
    public void deleteBook_withNonExistentId_shouldReturn404AndErrorMessage() {
        int nonExistentId = -999999;
        logger.info("Scenario: Attempt to delete a book that does not exist with ID: " + nonExistentId);

        Response response = BookEndpoints.deleteBook(nonExistentId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(404, response.getStatusCode(), "Expected 404 for deletion of non-existent book");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for deletion of book ID: " + nonExistentId);
    }

    @Test
    public void deleteBook_withInvalidIdFormat_shouldReturn400AndValidationError() {
        String invalidId = "a";
        logger.info("Scenario: Attempt to delete a book using invalid ID format: " + invalidId);

        Response response = BookEndpoints.deleteBook(invalidId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(400, response.getStatusCode(), "Expected 400 for invalid ID format");

        String body = response.getBody().asString();
        assertTrue(body.contains("One or more validation errors occurred."),
                "Expected validation error message for invalid ID format");

        logger.info("Validation error confirmed for malformed book ID: " + invalidId);
    }
}