package ru.academy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executor;

public class StaticFileServer extends WebServer{


    private final Set<Path> staticFileDirectories = new HashSet<>();

    public StaticFileServer(InetSocketAddress socketAddress){
        super(socketAddress);
    }

    public StaticFileServer(int port){
        super(port);
    }

    public void addStaticFilesDirectory(Path directoryPath){
//        if(directoryPath == null) {
//            throw new NullPointerException();
//        }
        Objects.requireNonNull(directoryPath, "Directory path must not be null");

        if(!Files.isDirectory(directoryPath)){
            throw new IllegalArgumentException("Specified illegal static directory path");
        }
        staticFileDirectories.add(directoryPath);
    }
    @Override
    protected void configureHttpServer(HttpServer httpServer) {
        httpServer.createContext("/", this::handleRequest ); // вместо лямбда выражений указываем ссылку на handleRequest

//        System.out.println(getFileExtension(Path.of("static/files/.test/string.png")));
//        System.out.println(getFileExtension(Path.of("static/files/test/file.txt")));
    }

    //обработка запросов
    private void handleRequest(HttpExchange exchange) throws IOException {
        URI requestUri = exchange.getRequestURI();
        System.out.println("Клиент запросил ресурс по адресу: " + requestUri);

        String relativeFilePath = requestUri.getPath()
                .replaceAll("^/+", "");

        Path staticFilePath = getStaticFilePath(relativeFilePath);

        if (staticFilePath == null){
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }

            String contentType = getContentType(staticFilePath);

        if(contentType == null) {
            exchange.sendResponseHeaders(403, 0);
            exchange.close();

            return;
        }
        byte[] fileContent = Files.readAllBytes(staticFilePath); // считываем файл

        exchange.getResponseHeaders().set("Content-Type", contentType);

        exchange.sendResponseHeaders(200, fileContent.length);

        try(OutputStream outputStream = exchange.getResponseBody()){
            outputStream.write(fileContent);
        }
    }

    private  Path getStaticFilePath(String relativePath){
        for(Path staticFileDirectory :staticFileDirectories){
            Path staticFilePath = staticFileDirectory.resolve(relativePath);

            if(Files.isRegularFile(staticFilePath))
                return staticFilePath;
        }
        return null;
    }

    private String getContentType(Path filePath){
    String fileExtensions = getFileExtension(filePath);

    if(fileExtensions == null)
        return null;

    return switch (fileExtensions){
        case "txt" -> "text/plain";
        case "html" -> "text/html; charset=UTF-8";
        case "css" -> "text/css";
        case "js" -> "text/javascript";

        case "png" -> "image/png";
        case "jpg", "jpeg" -> "image/jpg";

        case "mp4" -> "video/mp4";

        default -> "application/octet-stream";
    };
    }

    private String getFileExtension(Path filePath){
        String path = filePath.toString();
        int extensionStartsAt = path.lastIndexOf('.');
        if(extensionStartsAt == -1)
            return null;

        return path.substring(extensionStartsAt + 1).toLowerCase(Locale.ROOT);
    }
}
