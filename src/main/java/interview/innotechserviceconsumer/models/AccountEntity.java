package interview.innotechserviceconsumer.models;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AccountEntity {
    private Long number;
    private UUID clientId;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private BigDecimal balance;
}
