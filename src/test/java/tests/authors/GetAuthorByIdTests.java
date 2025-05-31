package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import endpoints.AuthorEndpoints;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GET /Author/{id} endpoint
 * */

@Epic("Authors API")
@Feature("GET /Authors/{id}")
@Story("Retrieve an Author by id")
@Severity(SeverityLevel.CRITICAL)
@ExtendWith(LogAssertionErrorsRule.class)
public class GetAuthorByIdTests extends BaseTest {

    @Test
    public void getAuthorByValidId_shouldReturn200AndValidAuthor() {
        int AuthorId = 15;
        logger.info("Scenario: Successfully retrieve Author details by ID " + AuthorId);

        Response response = AuthorEndpoints.getAuthorById(AuthorId);

        logger.debug("Response: " + response.asPrettyString());

        JsonPath Author = response.jsonPath();
        int returnedAuthorId = Author.getInt("id");
        assertEquals(AuthorId, returnedAuthorId);

        assertInstanceOf(Integer.class, response.jsonPath().get("id"), "Author ID should be integer");

        assertNotNull(Author.get("idBook"), "Author idBook should not be null");
        assertFalse(Author.get("idBook").toString().isBlank(), "Author idBook should not be blank");

        assertNotNull(Author.get("firstName"), "Author firstName should not be null");
        assertFalse(Author.get("firstName").toString().isBlank(), "Author firstName should not be blank");

        assertNotNull(Author.get("lastName"), "Author lastName should not be null");
        assertFalse(Author.get("lastName").toString().isBlank(), "Author lastName should not be blank");

        logger.info("Author with ID " + AuthorId + " validated successfully.");
    }

    @Test
    public void getAuthorByNonExistentId_shouldReturn404AndErrorMessage() {
        int invalidAuthorId = -999999;
        logger.info("Scenario: Author not found with ID " + invalidAuthorId);

        Response response = AuthorEndpoints.getAuthorById(invalidAuthorId);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(404, response.getStatusCode(), "Expected 404 for non-existent Author ID");

        String body = response.getBody().asString();
        assertTrue(body.contains("Not Found"),
                "Expected error message to contain 'Not found'");

        logger.info("Verified 404 and error message for missing Author ID: " + invalidAuthorId);
    }

    @Test
    public void getAuthorByInvalidIdFormat_shouldReturn400AndValidationError() {
        String invalidId = "a";
        logger.info("Scenario: Invalid Author ID format using ID: " + invalidId);

        Response response = AuthorEndpoints.getAuthorById(invalidId);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response body: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(), "Expected status code 400 for invalid ID format");

        String errorMessage = response.jsonPath().getList("errors.id").getFirst().toString();
        assertTrue(errorMessage.contains("not valid"), "Expected validation error message for invalid ID format");

        logger.info("Validation error confirmed for non-numeric ID: " + invalidId);
    }
}

/*
 * NOTE:
 * The following test scenarios have been outlined but are not implemented
 * due to limitations of the FakeRestAPI. These include:
 *
 * - Unauthorized access to author details or author list
 * - Forbidden access due to insufficient permissions
 */