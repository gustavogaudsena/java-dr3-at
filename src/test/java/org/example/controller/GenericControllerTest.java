package org.example.controller;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.example.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericControllerTest {

    @BeforeEach
    public void limpaDatabase() {
        Database.data.clear();
    }

    public Javalin setApp() {
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8");
        new GenericController().registrarRotas(app);
        return app;
    }

    @Test
    @DisplayName(value = "GET no endpoint /hello deve retornar 'Hello, Javalin!'")
    public void testHelloEndpoint() {
        Javalin app = setApp();
        JavalinTest.test(app, (_, client) -> {
            var response = client.get("/hello");
            assertEquals(200, response.code());
            assert response.body() != null;
            assertEquals("Hello, Javalin!", response.body().string());
        });
    }
}