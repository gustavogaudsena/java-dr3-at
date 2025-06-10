package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.models.EchoEndpoint;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GenericController {

    public void getHello(Context context) {
        context.contentType("text/plain; charset=UTF-8");
        context.result("Hello, Javalin!");
    }

    public void getStatus(Context context) {
        Map<String, String> response = new HashMap<>();
        context.contentType("application/json;");
        response.put("status", "ok");
        response.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        context.status(200);
        context.json(response);
    }

    public void getSaudacao(Context context) {
        String nome = context.pathParam("nome");
        String saudacao = String.format("Olá, %s", nome);
        var response = Map.of("mensagem", saudacao);
        context.json(response);
    }

    public void postEcho(Context context) {
        EchoEndpoint body = context.bodyAsClass(EchoEndpoint.class);
        String mensagem = body.mensagem;
        context.result(mensagem);
    }


    public void registrarRotas(Javalin app) {
        app.get("/hello", this::getHello);
        app.get("/status", this::getStatus);
        app.get("/saudacao/{nome}", this::getSaudacao);
        app.post("/echo", this::postEcho);
    }

}
