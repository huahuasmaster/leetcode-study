package com.zyz.study.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/2/6 22:55
 */
@Configuration
public class EsConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        restClientBuilder.setMaxRetryTimeoutMillis(6000);// 最大重试时长

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder.build());
        return restHighLevelClient;
    }
    
    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http")
        );
    }
}
