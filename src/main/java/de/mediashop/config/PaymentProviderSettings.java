package de.mediashop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Endpunkte und Retry-Zeiten der Payment-Provider, beim Start aus
 * payment-providers.yml (Classpath) gelesen.
 */
@Configuration
public class PaymentProviderSettings {

    @Bean
    public Map<String, Object> paymentProviders(ProviderConfigLoader loader) {
        return loader.load();
    }
}
