package de.mediashop.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class PaymentClient {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String fetchDetails(String detailsUrl) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(detailsUrl))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        try {
            return http.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            throw new IllegalStateException("Transaktionsdetails nicht abrufbar", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Abruf unterbrochen", e);
        }
    }
}
