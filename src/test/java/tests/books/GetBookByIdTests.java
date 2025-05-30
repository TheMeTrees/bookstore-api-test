package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GET /Books/{id} endpoint
 * */
public class GetBookByIdTests extends BaseTest {

    @Test
    public void getBookByValidId_shouldReturn200AndValidBook() {
        int bookId = 15;
        logger.info("Scenario: Successfully retrieve book details by ID " + bookId);

        Response response = BookEndpoints.getBookById(bookId);

        logger.debug("Response: " + response.asPrettyString());

        JsonPath book = response.jsonPath();
        int returnedBookId = book.getInt("id");
        assertEquals(bookId, returnedBookId);

        assertInstanceOf(Integer.class, response.jsonPath().get("id"), "Book ID should be integer");

        assertNotNull(book.get("title"), "Book title should not be null");
        assertFalse(book.get("title").toString().isBlank(), "Book title should not be blank");

        assertNotNull(book.get("description"), "Book description should not be null");
        assertFalse(book.get("description").toString().isBlank(), "Book description should not be blank");

        assertNotNull(book.get("pageCount"), "Book pageCount should not be null");
        assertInstanceOf(Integer.class, book.get("pageCount"), "Book pageCount should be int");

        assertNotNull(book.get("excerpt"), "Book excerpt should not be null");
        assertFalse(book.get("excerpt").toString().isBlank(), "Book excerpt should not be blank");

        assertNotNull(book.get("publishDate"), "Publish date should not be null");
        assertTrue(book.get("publishDate").toString().matches("\\d{4}-\\d{2}-\\d{2}.*"),
                "Publish date should be in \"YYYY-MM-DD’T’HH:mm:ss.SSS\" format");

        logger.info("Book with ID " + bookId + " validated successfully.");
    }

    @Test
    public void getBookByNonExistentId_shouldReturn404AndErrorMessage() {
        int invalidBookId = -999999;
        logger.info("Scenario: Book not found with ID " + invalidBookId);

        Response response = BookEndpoints.getBookById(invalidBookId);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(404, response.getStatusCode(), "Expected 404 for non-existent book ID");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for missing book ID: " + invalidBookId);
    }

    @Test
    public void getBookByInvalidIdFormat_shouldReturn400AndValidationError() {
        String invalidId = "a";
        logger.info("Scenario: Invalid book ID format using ID: " + invalidId);

        Response response = BookEndpoints.getBookById(invalidId);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(), "Expected status code 400 for invalid ID format");

        String errorMessage = response.jsonPath().getList("errors.id").getFirst().toString();
        assertTrue(errorMessage.contains("not valid"), "Expected validation error message for invalid ID format");

        logger.info("Validation error confirmed for non-numeric ID: " + invalidId);
    }
}
