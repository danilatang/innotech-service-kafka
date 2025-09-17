package interview.innotechserviceconsumer.kafka.consumer;

import interview.innotechserviceconsumer.DTO.ClientFromKafkaDto;
import interview.innotechserviceconsumer.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ClientConsumerEvent {

    private final ClientService clientService;

    @KafkaListener(
            topics = "client-accounts",
            groupId = "client-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consume(ClientFromKafkaDto clientDto) {
        if (clientDto == null) {
            System.err.println("Получено пустое сообщение, пропускаем");
            return;
        }

        try {
            System.out.println("Получили сообщение:");
            System.out.println("clientId: " + clientDto.getClientId());
            System.out.println("name: " + clientDto.getName());
            System.out.println("age: " + clientDto.getAge());
            System.out.println("updatedAt: " + clientDto.getUpdatedAt());
            System.out.println("departmentId: " + clientDto.getDepartmentId());
            System.out.println("accounts:");
            if (clientDto.getAccounts() != null) {
                clientDto.getAccounts().forEach(acc -> {
                    System.out.println("\tnumber: " + acc.getNumber());
                    System.out.println("\topeningDate: " + acc.getOpeningDate());
                    System.out.println("\tclosingDate: " + acc.getClosingDate());
                    System.out.println("\tbalance: " + (acc.getBalance() != null ? acc.getBalance() : BigDecimal.ZERO));
                });
            } else {
                System.out.println("\tНет аккаунтов");
            }

            clientService.saveClient(clientDto);

        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения из Kafka");
            System.err.println("Причина: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
