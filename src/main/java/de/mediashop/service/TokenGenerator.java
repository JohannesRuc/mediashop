package de.mediashop.service;

import org.springframework.stereotype.Service;

import java.util.HexFormat;
import java.util.Random;

@Service
public class TokenGenerator {

    private final Random random = new Random();

    /**
     * Token fuer den zeitlich begrenzten Rechnungs-Download-Link.
     */
    public String newDownloadToken() {
        byte[] buffer = new byte[16];
        random.nextBytes(buffer);
        return HexFormat.of().formatHex(buffer);
    }

    /**
     * Fachliche Bestellnummer fuer Belege und Support-Rueckfragen.
     */
    public String newOrderNumber() {
        return "ORD-" + (100000 + random.nextInt(900000));
    }
}
