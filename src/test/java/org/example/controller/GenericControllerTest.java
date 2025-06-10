package org.example.controller;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.example.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericControllerTest {
    private static Javalin app;
    @BeforeEach
    public void limpaDatabase() {
        Database.data.clear();
    }

    @BeforeAll
    public static void setApp() {
        app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8");
        new GenericController().registrarRotas(app);
    }

    @Test
    public void testaHelloEndpoint() {
        JavalinTest.test(app, (_, client) -> {
            var response = client.get("/hello");
            assertEquals(200, response.code());
            assert response.body() != null;
            assertEquals("Hello, Javalin!", response.body().string());
        });
    }
}