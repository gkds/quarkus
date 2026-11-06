package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

import org.acme.cabinetmedical.started.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PersonResourceTest {


    @Test
    public void testCreatePersonEndpoint() {
        Patient personToCreate= new Patient();
        personToCreate.birth = LocalDate.of(2001,5,13);
        personToCreate.lname = "Connors";
        personToCreate.fname= "Sarah";

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("lname", "Connors"); // Cast
        requestParams.put("fname", "Sarah");
        requestParams.put("birth", "2001-05-13");
        requestParams.put("status", "0");

        request.body(personToCreate);
        Response response = request.post("/persons");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 201);


        given()
                .when().get("/persons")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":1,\"name\":\"Connors\",\"fname\":\"Sarah\",\"birth\":\"2001-05-13\",\"status\":null}]"));
    }

}
