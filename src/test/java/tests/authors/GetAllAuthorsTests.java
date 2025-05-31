package tests.authors;

import base.BaseTest;
import endpoints.AuthorEndpoints;
import endpoints.AuthorEndpoints;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GET /Authors endpoint
 * */

@Epic("Authors API")
@Feature("GET /Authors")
@Story("Retrieve all Authors")
@Severity(SeverityLevel.CRITICAL)
@ExtendWith(LogAssertionErrorsRule.class)
public class GetAllAuthorsTests extends BaseTest {

    @Test
    public void getAllAuthors_shouldReturn200AndList(){
        logger.info("Scenario: Successfully retrieve a list of Authors");

        Response response = AuthorEndpoints.getAllAuthors();

        logger.info("Response status: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode(), "Expected status code 200");

        int listSize = response.jsonPath().getList("$").size();
        assertEquals(589, listSize, "Expected a non-empty list of Authors");
        logger.info("Received " + listSize + " Authors");
    }

    @Test
    public void getAllAuthors_shouldContainValidAuthorFields() {
        logger.info("Scenario: Validate structure and content of the first Author in the response");

        Response response = AuthorEndpoints.getAllAuthors();

        assertEquals(200, response.getStatusCode());

        List<Map<String, Object>> Authors = response.jsonPath().getList("$");
        assertFalse(Authors.isEmpty(), "Authors list should not be empty");

        Map<String,Object> Author = Authors.getFirst();

        assertNotNull(Author.get("id"), "Author ID should not be null");
        assertInstanceOf(Integer.class, Author.get("id"), "Author ID should be integer");

        assertNotNull(Author.get("idBook"), "Author idBook should not be null");
        assertFalse(Author.get("idBook").toString().isBlank(), "Author idBook should not be blank");

        assertNotNull(Author.get("firstName"), "Author firstName should not be null");
        assertFalse(Author.get("firstName").toString().isBlank(), "Author firstName should not be blank");

        assertNotNull(Author.get("lastName"), "Author lastName should not be null");
        assertFalse(Author.get("lastName").toString().isBlank(), "Author lastName should not be blank");

        logger.info("Author validated for required fields.");
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
