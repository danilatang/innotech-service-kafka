package interview.innotechserviceconsumer.repositories;

import interview.innotechserviceconsumer.mapper.AccountRowMapper;
import interview.innotechserviceconsumer.models.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<AccountEntity> accounts) {
        String sql = """
        INSERT INTO clients_accounts_schema.accounts (number, client_id, opening_date, closing_date, balance)
        VALUES (?, ?, ?, ?, ?)
        ON CONFLICT (number) DO UPDATE
        SET opening_date = EXCLUDED.opening_date,
            closing_date = EXCLUDED.closing_date,
            balance = EXCLUDED.balance
        """;

        for (AccountEntity account : accounts) {
            jdbcTemplate.update(sql,
                    account.getNumber(),
                    account.getClientId(),
                    account.getOpeningDate() != null ? Date.valueOf(account.getOpeningDate()) : null,
                    account.getClosingDate() != null ? Date.valueOf(account.getClosingDate()) : null,
                    account.getBalance()
            );
        }
    }

    public List<AccountEntity> findByClientId(UUID clientId, BigDecimal minAmount) {
        String sql = "SELECT * FROM clients_accounts_schema.accounts WHERE client_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(clientId);

        if (minAmount != null) {
            sql += " AND balance >= ?";
            params.add(minAmount);
        }

        return jdbcTemplate.query(sql, params.toArray(), new AccountRowMapper());
    }
}
