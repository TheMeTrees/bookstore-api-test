package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.LogAssertionErrorsRule;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GET /Books endpoint
 * */

@ExtendWith(LogAssertionErrorsRule.class)
public class GetAllBooksTests extends BaseTest {

    @Test
    public void getAllBooks_shouldReturn200AndList(){
        logger.info("Scenario: Successfully retrieve a list of books");

        Response response = BookEndpoints.getAllBooks();

        logger.info("Response status: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());
        assertEquals(200, response.getStatusCode(), "Expected status code 200");

        int listSize = response.jsonPath().getList("$").size();
        assertTrue(listSize > 0, "Expected a non-empty list of books");
        logger.info("Received " + listSize + " books");
    }

    @Test
    public void getAllBooks_shouldContainValidBookFields() {
        logger.info("Scenario: Validate structure and content of the first book in the response");

        Response response = BookEndpoints.getAllBooks();

        assertEquals(200, response.getStatusCode());

        List<Map<String, Object>> books = response.jsonPath().getList("$");
        assertFalse(books.isEmpty(), "Books list should not be empty");

        Map<String,Object> book = books.getFirst();

        assertNotNull(book.get("id"), "Book ID should not be null");
        assertInstanceOf(Integer.class, book.get("id"), "Book ID should be integer");

        assertNotNull(book.get("title"), "Book title should not be null");
        assertFalse(book.get("title").toString().isBlank(), "Book title should not be blank");

        assertNotNull(book.get("description"), "Book description should not be null");
        assertFalse(book.get("description").toString().isBlank(), "Book description should not be blank");

        assertNotNull(book.get("pageCount"), "Book pageCount should not be null");
        assertInstanceOf(Integer.class, book.get("pageCount"), "Book pageCount should be integer");

        assertNotNull(book.get("excerpt"), "Book excerpt should not be null");
        assertFalse(book.get("excerpt").toString().isBlank(), "Book excerpt should not be blank");

        assertNotNull(book.get("publishDate"), "Publish date should not be null");
        assertTrue(book.get("publishDate").toString().matches("\\d{4}-\\d{2}-\\d{2}.*"),
                "Publish date should be in \"YYYY-MM-DD’T’HH:mm:ss.SSS\" format");

        logger.info("Book validated for required fields.");
    }
}

/*
 * NOTE:
 * The following test scenarios have been outlined but are not implemented
 * due to limitations of the FakeRestAPI. These include:
 *
 * - Unauthorized access attempt
 * - Forbidden access due to insufficient permissions
 *
 */