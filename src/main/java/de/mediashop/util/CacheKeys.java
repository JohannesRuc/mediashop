package de.mediashop.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Schluessel fuer den Katalog-Cache. Reine Verteilungsfunktion, kein Sicherheitszweck.
 */
public final class CacheKeys {

    private CacheKeys() {
    }

    public static String of(String... parts) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            for (String part : parts) {
                digest.update(part.getBytes(StandardCharsets.UTF_8));
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
