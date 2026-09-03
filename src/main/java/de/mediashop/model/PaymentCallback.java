package de.mediashop.model;

import java.math.BigDecimal;

public record PaymentCallback(String orderId, String status, BigDecimal amount, String detailsUrl) {
}
