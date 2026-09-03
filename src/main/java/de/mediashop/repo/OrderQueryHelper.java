package de.mediashop.repo;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Bestelluebersicht des angemeldeten Kunden.
 *
 * ORDER BY laesst sich nicht als Prepared-Statement-Parameter binden. Die
 * Sortierspalte wird deshalb bereits am Controller per Bean Validation
 * (@Pattern) auf die erlaubten Spaltennamen eingeschraenkt.
 */
@Component
public class OrderQueryHelper {

    private static final String DEFAULT_COLUMN = "created_at";

    private final NamedParameterJdbcTemplate jdbc;

    public OrderQueryHelper(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findForCustomer(String customerId, String sortColumn) {
        String column = (sortColumn == null || sortColumn.isBlank()) ? DEFAULT_COLUMN : sortColumn;

        String sql = "SELECT id, status, total_amount, created_at FROM orders"
                + " WHERE customer_id = :customerId"
                + " ORDER BY " + column + " DESC";

        return jdbc.queryForList(sql, Map.of("customerId", customerId));
    }
}
