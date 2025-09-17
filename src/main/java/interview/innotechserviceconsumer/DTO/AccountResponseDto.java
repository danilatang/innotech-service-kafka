package interview.innotechserviceconsumer.DTO;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AccountResponseDto {
    private Long number;
    private UUID clientId;
    private String name;
    private int age;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private BigDecimal balance;
}
