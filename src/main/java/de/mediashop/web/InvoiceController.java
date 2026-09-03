package de.mediashop.web;

import de.mediashop.service.TokenGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private static final Path INVOICE_DIR = Paths.get("/var/mediashop/invoices");

    private final TokenGenerator tokens;

    public InvoiceController(TokenGenerator tokens) {
        this.tokens = tokens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws IOException {
        Path file = INVOICE_DIR.resolve(id + ".pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(file));
    }

    /**
     * Erzeugt einen teilbaren Download-Link fuer die Rechnung (z. B. fuer den
     * Steuerberater des Kunden).
     */
    @PostMapping("/{id}/share")
    public Map<String, String> share(@PathVariable String id) {
        String downloadToken = tokens.newDownloadToken();
        return Map.of(
                "orderNumber", tokens.newOrderNumber(),
                "url", "https://shop.example/invoices/" + id + "?token=" + downloadToken);
    }
}
