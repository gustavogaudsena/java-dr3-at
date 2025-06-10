package org.example;

import io.javalin.Javalin;
import org.example.models.EchoEndpoint;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class JavalinMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.get("/hello", context -> {
            context.contentType("text/plain; charset=UTF-8");
            context.result("Hello, Javalin!");
        });

        app.get("/status", context -> {
            Map<String, String> response = new HashMap<>();
            context.contentType("application/json;");
            response.put("status", "ok");
            response.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
            context.status(200);
            context.json(response);
        });

        app.post("/echo", context -> {
            EchoEndpoint body = context.bodyAsClass(EchoEndpoint.class);
            String mensagem = body.mensagem;
            context.result(mensagem);
        });

        app.get("/saudacao/{nome}", context -> {
            String nome = context.pathParam("nome");
            String saudacao = String.format("Olá, %s", nome);
            var response = Map.of("mensagem", saudacao);
            context.json(response);
        });

        app.start(7001);
    }
}
