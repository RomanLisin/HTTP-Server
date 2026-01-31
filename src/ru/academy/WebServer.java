package ru.academy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

private  static  final int DEFAULT_BACKLOG = 0;

private final InetSocketAddress socketAddress;

    private HttpServer httpServer;

    public WebServer(InetSocketAddress socketAddress){
        this.socketAddress = socketAddress;
    }
    public WebServer(int port){

        this(new InetSocketAddress(port));
    }

    public void start() throws IOException {
        httpServer = HttpServer.create(socketAddress, DEFAULT_BACKLOG);
        httpServer.setExecutor(null); // если null в режиме одного потока

        httpServer.start();
    }

    private Executor getExecutor(){
        return Executors.newSingleThreadExecutor();
    }
}
