package com.mobiquityinc.mobit.modules.generic.httpclient;

import lombok.NonNull;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.springframework.beans.factory.FactoryBean;

import java.net.ProxySelector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public final class RestApiClientFactory implements FactoryBean<HttpClient> {

    private static final Charset REQUEST_ENCODING = StandardCharsets.UTF_8;

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS     = 200;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = DEFAULT_MAX_TOTAL_CONNECTIONS;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MILLIS = (int) TimeUnit.SECONDS.toMillis(5);
    private static final int DEFAULT_SOCKET_TIMEOUT_MILLIS     = (int) TimeUnit.SECONDS.toMillis(5);

    private int maxTotalConnections     = DEFAULT_MAX_TOTAL_CONNECTIONS;
    private int maxConnectionsPerRoute  = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    private int connectionTimeoutMillis = DEFAULT_CONNECTION_TIMEOUT_MILLIS;
    private int socketTimeoutMillis     = DEFAULT_SOCKET_TIMEOUT_MILLIS;


    public void setMaxTotalConnections(final int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public void setMaxConnectionsPerRoute(final int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    public void setConnectionTimeoutMillis(final int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    public void setSocketTimeoutMillis(final int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    @NonNull
    public HttpClient createClient() {
        final HttpClientConnectionManager connectionManager = createHttpClientConnectionManager(
                this.maxTotalConnections,
                this.maxConnectionsPerRoute
        );

        final ConnectionConfig connectionConfig = getConnectionConfig();
        final HttpRoutePlanner httpRoutePlanner = getHttpRoutePlanner();
        final RequestConfig requestConfig = getRequestConfig(this.connectionTimeoutMillis, this.socketTimeoutMillis);

        return HttpClients.custom()
                          .setConnectionManager(connectionManager)
                          .setDefaultConnectionConfig(connectionConfig)
                          .setRoutePlanner(httpRoutePlanner)
                          .setDefaultRequestConfig(requestConfig)
                          .disableRedirectHandling()
                          .build();
    }

    private static HttpClientConnectionManager createHttpClientConnectionManager(final int maxTotalConnections,
                                                                                 final int maxConnectionsPerRoute) {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

        return connectionManager;
    }

    private static ConnectionConfig getConnectionConfig() {
        return ConnectionConfig.custom()
                               .setCharset(REQUEST_ENCODING)
                               .build();
    }

    private static HttpRoutePlanner getHttpRoutePlanner() {
        return new SystemDefaultRoutePlanner(
                ProxySelector.getDefault()
        );
    }

    private static RequestConfig getRequestConfig(final int connectionTimeoutMillis,
                                                  final int socketTimeoutMillis) {
        return RequestConfig.custom()
                            .setConnectTimeout(connectionTimeoutMillis)
                            .setSocketTimeout(socketTimeoutMillis)
                            .build();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override
    public HttpClient getObject() {
        return createClient();
    }

}
