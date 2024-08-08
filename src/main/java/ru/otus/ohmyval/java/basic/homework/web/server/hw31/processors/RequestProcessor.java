package ru.otus.ohmyval.java.basic.homework.web.server.hw31.processors;

import ru.otus.ohmyval.java.basic.homework.web.server.hw31.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream output) throws IOException;
}
