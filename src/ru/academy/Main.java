package ru.academy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
//        InetSocketAddress socketAddress = new InetSocketAddress(8080);
//        HttpServer httpServer = HttpServer.create(socketAddress, 0);
//
//        httpServer.createContext("/", exchange -> {
//            URI requestUri = exchange.getRequestURI();
//            System.out.println("Клиент запросил ресурс по адресу:" + requestUri);
//
//            Path htmlFilePath = Path.of("Index.html");
//            byte[] messageAsBytes = Files.readAllBytes(htmlFilePath);
//
//            exchange.getResponseHeaders()
//                    .set("Content-Type", "text/html ; charset=UTF-8");
//            exchange.sendResponseHeaders(200, messageAsBytes.length);
//
//
//            try (OutputStream outputStream = exchange.getResponseBody();){
//                outputStream.write(messageAsBytes);
//            }
//
//
//        });
        WebServer webServer = new WebServer(8080);
        try{
            webServer.start();
        }catch (IOException e){
            System.out.println("Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
