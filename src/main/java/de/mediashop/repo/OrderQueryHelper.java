package de.mediashop.repo;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bestelluebersicht des angemeldeten Kunden.
 *
 * ORDER BY laesst sich nicht als Prepared-Statement-Parameter binden, deshalb wird
 * die Sortierspalte gegen eine feste Allowlist geprueft und faellt sonst auf den
 * Default zurueck.
 */
@Component
public class OrderQueryHelper {

    private static final Set<String> SORTABLE_COLUMNS = Set.of("created_at", "total_amount", "status");
    private static final String DEFAULT_COLUMN = "created_at";

    private final NamedParameterJdbcTemplate jdbc;

    public OrderQueryHelper(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findForCustomer(String customerId, String requestedSort) {
        String column = allowedColumn(requestedSort);

        String sql = "SELECT id, status, total_amount, created_at FROM orders"
                + " WHERE customer_id = :customerId"
                + " ORDER BY " + column + " DESC";

        return jdbc.queryForList(sql, Map.of("customerId", customerId));
    }

    private String allowedColumn(String requested) {
        if (requested == null) {
            return DEFAULT_COLUMN;
        }
        String normalized = requested.trim().toLowerCase();
        return SORTABLE_COLUMNS.contains(normalized) ? normalized : DEFAULT_COLUMN;
    }
}
