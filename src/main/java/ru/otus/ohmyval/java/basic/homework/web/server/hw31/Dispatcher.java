package ru.otus.ohmyval.java.basic.homework.web.server.hw31;

import ru.otus.ohmyval.java.basic.homework.web.server.hw31.processors.CalculatorRequestProcessor;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.processors.HelloWorldRequestProcessor;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.processors.RequestProcessor;
import ru.otus.ohmyval.java.basic.homework.web.server.hw31.processors.UnknownOperationRequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownOperationRequestProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("/calc", new CalculatorRequestProcessor());
        this.router.put("/hello", new HelloWorldRequestProcessor());
        this.unknownOperationRequestProcessor = new UnknownOperationRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        if (!router.containsKey(httpRequest.getUri())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream);
            return;
        }
        router.get(httpRequest.getUri()).execute(httpRequest, outputStream);
    }
}
