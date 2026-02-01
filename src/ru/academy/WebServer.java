package ru.academy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class WebServer {

private  static  final int DEFAULT_BACKLOG = 0;

private final InetSocketAddress socketAddress;

    private HttpServer httpServer;

   protected WebServer(InetSocketAddress socketAddress){
        this.socketAddress = Objects.requireNonNull(socketAddress, "Socket address must not be null");//socketAddress;
    }
    protected WebServer(int port){

        this(new InetSocketAddress(port));
    }

    protected void start() throws IOException {
//        httpServer = HttpServer.create(socketAddress, DEFAULT_BACKLOG);
//        httpServer.setExecutor(null); // если null в режиме одного потока
        if(this.httpServer != null){
            throw new IllegalStateException("Web server already started.");
        }
        this.httpServer = createHttpServer();
        this.httpServer.start();
    }

    protected  HttpServer createHttpServer() throws IOException {
        HttpServer httpServer = HttpServer.create(socketAddress, DEFAULT_BACKLOG);
        httpServer.setExecutor(getExecutor());

        configureHttpServer(httpServer);

        return httpServer;
    }


    protected Executor getExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    protected  abstract void configureHttpServer(HttpServer httpServer);
}
