package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.webservice.dto.NewOperationIntentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OperationIntent> createOperationIntent(@Valid @RequestBody NewOperationIntentDTO newOperationIntentDTO, @PathVariable Long userId) {
        OperationIntent operationIntent = operationIntentService.createOperationIntent(userId, newOperationIntentDTO);
        return ResponseEntity.ok(operationIntent);
    }


}
