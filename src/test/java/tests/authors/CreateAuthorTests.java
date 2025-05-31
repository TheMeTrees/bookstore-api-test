package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import io.restassured.response.Response;
import models.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for POST /Authors endpoint
 * */

@ExtendWith(LogAssertionErrorsRule.class)
public class CreateAuthorTests extends BaseTest {

    // Happy paths
    @Test
    public void createAuthor_shouldReturn200AndAuthorId() {
        int authorId = 8888838;
        logger.info("Scenario: Successfully add a new author with valid data");

        Author author = new Author(
                authorId,
                901,
                "Dimitris",
                "Karakasidis");

        Response response = AuthorEndpoints.createAuthor(author);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());

        assertEquals(200, response.getStatusCode(), "Expected status code 200 for created resource");

        int createdId = response.jsonPath().getInt("id");
        assertEquals(authorId, createdId, "Returned author ID should be " + authorId);

        String actualFirstName = response.jsonPath().getString("firstName");
        assertEquals("Dimitris", actualFirstName, "Returned firstName should be Dimitris");

        logger.info("Author successfully created with ID: " + createdId);
    }


    //Edge cases

    @Test
    public void createAuthor_missingFirstName_shouldReturn400() {
        logger.info("Scenario: Create author with missing firstName");

        Author author = new Author(
                8888838,
                901,
                null,
                "Karakasidis");

        Response response = AuthorEndpoints.createAuthor(author);

        logger.debug("Response code: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());

        assertEquals(400, response.getStatusCode(),
                "Expected status code 400 for invalid request body");

        logger.info("Validation error correctly returned for invalid author creation request.");
    }
}

/*
 * NOTE:
 * The following test scenarios have been outlined but are not implemented
 * due to limitations of the FakeRestAPI. These include:
 *
 * - Rejecting a duplicate author if duplicates are not allowed
 * - Unauthorized attempt to add an author
 * - Forbidden attempt to add an author due to insufficient permissions
 */