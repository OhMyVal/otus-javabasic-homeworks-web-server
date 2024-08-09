package ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.processors;

import com.google.gson.Gson;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.HttpRequest;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Item;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllProductsProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        List<Item> items = Storage.getItems();
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + gson.toJson(items);
        output.write(result.getBytes(StandardCharsets.UTF_8));
    }
}
