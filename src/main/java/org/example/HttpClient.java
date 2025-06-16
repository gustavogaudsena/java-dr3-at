package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpClient {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            createTarefa();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String SERVER_URL = "http://localhost:7002" ;

    public static String get(String route) throws IOException, URISyntaxException {
        URL url = new URI(SERVER_URL + route).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        InputStreamReader streamReader;
        streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);

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
            out.writeBytes(data);
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

    public static void createTarefa() throws IOException, URISyntaxException {
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
    }

}
