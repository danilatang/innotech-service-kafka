package interview.innotechserviceconsumer.configuration;

import interview.innotechserviceconsumer.DTO.ClientFromKafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, ClientFromKafkaDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "client-consumer-group");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "interview.innotechserviceconsumer.dto"); // твой пакет
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(ClientFromKafkaDto.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientFromKafkaDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClientFromKafkaDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        DefaultErrorHandler errorHandler = new DefaultErrorHandler((record, exception) -> {
            System.err.println("Ошибка при обработке сообщения из Kafka");
            System.err.println("Топик: " + record.topic());
            System.err.println("Смещение: " + record.offset());
            System.err.println("Ключ: " + record.key());
            System.err.println("Значение: " + record.value());
            System.err.println("Причина: " + exception.getMessage());
        });

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}

