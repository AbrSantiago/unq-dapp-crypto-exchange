package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.service.TransactionService;
import ar.edu.unq.dapp_api.webservice.dto.transaction.NewTransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction services", description = "Manage transactions")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create a new transaction")
    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody NewTransactionDTO newTransactionDTO) {
        TransactionDTO transaction = TransactionDTO.fromModel(transactionService.createTransaction(newTransactionDTO.getUserId(), newTransactionDTO.getOperationIntentId()));
        return ResponseEntity.ok(transaction);
    }



}
