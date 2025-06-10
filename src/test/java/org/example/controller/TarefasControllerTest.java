package org.example.controller;

import io.javalin.Javalin;
import org.example.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class TarefasControllerTest {
    private static Javalin app;
    @BeforeEach
    public void limpaDatabase() {
        Database.data.clear();
    }

    @BeforeAll
    public static void setApp() {
        app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8");
        new TarefasController().registrarRotas(app);
    }
}