package de.mediashop.web;

import de.mediashop.model.PaymentCallback;
import de.mediashop.repo.OrderRepository;
import de.mediashop.service.PaymentClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentCallbackController {

    private final PaymentClient paymentClient;
    private final OrderRepository orders;

    public PaymentCallbackController(PaymentClient paymentClient, OrderRepository orders) {
        this.paymentClient = paymentClient;
        this.orders = orders;
    }

    /**
     * Webhook des Payment-Providers. Der Provider schickt nur den Status und eine URL,
     * unter der die vollstaendigen Transaktionsdaten abgeholt werden koennen.
     */
    @PostMapping("/payments/callback")
    public Map<String, Object> callback(@RequestBody PaymentCallback callback) {
        String details = paymentClient.fetchDetails(callback.detailsUrl());

        if ("CONFIRMED".equals(callback.status())) {
            orders.markPaid(callback.orderId(), callback.amount());
        }
        return Map.of("orderId", callback.orderId(), "details", details);
    }

    /**
     * Support-Werkzeug: Transaktionsdetails erneut abholen, wenn der erste
     * Callback unvollstaendig war.
     */
    @PostMapping("/internal/payments/refetch")
    public Map<String, Object> refetch(@RequestParam("orderId") String orderId,
                                       @RequestParam("detailsUrl") String detailsUrl) {
        return Map.of("orderId", orderId, "details", paymentClient.fetchDetails(detailsUrl));
    }
}
