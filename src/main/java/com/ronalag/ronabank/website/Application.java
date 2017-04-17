package com.ronalag.ronabank.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.WebServiceTemplate;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.ronalag.ronabank"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @LoadBalanced
    WebServiceTemplate webServiceTemplate() {
    	return new WebServiceTemplate();
    }
}
