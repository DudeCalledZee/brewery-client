package com.bms.breweryclient.client.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer socketTimeout;
    private final Integer maxTotalConnections;
    private final Integer maxConnectionsPerRoute;
    private final Integer connectionRequestTimeout;

    public BlockingRestTemplateCustomizer(@Value("${sfg.socketTimeout}") Integer socketTimeout,
                                          @Value("${sfg.maxTotalConnections}") Integer maxTotalConnections,
                                          @Value("${sfg.maxConnectionsPerRoute}") Integer maxConnectionsPerRoute,
                                          @Value("${sfg.connectionRequestTimeout}") Integer connectionRequestTimeout) {

        this.socketTimeout = socketTimeout;
        this.maxTotalConnections = maxTotalConnections;
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clietHttpRequestFactory());
    }

    private ClientHttpRequestFactory clietHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

}
