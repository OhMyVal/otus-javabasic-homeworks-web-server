package ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.processors;

import com.google.gson.Gson;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.HttpRequest;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Item;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.save(item);
        String jsonOutItem = gson.toJson(item);

        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + jsonOutItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}

