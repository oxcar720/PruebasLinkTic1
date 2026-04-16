package com.oxcar720.inventory_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import java.util.*;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public boolean exists(UUID productId) {
        try {
            restTemplate.getForObject(
                    "http://localhost:8081/products/" + productId,
                    String.class
            );
            return true;

        } catch (HttpClientErrorException.NotFound e) {
            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error calling Product Service", e);
        }
    }
    public boolean fallback(UUID productId, Throwable t) {
        throw new RuntimeException("Product service unavailable");
    }
        @SuppressWarnings("unchecked")//jumm
        public List<Map<String, Object>> findAllProducts() {
        try {
            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            "http://localhost:8081/products?page=0&size=999",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Map<String, Object>>() {}
                    );

            return (List<Map<String, Object>>) response.getBody().get("content");

        } catch (Exception e) {
            throw new RuntimeException("Error loading products");
        }
    }
}