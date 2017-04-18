package com.ronalag.ronabank.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * The entry point for the application.
 * 
 * @author Henry Aghaulor
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.ronalag.ronabank"})
public class Application {
	
	/**
	 * Entry point for the application.
	 * 
	 * @param args Arguments for the application.
	 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * The web service template. Used to communicate with the financial
     * calculator SOAP web service.
     * 
     * @return The web service template.
     */
    @Bean    
    WebServiceTemplate webServiceTemplate() {
    	return new WebServiceTemplate();
    }
}
