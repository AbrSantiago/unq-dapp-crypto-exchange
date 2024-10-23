package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.*;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.service.TransactionService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.transaction.NewTransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.ProcessedTransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionActionDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Tag(name = "Transaction services", description = "Manage transactions")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Operation(summary = "Create a new transaction")
    @PostMapping("/create")
    public ResponseEntity<Object> createTransaction(@Valid @RequestBody NewTransactionDTO newTransactionDTO) {
        try {
            TransactionDTO transaction = TransactionDTO.fromModel(
                    transactionService.createTransaction(
                            newTransactionDTO.getUserId(),
                            newTransactionDTO.getOperationIntentId()
                    )
            );
            return ResponseEntity.ok(transaction);
        } catch (UserNotFoundException | OperationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OperationClosedException | OperationInProcessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating new transaction: " + e.getMessage());
        }
    }


    @Operation(summary = "Process an action in the transaction")
    @PostMapping("/process/{transactionId}")
    public ResponseEntity<Object> processTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody TransactionActionDTO transactionActionDTO) {
        try {
            User user = userService.getUserById(transactionActionDTO.getUserId());
            Transaction transaction = transactionService.processTransaction(
                    transactionId,
                    transactionActionDTO.getUserId(),
                    transactionActionDTO.getAction()
            );
            ProcessedTransactionDTO transactionDTO = ProcessedTransactionDTO.fromModel(transaction, user);
            return ResponseEntity.ok(transactionDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + e.getMessage());
        } catch (TransactionDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transaction not found: " + e.getMessage());
        } catch (UnauthorizedUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Unauthorized user: " + e.getMessage());
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid action: " + e.getMessage());
        } catch (NullActionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Action cannot be null: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing a transaction: " + e.getMessage());
        }
    }


    @Operation(summary = "Get volume of transactions within a date range")
    @GetMapping("/getVolume")
    public ResponseEntity<Integer> getVolume(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        int volume = transactionService.getConfirmedTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(volume);
    }
}