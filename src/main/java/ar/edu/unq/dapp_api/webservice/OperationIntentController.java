package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ExpressedOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ActiveOperationIntentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Operation Intent services", description = "Manage operation intents")
@RestController
@RequestMapping("/operation-intent")
public class OperationIntentController {
    private final OperationIntentService operationIntentService;

    @Autowired
    public OperationIntentController(OperationIntentService operationIntentService) {
        this.operationIntentService = operationIntentService;
    }

    @Operation(summary = "Create a new operation intent")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ExpressedOperationIntentDTO> createOperationIntent(
            @Valid @RequestBody NewOperationIntentDTO newOperationIntentDTO,
            @PathVariable Long userId) {
        ExpressedOperationIntentDTO expressedOperationIntentDTO = ExpressedOperationIntentDTO
                .fromModel(operationIntentService.createOperationIntent(userId, newOperationIntentDTO));
        return ResponseEntity.ok(expressedOperationIntentDTO);
    }

    @Operation(summary = "Get all active operation intents for a user")
    @GetMapping("/actives/{userId}")
    public ResponseEntity<Object> getActivesOperationIntentsFromUser(@PathVariable Long userId) {
        try {
            List<OperationIntent> activeUserOperationIntents = operationIntentService.getActivesOperationIntentsFromUser(userId);
            List<ActiveOperationIntentDTO> activeUserOperationIntentsDTOs = new ArrayList<>();

            activeUserOperationIntents.forEach(
                    operationIntent -> activeUserOperationIntentsDTOs.add(ActiveOperationIntentDTO.fromModel(operationIntent))
            );
            return ResponseEntity.ok(activeUserOperationIntentsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting active operation intents for a user: " + e.getMessage());
        }
    }
}