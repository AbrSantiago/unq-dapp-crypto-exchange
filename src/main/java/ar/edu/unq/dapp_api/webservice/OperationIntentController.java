package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.webservice.dto.operationIntent.NewOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.operationIntent.OperationIntentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OperationIntentDTO> createOperationIntent(@Valid @RequestBody NewOperationIntentDTO newOperationIntentDTO, @PathVariable Long userId) {
        OperationIntentDTO operationIntent = OperationIntentDTO.fromModel(operationIntentService.createOperationIntent(userId, newOperationIntentDTO));
        return ResponseEntity.ok(operationIntent);
    }

    @Operation(summary = "Get all the actives operation intents")
    @GetMapping("/actives")
    public ResponseEntity<List<OperationIntentDTO>> getActivesOperationIntents() {
        List<OperationIntentDTO> operationIntents = OperationIntentDTO.fromModelList(operationIntentService.getActivesOperationIntents());
        return ResponseEntity.ok(operationIntents);
    }


}
