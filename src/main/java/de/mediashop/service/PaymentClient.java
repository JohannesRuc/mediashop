package de.mediashop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class PaymentClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchDetails(String detailsUrl) {
        return restTemplate.getForObject(URI.create(detailsUrl), String.class);
    }
}
