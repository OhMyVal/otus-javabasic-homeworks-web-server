package ru.otus.ohmyval.java.basic.homework.web.server.hw31;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            this.dispatcher = new Dispatcher();
            System.out.println("Диспетчер проинициализирован");
//            ExecutorService serv = Executors.newCachedThreadPool();
            while (true) {
//                serv.execute(()-> {
                new Thread(() -> {
                    try (Socket socket = serverSocket.accept()) {
                        byte[] buffer = new byte[8192];
                        int n = socket.getInputStream().read(buffer);
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);

                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
//            });
//                serv.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
