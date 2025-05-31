package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import models.Author;
import models.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PUT /Authors/{id} endpoint
 * */

@ExtendWith(LogAssertionErrorsRule.class)
public class UpdateAuthorTests extends BaseTest {

    @Test
    public void updateAuthor_withValidData_shouldReturn200AndUpdatedContent() {
        int authorId = 24;
        logger.info("Scenario: Successfully update Author details with valid data for Author ID: " + authorId);

        Author updatedAuthor = new Author(
                authorId,
                10,
                "First Name 24",
                "Updated Last Name"
        );

        logger.debug("Request: \n" + updatedAuthor.toString());

        Response response = AuthorEndpoints.updateAuthor(authorId, updatedAuthor);

        logger.debug("Response: " + response.asPrettyString());
        logger.debug("Status: " + response.getStatusCode());

        assertEquals(200, response.getStatusCode(), "Expected status code 200 after successful update");

        String updatedLastName = response.jsonPath().getString("lastName");
        assertEquals("Updated Last Name", updatedLastName, "lastName in response should match updated value");

        logger.info("Author ID " + authorId + " updated successfully with new last name: " + updatedLastName);
    }

    @Test
    public void updateAuthor_withNonExistentId_shouldReturn404AndErrorMessage() {
        int nonExistentId = -999999;
        logger.info("Scenario: Attempt to update a Author with non-existent ID: " + nonExistentId);

        Author updatedAuthor = new Author(
                nonExistentId,
                10,
                "Updated First Name",
                "Last Name"
        );

        logger.debug("Request: \n" + updatedAuthor.toString());

        Response response = AuthorEndpoints.updateAuthor(nonExistentId, updatedAuthor);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(404, response.getStatusCode(), "Expected 404 for updating a non-existent Author");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for Author ID: " + nonExistentId);
    }

    @Test
    public void updateAuthor_withEmptyFirstName_shouldReturn400AndFirstNameError() {
        int AuthorId = 15;
        logger.info("Scenario: Attempt to update an Author with an empty first name for ID: " + AuthorId);

        Author updatedAuthor = new Author(
                AuthorId,
                10,
                "",
                "Last Name"
        );

        logger.debug("Request: \n" + updatedAuthor.toString());

        Response response = AuthorEndpoints.updateAuthor(AuthorId, updatedAuthor);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(),
                "Expected status code 400 for invalid request body");

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("firstName"), "Invalid request, 'firstName' is missing");

        logger.info("Validation error returned as expected for missing firstName during Author update.");
    }
}
