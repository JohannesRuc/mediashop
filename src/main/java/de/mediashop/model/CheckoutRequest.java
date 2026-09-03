package de.mediashop.model;

import java.math.BigDecimal;
import java.util.List;

public record CheckoutRequest(BigDecimal amount, String currency, List<String> positions) {
}
