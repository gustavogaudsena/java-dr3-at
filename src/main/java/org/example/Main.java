package org.example;

import io.javalin.Javalin;
import org.example.controller.GenericController;
import org.example.controller.TarefasController;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8").start(7001);

        new TarefasController().registrarRotas(app);
        new GenericController().registrarRotas(app);
    }
}