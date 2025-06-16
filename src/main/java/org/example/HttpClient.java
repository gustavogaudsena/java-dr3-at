package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private static final String SERVER_URL = "http://localhost:7001";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            String newTarefa = createTarefa();
            JsonNode tarefaJson = objectMapper.readTree(newTarefa);
            String id = tarefaJson.get("id").asText();
            getTarefaById(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String get(String route) throws IOException, URISyntaxException {
        URL url = new URI(SERVER_URL + route).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        InputStreamReader streamReader;
        streamReader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(streamReader);
        int status = conn.getResponseCode();
        System.out.println("Status: " + status);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String post(String route, String data) throws IOException, URISyntaxException {
        URL url = new URI(SERVER_URL + route).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);


        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            out.write(data.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }

        InputStreamReader streamReader;
        streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);
        int status = conn.getResponseCode();

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String createTarefa() throws IOException, URISyntaxException {
        String mockTitulo = "Nova tarefa da etapa 3 ";
        String mockDescricao = "Criando uma tarefa pelo método Post";
        String data = String.format("""
                    {
                      "descricao": "%s",
                      "titulo": "%s"
                    }
                """, mockDescricao, mockTitulo);
        String response = post("/tarefas", data);

        System.out.println(response);

        return response;
    }

    public static void getTarefas() throws IOException, URISyntaxException {
        String response = get("/tarefas");

        System.out.println(response);
    }

    public static void getTarefaById(String id) throws IOException, URISyntaxException {
        String response = get("/tarefas/" + id);

        System.out.println(response);
    }
}
