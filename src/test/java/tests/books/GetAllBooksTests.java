package tests.books;

import base.BaseTest;
import endpoints.BookEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetAllBooksTests extends BaseTest {

    @Test
    public void getAllBooks_shouldReturn200AndList(){
        logger.info("Running happy path: get all books");

        Response response = BookEndpoints.getAllBooks();

        logger.info("Response status: " + response.getStatusCode());
        logger.debug("Response: " + response.asPrettyString());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertFalse(response.jsonPath().getList("$").isEmpty());
    }
}
