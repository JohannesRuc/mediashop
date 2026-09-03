package de.mediashop.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Laedt die mitgelieferte Provider-Konfiguration (Endpunkte, Retry-Zeiten)
 * aus dem Classpath.
 */
@Component
public class ProviderConfigLoader {

    public Map<String, Object> load() {
        Yaml yaml = new Yaml();
        try (InputStream in = new ClassPathResource("payment-providers.yml").getInputStream()) {
            return yaml.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Provider-Konfiguration nicht lesbar", e);
        }
    }
}
