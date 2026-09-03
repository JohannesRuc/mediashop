package de.mediashop.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private static final Path INVOICE_DIR = Paths.get("/var/mediashop/invoices");

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws IOException {
        Path file = INVOICE_DIR.resolve(id + ".pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(file));
    }
}
