package interview.innotechserviceconsumer.services.impl;

import interview.innotechserviceconsumer.DTO.AccountResponseDto;
import interview.innotechserviceconsumer.DTO.ClientFromKafkaDto;
import interview.innotechserviceconsumer.DTO.ClientResponseDto;
import interview.innotechserviceconsumer.mapper.ClientMapper;
import interview.innotechserviceconsumer.models.AccountEntity;
import interview.innotechserviceconsumer.models.ClientEntity;
import interview.innotechserviceconsumer.repositories.AccountRepository;
import interview.innotechserviceconsumer.repositories.ClientRepository;
import interview.innotechserviceconsumer.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponseDto> getClients(int page, int size, String clientName, Integer minAge, Integer maxAge) {
        return clientRepository.findAll(page, size, clientName, minAge, maxAge);
    }

    @Override
    public List<AccountResponseDto> getAccounts(UUID clientId, BigDecimal minAmount) {
        List<AccountEntity> accounts = accountRepository.findByClientId(clientId, minAmount);

        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));

        return accounts.stream()
                .map(account -> {
                    AccountResponseDto dto = new AccountResponseDto();
                    dto.setClientId(client.getClientId());
                    dto.setName(client.getName());
                    dto.setAge(client.getAge());
                    dto.setNumber(account.getNumber());
                    dto.setOpeningDate(account.getOpeningDate());
                    dto.setClosingDate(account.getClosingDate());
                    dto.setBalance(account.getBalance());
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public void saveClient(ClientFromKafkaDto clientFromKafkaDto) {
        ClientEntity client = clientMapper.toClientFromKafka(clientFromKafkaDto);
        List<AccountEntity> accounts = clientMapper.toAccountsFromKafka(clientFromKafkaDto);
        clientRepository.save(client);
        accountRepository.saveAll(accounts);
    }
}
