package de.mediashop.web;

import de.mediashop.model.CheckoutRequest;
import de.mediashop.repo.OrderQueryHelper;
import de.mediashop.repo.OrderRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orders;
    private final OrderQueryHelper queryHelper;

    public OrderController(OrderRepository orders, OrderQueryHelper queryHelper) {
        this.orders = orders;
        this.queryHelper = queryHelper;
    }

    @GetMapping("/{id}")
    public Map<String, Object> byId(@PathVariable String id) {
        return orders.findById(id);
    }

    @GetMapping
    public List<Map<String, Object>> list(@AuthenticationPrincipal Jwt jwt,
                                          @RequestParam(name = "sort", required = false) String sort) {
        return queryHelper.findForCustomer(jwt.getSubject(), sort);
    }

    @PostMapping("/{id}/checkout")
    public Map<String, Object> checkout(@PathVariable String id, @RequestBody CheckoutRequest request) {
        return orders.markPaid(id, request.amount());
    }
}
