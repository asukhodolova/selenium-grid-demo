package com.solvd.demo.custom;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.grid.config.Config;
import org.openqa.selenium.grid.data.*;
import org.openqa.selenium.grid.log.LoggingOptions;
import org.openqa.selenium.grid.node.HealthCheck;
import org.openqa.selenium.grid.node.Node;
import org.openqa.selenium.grid.node.local.LocalNodeFactory;
import org.openqa.selenium.grid.security.Secret;
import org.openqa.selenium.grid.security.SecretOptions;
import org.openqa.selenium.grid.server.BaseServerOptions;
import org.openqa.selenium.internal.Either;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.UUID;

public class CustomNode extends Node {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Node node;
    private int counter;

    protected CustomNode(Tracer tracer, URI uri, Secret registrationSecret) {
        super(tracer, new NodeId(UUID.randomUUID()), uri, registrationSecret);
    }

    public static Node create(Config config) {
        LoggingOptions loggingOptions = new LoggingOptions(config);
        BaseServerOptions serverOptions = new BaseServerOptions(config);
        URI uri = serverOptions.getExternalUri();
        SecretOptions secretOptions = new SecretOptions(config);

        Node node = LocalNodeFactory.create(config);

        CustomNode wrapper = new CustomNode(loggingOptions.getTracer(), uri, secretOptions.getRegistrationSecret());
        wrapper.node = node;
        wrapper.counter = 0;

        return wrapper;
    }

    @Override
    public synchronized Either<WebDriverException, CreateSessionResponse> newSession(CreateSessionRequest sessionRequest) {
        counter++;
        LOGGER.info("New session creation... Request number = " + counter);
        if (counter % 2 == 0) {
            LOGGER.warn("Request will be failed for capabilities: " + sessionRequest.getDesiredCapabilities().toString());
            throw new CustomNodeException("Even number request to the node has been received");
        }
        LOGGER.info("Session will be created for capabilities: " + sessionRequest.getDesiredCapabilities().toString());
        return node.newSession(sessionRequest);
    }

    @Override
    public HttpResponse executeWebDriverCommand(HttpRequest req) {
        return node.executeWebDriverCommand(req);
    }

    @Override
    public Session getSession(SessionId id) throws NoSuchSessionException {
        return node.getSession(id);
    }

    @Override
    public HttpResponse uploadFile(HttpRequest req, SessionId id) {
        return node.uploadFile(req, id);
    }

    @Override
    public void stop(SessionId id) throws NoSuchSessionException {
        node.stop(id);
    }

    @Override
    public boolean isSessionOwner(SessionId id) {
        return node.isSessionOwner(id);
    }

    @Override
    public boolean isSupporting(Capabilities capabilities) {
        return node.isSupporting(capabilities);
    }

    @Override
    public NodeStatus getStatus() {
        return node.getStatus();
    }

    @Override
    public HealthCheck getHealthCheck() {
        return node.getHealthCheck();
    }

    @Override
    public void drain() {
        node.drain();
    }

    @Override
    public boolean isReady() {
        return node.isReady();
    }
}
