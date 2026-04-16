package com.oxcar720.inventory_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import java.util.*;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String baseUrl;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean exists(UUID productId) {
        try {
            restTemplate.getForObject(
                    baseUrl + "/products/" + productId,
                    String.class
            );
            return true;

        } catch (HttpClientErrorException.NotFound e) {
            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error calling Product Service", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllProducts() {
        try {
            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            baseUrl + "/products?page=0&size=999",
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