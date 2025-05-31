package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import endpoints.AuthorEndpoints;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DELETE /Authors/{id} endpoint
 * */

@ExtendWith(LogAssertionErrorsRule.class)
public class DeleteAuthorTests extends BaseTest {

    @Test
    public void deleteAuthor_withValidId_shouldReturn204AndBeDeleted() {
        int authorId = 15;
        logger.info("Scenario: Successfully delete an Author by ID " + authorId);

        Response response = AuthorEndpoints.deleteAuthor(authorId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(200, response.getStatusCode(), "Expected 200 No Content after deletion");

        Response getResponse = AuthorEndpoints.getAuthorById(authorId);
        logger.debug("GET response code after deletion: " + getResponse.getStatusCode());
        assertEquals(404, getResponse.getStatusCode(), "Expected 404 for deleted Author");

        logger.info("Author ID " + authorId + " was successfully deleted");
    }

    @Test
    public void deleteAuthor_withNonExistentId_shouldReturn404AndErrorMessage() {
        int nonExistentId = -999999;
        logger.info("Scenario: Attempt to delete a Author that does not exist with ID: " + nonExistentId);

        Response response = AuthorEndpoints.deleteAuthor(nonExistentId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(404, response.getStatusCode(), "Expected 404 for deletion of non-existent Author");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for deletion of Author ID: " + nonExistentId);
    }

    @Test
    public void deleteAuthor_withInvalidIdFormat_shouldReturn400AndValidationError() {
        String invalidId = "a";
        logger.info("Scenario: Attempt to delete a Author using invalid ID format: " + invalidId);

        Response response = AuthorEndpoints.deleteAuthor(invalidId);

        logger.debug("Response code: " + response.getStatusCode());

        assertEquals(400, response.getStatusCode(), "Expected 400 for invalid ID format");

        String body = response.getBody().asString();
        assertTrue(body.contains("One or more validation errors occurred."),
                "Expected validation error message for invalid ID format");

        logger.info("Validation error confirmed for malformed Author ID: " + invalidId);
    }
}

/*
 * NOTE:
 * The following test scenarios have been outlined but are not implemented
 * due to limitations of the FakeRestAPI. These include:
 *
 * - Testing forbidden deletion attempts due to insufficient permissions
 * - Testing unauthorized deletion attempts
 * - Preventing deletion when the author is linked to existing books
*/