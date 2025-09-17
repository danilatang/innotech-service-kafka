package interview.innotechserviceconsumer.mapper;

import interview.innotechserviceconsumer.DTO.ClientFromKafkaDto;
import interview.innotechserviceconsumer.models.AccountEntity;
import interview.innotechserviceconsumer.models.ClientEntity;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
public class ClientMapper {

    public ClientEntity toClientFromKafka(ClientFromKafkaDto dto) {
        if (dto == null) return null;

        ClientEntity client = new ClientEntity();
        client.setClientId(dto.getClientId());
        client.setName(dto.getName());
        client.setAge(dto.getAge());
        client.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : Instant.now());
        client.setDepartmentId(dto.getDepartmentId());
        return client;
    }

    public List<AccountEntity> toAccountsFromKafka(ClientFromKafkaDto dto) {
        if (dto.getAccounts() == null) return List.of();

        return dto.getAccounts().stream()
                .filter(accDto -> accDto.getNumber() != null)
                .map(accDto -> {
                    AccountEntity acc = new AccountEntity();
                    acc.setNumber(accDto.getNumber());
                    acc.setClientId(dto.getClientId());
                    acc.setOpeningDate(accDto.getOpeningDate());
                    acc.setClosingDate(accDto.getClosingDate());
                    acc.setBalance(accDto.getBalance() != null ? accDto.getBalance() : BigDecimal.ZERO);
                    return acc;
                })
                .toList();
    }
}
