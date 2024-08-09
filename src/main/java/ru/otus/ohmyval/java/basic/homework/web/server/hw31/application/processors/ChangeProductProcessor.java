package ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.processors;

import com.google.gson.Gson;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.HttpRequest;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Item;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChangeProductProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item changedItem = gson.fromJson(httpRequest.getBody(), Item.class);
        List<Item> items = Storage.getItems();
        for (Item item : items) {
            if (item.getId().equals(changedItem.getId())) {
                item.setTitle(changedItem.getTitle());
                item.setPrice(changedItem.getPrice());
                String jsonOutItem = gson.toJson(item);
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + jsonOutItem;
                output.write(response.getBytes(StandardCharsets.UTF_8));
                return;
            }
        }
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + "<html><body><h1>ITEM NOT FOUND!!!</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
