package interview.innotechserviceconsumer.mapper;

import interview.innotechserviceconsumer.models.AccountEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountRowMapper implements RowMapper<AccountEntity> {
    @Override
    public AccountEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        AccountEntity account = new AccountEntity();
        account.setNumber(resultSet.getLong("number"));
        account.setClientId(UUID.fromString(resultSet.getString("client_id")));
        account.setOpeningDate(resultSet.getDate("opening_date").toLocalDate());
        account.setClosingDate(resultSet.getDate("closing_date") != null ? resultSet.getDate("closing_date").toLocalDate() : null);
        account.setBalance(resultSet.getBigDecimal("balance"));
        return account;
    }
}
