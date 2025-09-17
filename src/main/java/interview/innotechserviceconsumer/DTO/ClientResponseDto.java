package interview.innotechserviceconsumer.DTO;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ClientResponseDto {
    private UUID clientId;
    private String name;
    private int age;
    private BigDecimal totalBalance;
    private int page;
    private int size;
}
