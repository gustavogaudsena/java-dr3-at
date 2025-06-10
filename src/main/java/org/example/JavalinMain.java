package org.example;

import io.javalin.Javalin;

public class JavalinMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.get("/hello", context -> context.result("Hello, Javalin!"));
        app.start(7001);
    }
}
