package org.garnishtest.steps.mockhttpserver.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;

public final class HttpMockServerManager {

    @NonNull private final WireMockServer server;

    public HttpMockServerManager(final int httpMockServerPort) {
        this.server = new WireMockServer(
                wireMockConfig()
                        .port(httpMockServerPort)
        );
    }

    public void startServer() {
        server.start();
    }

    public void stopServer() {
        server.shutdown();
    }

    public void register(@NonNull final StubMapping... mappings) {
        register(
                Arrays.asList(mappings)
        );
    }

    public void reset() {
        server.resetMappings();
        server.resetRequests();
        server.resetScenarios();
    }

    public void register(@NonNull final List<StubMapping> mappings) {
        for (final StubMapping mapping : mappings) {
            server.addStubMapping(mapping);
        }
    }

}
