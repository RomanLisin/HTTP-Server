package ru.academy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
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
        StaticFileServer staticFileServer = new StaticFileServer(8080);
      staticFileServer.addStaticFilesDirectory(Path.of("static/images"));
      staticFileServer.addStaticFilesDirectory(Path.of("static/text"));

      staticFileServer.start();

    }
}
