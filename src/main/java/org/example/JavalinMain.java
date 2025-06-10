package org.example;

import io.javalin.Javalin;

public class JavalinMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/hello", context -> context.result("Olá, Javalin!"));
    }
}
