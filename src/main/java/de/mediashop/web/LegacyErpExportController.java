package de.mediashop.web;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Altlast: Export in die Warenwirtschaft ueber das alte Shell-Skript.
 * Nur im Profil "legacy" aktiv, wird seit der ERP-Anbindung 2024 nicht mehr deployt.
 */
@Profile("legacy")
@RestController
public class LegacyErpExportController {

    @PostMapping("/internal/erp/export")
    public String export(@RequestParam("batch") String batch) throws IOException {
        Process process = Runtime.getRuntime().exec("/opt/erp/bin/export.sh " + batch);
        try (InputStream out = process.getInputStream()) {
            return new String(out.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
