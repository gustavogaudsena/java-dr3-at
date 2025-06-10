package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.Database;
import org.example.models.Tarefa;

import java.util.Map;
import java.util.UUID;

public class TarefasController {


    public void getAll(Context context) {
            var response = Map.of("status", "ok", "data", Database.data);
            context.status(200).json(response);
    }

    public void getById(Context context) {
        var id = context.pathParam("id");
        var uuid = UUID.fromString(id);
        Tarefa item = Database.data.stream().filter(t -> t.getId().equals(uuid)).findFirst().orElse(null);
        var response = item == null
                ? Map.of("status", "error", "mensagem", "Item não encontrado")
                : Map.of("status", "ok", "data", item);

        var status = item == null
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        context.status(status).json(response);
    }

    public void createTarefa(Context context) {
        Tarefa newTarefa = context.bodyAsClass(Tarefa.class);
        newTarefa.initialize();
        Database.data.add(newTarefa);
        context.status(HttpStatus.CREATED);
        var response = Map.of("status", "ok", "id", newTarefa.getId());
        context.json(response);
    }

    public void registrarRotas(Javalin app) {
        app.get("/tarefas", this::getAll);
        app.get("/tarefas/{id}", this::getById);
        app.post("/tarefas", this::createTarefa);
    }
}
