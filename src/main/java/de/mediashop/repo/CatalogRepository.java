package de.mediashop.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CatalogRepository {

    private final JdbcTemplate jdbc;

    public CatalogRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> search(String query) {
        String sql = "SELECT id, name, description, price FROM products"
                + " WHERE name LIKE '%" + query + "%' ORDER BY name";
        return jdbc.queryForList(sql);
    }

    public Map<String, Object> findById(String id) {
        return jdbc.queryForMap("SELECT id, name, description, price FROM products WHERE id = ?", id);
    }
}
