package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.example.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TarefasControllerTest {
    private static Javalin app;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void limpaDatabase() {
        Database.data.clear();
    }

    @BeforeAll
    public static void setApp() {
        app = Javalin.create(javalinConfig -> javalinConfig.http.defaultContentType = "application/json; charset=UTF-8");
        new TarefasController().registrarRotas(app);
    }

    @Test
    @DisplayName(value = "Post no endpoint /tarefas deve retornar status 201")
    public void testPostNovaTarefa() {

        JavalinTest.test(app, (_, client) -> {
            String body = """
                {
                  "descricao": "Executando tarefa 8 do AT",
                  "titulo": "Tarefa 8"
                }
            """;
            var response = client.post("/tarefas", body);

            assertEquals(201, response.code(), "Código deve ser 201");

            assert response.body() != null;
            JsonNode json = objectMapper.readTree(response.body().string());
            assertTrue(json.has("id"), "Resposta deve contar campo ID");
            assertTrue(json.has("status"), "Resposta deve contar campo Status");
        });
    }
}