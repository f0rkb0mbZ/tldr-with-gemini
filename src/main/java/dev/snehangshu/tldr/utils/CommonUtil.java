package dev.snehangshu.tldr.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonUtil {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
