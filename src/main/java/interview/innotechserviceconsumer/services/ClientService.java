package interview.innotechserviceconsumer.services;

import interview.innotechserviceconsumer.DTO.AccountResponseDto;
import interview.innotechserviceconsumer.DTO.ClientFromKafkaDto;
import interview.innotechserviceconsumer.DTO.ClientResponseDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    public List<ClientResponseDto> getClients(int page, int size, String clientName, Integer minAge, Integer maxAge);

    public List<AccountResponseDto> getAccounts(UUID clientId, BigDecimal minAmount);

    public void saveClient(ClientFromKafkaDto clientFromKafkaDto);
}