package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateBookTests extends BaseTest {

    @Test
    public void updateBook_withValidData_shouldReturn200AndUpdatedContent() {
        int bookId = 456;
        logger.info("Scenario: Successfully update book details with valid data for book ID: " + bookId);

        Book updatedBook = new Book(
                bookId,
                "Updated Title",
                "Updated description",
                152,
                "Updated excerpt",
                "2021-05-10T00:00:00"
        );

        logger.debug("Request: \n" + updatedBook.toString());

        Response response = BookEndpoints.updateBook(bookId, updatedBook);

        logger.debug("Response: " + response.asPrettyString());
        logger.debug("Status: " + response.getStatusCode());

        assertEquals(200, response.getStatusCode(), "Expected status code 200 after successful update");

        String updatedTitle = response.jsonPath().getString("title");
        assertEquals("Updated Title", updatedTitle, "Title in response should match updated value");

        logger.info("Book ID " + bookId + " updated successfully with new title: " + updatedTitle);
    }

    @Test
    public void updateBook_withNonExistentId_shouldReturn404AndErrorMessage() {
        int nonExistentId = -999999;
        logger.info("Scenario: Attempt to update a book with non-existent ID: " + nonExistentId);

        Book updatedBook = new Book(
                nonExistentId,
                "Updated Title",
                "Updated description",
                152,
                "Updated excerpt",
                "2021-05-10T00:00:00"
        );

        logger.debug("Request: \n" + updatedBook.toString());

        Response response = BookEndpoints.updateBook(nonExistentId, updatedBook);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(404, response.getStatusCode(), "Expected 404 for updating a non-existent book");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for book ID: " + nonExistentId);
    }

    @Test
    public void updateBook_withEmptyTitle_shouldReturn400AndTitleError() {
        int bookId = 15;
        logger.info("Scenario: Attempt to update a book with an empty title for ID: " + bookId);

        Book updatedBook = new Book(
                bookId,
                "",
                "Updated description",
                152,
                "Updated excerpt",
                "2021-05-10T00:00:00"
        );

        logger.debug("Request: \n" + updatedBook.toString());

        Response response = BookEndpoints.updateBook(bookId, updatedBook);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(),
                "Expected status code 400 for invalid request body");

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("title"), "Invalid request, 'Title' is missing");

        logger.info("Validation error returned as expected for missing title during book update.");
    }

}
