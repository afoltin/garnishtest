/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.garnishtest.modules.generic.httpclient;

import lombok.NonNull;
import org.springframework.beans.factory.FactoryBean;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class RestApiClientFactory implements FactoryBean<HttpClient> {

    private static final Charset REQUEST_ENCODING = StandardCharsets.UTF_8;

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 200;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = DEFAULT_MAX_TOTAL_CONNECTIONS;
    private static final Duration DEFAULT_CONNECTION_TIMEOUT_MILLIS =
        Duration.ofMillis(TimeUnit.SECONDS.toMillis(5));
    private static final Duration DEFAULT_SOCKET_TIMEOUT_MILLIS =
        Duration.ofMillis(TimeUnit.SECONDS.toMillis(5));

    private int maxTotalConnections = DEFAULT_MAX_TOTAL_CONNECTIONS;
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    private Duration connectionTimeoutMillis = DEFAULT_CONNECTION_TIMEOUT_MILLIS;
    private Duration socketTimeoutMillis = DEFAULT_SOCKET_TIMEOUT_MILLIS;


    public void setMaxTotalConnections(final int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public void setMaxConnectionsPerRoute(final int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    public void setConnectionTimeoutMillis(final int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = Duration.ofMillis(connectionTimeoutMillis);
    }

    public void setSocketTimeoutMillis(final int socketTimeoutMillis) {
        this.socketTimeoutMillis = Duration.ofMillis(socketTimeoutMillis);
    }

    @NonNull public HttpClient createClient() {
        System.setProperty("jdk.httpclient.connectionPoolSize",
            String.valueOf(this.maxTotalConnections));
        return HttpClient.newBuilder().connectTimeout(this.connectionTimeoutMillis)
            .proxy(ProxySelector.getDefault()).followRedirects(HttpClient.Redirect.NEVER).build();
    }

    @Override public boolean isSingleton() {
        return false;
    }

    @Override public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override public HttpClient getObject() {
        return createClient();
    }

}
