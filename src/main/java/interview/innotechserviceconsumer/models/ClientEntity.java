package interview.innotechserviceconsumer.models;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ClientEntity {
    private UUID clientId;
    private String name;
    private int age;
    private Instant updatedAt;
    private int departmentId;
}
