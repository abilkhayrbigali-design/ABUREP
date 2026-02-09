import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SimpleWebServer {


    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1907";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/gym_data_base";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/api/clients", new ClientHandler());

        server.setExecutor(null); // используем дефолтный экзекутор
        System.out.println("Server started on port 8000");
        System.out.println("Go to http://localhost:8000/api/clients");
        server.start();
    }

    static class ClientHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            if ("GET".equals(method)) {
                handleGet(exchange);
            } else if ("POST".equals(method)) {
                handlePost(exchange);
            } else {
                String response = "Method not allowed";
                exchange.sendResponseHeaders(405, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private void handleGet(HttpExchange exchange) throws IOException {
            StringBuilder jsonResponse = new StringBuilder();

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery("SELECT * FROM client")) {

                jsonResponse.append("[");

                boolean first = true;
                while (result.next()) {
                    if (!first) {
                        jsonResponse.append(",");
                    }
                    first = false;


                    jsonResponse.append("{")
                            .append("\"id\":").append(result.getInt("id")).append(",")
                            .append("\"name\":\"").append(result.getString("name")).append("\",")
                            .append("\"age\":").append(result.getInt("age")).append(",")
                            .append("\"gender\":\"").append(result.getString("gender")).append("\",")
                            .append("\"days_left\":").append(result.getInt("days_left")).append(",")
                            .append("\"email\":\"").append(result.getString("email")).append("\"")
                            .append("}");
                }


                jsonResponse.append("]");

            } catch (SQLException e) {
                e.printStackTrace();
                jsonResponse = new StringBuilder("{\"error\": \"" + e.getMessage() + "\"}");
            }


            byte[] responseBytes = jsonResponse.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }


        private void handlePost(HttpExchange exchange) throws IOException {

            InputStream requestBody = exchange.getRequestBody();
            String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);


            try {
                String name = parseJsonValue(body, "name");
                int age = Integer.parseInt(parseJsonValue(body, "age"));
                String gender = parseJsonValue(body, "gender");
                int daysLeft = Integer.parseInt(parseJsonValue(body, "days_left"));
                String email = parseJsonValue(body, "email");

                // Сохранение в БД
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    String sql = "INSERT INTO client (name, age, gender, days_left, email) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setString(1, name);
                        pstmt.setInt(2, age);
                        pstmt.setString(3, gender);
                        pstmt.setInt(4, daysLeft);
                        pstmt.setString(5, email);
                        pstmt.executeUpdate();
                    }
                }

                String response = "{\"status\": \"Client added successfully\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, response.length()); // 201 Created
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
                String error = "{\"error\": \"Invalid data format or DB error\"}";
                exchange.sendResponseHeaders(400, error.length());
                OutputStream os = exchange.getResponseBody();
                os.write(error.getBytes());
                os.close();
            }
        }


        private String parseJsonValue(String json, String key) {
            String searchKey = "\"" + key + "\":";
            int startIndex = json.indexOf(searchKey);
            if (startIndex == -1) return null;

            startIndex += searchKey.length();


            if (json.charAt(startIndex) == '"') {
                startIndex++; // пропускаем открывающую кавычку
                int endIndex = json.indexOf("\"", startIndex);
                return json.substring(startIndex, endIndex);
            } else {

                int endIndex = json.indexOf(",", startIndex);
                if (endIndex == -1) endIndex = json.indexOf("}", startIndex); // если это последнее поле
                return json.substring(startIndex, endIndex).trim();
            }
        }
    }
}
