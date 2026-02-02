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

        StaticFileServer staticFileServer = new StaticFileServer(8080);
      staticFileServer.addStaticFilesDirectory(Path.of("static/images"));
      staticFileServer.addStaticFilesDirectory(Path.of("static/text"));

      staticFileServer.start();

    }
}
