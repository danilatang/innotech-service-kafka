package interview.innotechserviceconsumer.mapper;

import interview.innotechserviceconsumer.models.ClientEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientRowMapper implements RowMapper<ClientEntity> {
    @Override
    public ClientEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ClientEntity client = new ClientEntity();
        client.setClientId(UUID.fromString(resultSet.getString("client_id")));
        client.setName(resultSet.getString("name"));
        client.setAge(resultSet.getInt("age"));
        client.setUpdatedAt(resultSet.getTimestamp("updated_at").toInstant());
        client.setDepartmentId(resultSet.getInt("department_id"));
        return client;
    }
}
