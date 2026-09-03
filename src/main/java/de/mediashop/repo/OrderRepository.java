package de.mediashop.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbc;

    public OrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Map<String, Object> findById(String id) {
        return jdbc.queryForMap(
                "SELECT id, customer_id, status, total_amount, created_at FROM orders WHERE id = ?", id);
    }

    public Map<String, Object> markPaid(String id, BigDecimal amount) {
        jdbc.update("UPDATE orders SET status = 'PAID', total_amount = ? WHERE id = ?", amount, id);
        return findById(id);
    }
}
