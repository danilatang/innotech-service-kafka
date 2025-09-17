package interview.innotechserviceconsumer.repositories;

import interview.innotechserviceconsumer.DTO.ClientResponseDto;
import interview.innotechserviceconsumer.mapper.ClientRowMapper;
import interview.innotechserviceconsumer.models.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(ClientEntity client) {
        String sql = """
           INSERT INTO clients_accounts_schema.clients (client_id, name, age, updated_at, department_id)
           VALUES (?, ?, ?, ?, ?)
           ON CONFLICT (client_id) DO UPDATE
           SET name = EXCLUDED.name,
               age = EXCLUDED.age,
               updated_at = EXCLUDED.updated_at,
               department_id = EXCLUDED.department_id
           """;

        jdbcTemplate.update(sql,
                client.getClientId(),
                client.getName(),
                client.getAge(),
                client.getUpdatedAt() != null ? Timestamp.from(client.getUpdatedAt()) : null,
                client.getDepartmentId()
        );
    }

    public List<ClientResponseDto> findAll(int page, int size, String clientName, Integer minAge, Integer maxAge) {
        StringBuilder sql = new StringBuilder("""
        SELECT c.client_id,
               c.name,
               c.age,
               c.updated_at,
               c.department_id,
               COALESCE(SUM(a.balance), 0) AS total_balance
        FROM clients_accounts_schema.clients c
        LEFT JOIN clients_accounts_schema.accounts a ON c.client_id = a.client_id
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (clientName != null) {
            sql.append("AND c.name ILIKE ? ");
            params.add("%" + clientName + "%");
        }
        if (minAge != null) {
            sql.append("AND c.age >= ? ");
            params.add(minAge);
        }
        if (maxAge != null) {
            sql.append("AND c.age <= ? ");
            params.add(maxAge);
        }

        sql.append(" GROUP BY c.client_id, c.name, c.age, c.updated_at, c.department_id ");
        sql.append(" ORDER BY c.name ");
        sql.append(" LIMIT ? OFFSET ? ");
        params.add(size);
        params.add(page * size);

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            ClientResponseDto dto = new ClientResponseDto();
            dto.setClientId(UUID.fromString(rs.getString("client_id")));
            dto.setName(rs.getString("name"));
            dto.setAge(rs.getInt("age"));
            dto.setTotalBalance(rs.getBigDecimal("total_balance"));
            dto.setPage(page);
            dto.setSize(size);
            return dto;
        });
    }

    public Optional<ClientEntity> findById(UUID clientId) {
        String sql = "SELECT * FROM clients_accounts_schema.clients WHERE client_id = ?";
        List<ClientEntity> clients = jdbcTemplate.query(sql, new Object[]{clientId}, new ClientRowMapper());
        return clients.isEmpty() ? Optional.empty() : Optional.of(clients.get(0));
    }
}
