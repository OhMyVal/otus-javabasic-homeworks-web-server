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
        ExecutorService serv = Executors.newFixedThreadPool(4);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            this.dispatcher = new Dispatcher();
            System.out.println("Диспетчер проинициализирован");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    serv.execute(() -> {
                        try {
                            threadPoolTask(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        serv.shutdown();
    }

    private void threadPoolTask(Socket socket) throws IOException {
        byte[] buffer = new byte[8192];
        int n = socket.getInputStream().read(buffer);
        if (n > 0) {
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, socket.getOutputStream());
        }
        socket.close();
    }
}
