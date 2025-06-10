package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.example.Database;
import org.example.models.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TarefasControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void limpaDatabase() {
        Database.data.clear();
    }

    public Javalin setApp() {
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8");
        new TarefasController().registrarRotas(app);
        return app;
    }

    @Test
    @DisplayName(value = "Post no endpoint /tarefas deve retornar status 201")
    public void testPostNovaTarefa() {
        Javalin app = setApp();
        String mockDescricao = "Executando tarefa 8 do AT";
        String mockTitulo = "Tarefa 8";
        JavalinTest.test(app, (_, client) -> {
            String body = String.format("""
                        {
                          "descricao": "%s",
                          "titulo": "%s"
                        }
                    """, mockDescricao, mockTitulo);
            var response = client.post("/tarefas", body);

            assertEquals(201, response.code(), "Código deve ser 201");

            assert response.body() != null;
            JsonNode json = objectMapper.readTree(response.body().string());
            assertTrue(json.has("id"), "Resposta deve contar campo ID");
            assertTrue(json.has("status"), "Resposta deve contar campo Status");
        });
    }

    @Test
    @DisplayName(value = "GET no endpoint /tarefas/{id} deve retornar o item cadastrado no Database")
    public void testGetById() {
        Javalin app = setApp();
        String mockDescricao = "Executando tarefa 8 do AT";
        String mockTitulo = "Tarefa 8";
        Tarefa tarefa = new Tarefa(mockTitulo, mockDescricao);
        Database.data.add(tarefa);

        JavalinTest.test(app, (_, client) -> {
            var response = client.get("/tarefas/" + tarefa.getId());

            assertEquals(200, response.code(), "Código deve ser 200");
            assert response.body() != null;
            JsonNode json = objectMapper.readTree(response.body().string());
            assertEquals(json.get("data").get("id").asText(), tarefa.getId().toString(), "Deve retornar a tarefa com o mesmo Id buscado");
            assertEquals(json.get("data").get("descricao").asText(), tarefa.getDescricao(), "Deve retornar a tarefa com a mesma descrição cadastrada");
            assertEquals(json.get("data").get("titulo").asText(), tarefa.getTitulo(), "Deve retornar a tarefa com o mesmo título cadastrado");
        });
    }

    @Test
    @DisplayName(value = "GET no endpoint /tarefas/ deve retornar uma lista com todos os itens cadastrados")
    public void testGetAll() {
        Javalin app = setApp();
        String mockDescricao = "Executando tarefa 8 do AT";
        String mockTitulo = "Tarefa 8";
        Tarefa tarefa = new Tarefa(mockTitulo, mockDescricao);
        Database.data.add(tarefa);

        JavalinTest.test(app, (_, client) -> {
            var response = client.get("/tarefas/");

            assertEquals(200, response.code(), "Código deve ser 200");
            assert response.body() != null;
            JsonNode json = objectMapper.readTree(response.body().string());

            assertTrue(json.get("data").isArray(), "Data deve ser um array");
            assertFalse(json.get("data").isEmpty(), "Data deve conter pelo menos um item");

            assertEquals(json.get("data").get(0).get("id").asText(), tarefa.getId().toString(), "Deve retornar a tarefa com o mesmo Id buscado");
            assertEquals(json.get("data").get(0).get("descricao").asText(), tarefa.getDescricao(), "Deve retornar a tarefa com a mesma descrição cadastrada");
            assertEquals(json.get("data").get(0).get("titulo").asText(), tarefa.getTitulo(), "Deve retornar a tarefa com o mesmo título cadastrado");

        });
    }

}