package interview.innotechserviceconsumer.controllers;

import interview.innotechserviceconsumer.DTO.AccountResponseDto;
import interview.innotechserviceconsumer.DTO.ClientResponseDto;
import interview.innotechserviceconsumer.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientResponseDto>> getClients(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        return ResponseEntity.ok(clientService.getClients(page, size, clientName, minAge, maxAge));
    }

    @GetMapping("/accounts/{clientId}")
    public ResponseEntity<List<AccountResponseDto>> getAccounts(
            @PathVariable UUID clientId,
            @RequestParam(required = false) BigDecimal minAmount
    ) {
        return ResponseEntity.ok(clientService.getAccounts(clientId, minAmount));
    }
}
