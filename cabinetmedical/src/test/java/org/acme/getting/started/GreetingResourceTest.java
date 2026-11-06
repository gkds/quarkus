package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/user")
                .then()
                .statusCode(200)
                .body(containsString("{\"name\":\"Georges\","))

                .body(containsString("\"location\":\"Miami\"}"));
    }


}