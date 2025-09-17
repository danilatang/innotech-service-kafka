package interview.innotechserviceconsumer.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientFromKafkaDto {
    private UUID clientId;

    @JsonProperty("name")
    private String name;

    private int age;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant updatedAt;

    private int departmentId;

    private List<AccountFromKafkaDto> accounts;
}
