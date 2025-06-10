package org.example.cruds;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import org.example.models.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TarefasCrud {
    public static void main(String[] args) {
        List<Tarefa> data = new ArrayList<>();
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8").start(7001);

        //GET ENDPOINTS
        app.get("/tarefas", context -> {
            var response = Map.of("status", "ok", "data", data);
            context.status(200).json(response);
        });

        //POST ENDPOINTS
        app.post("/tarefas", context -> {
            Tarefa newTarefa = context.bodyAsClass(Tarefa.class);
            newTarefa.initialize();
            data.add(newTarefa);
            context.status(HttpStatus.CREATED);
            var response = Map.of("status", "ok", "id", newTarefa.getId());
            context.json(response);
        });
    }
}
