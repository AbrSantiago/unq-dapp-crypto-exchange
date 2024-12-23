package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ExpressedOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ActiveOperationIntentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @ApiResponse(responseCode = "200", description = "Operation created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpressedOperationIntentDTO.class)))
    public ResponseEntity<Object> createOperationIntent(
            @Valid @RequestBody NewOperationIntentDTO newOperationIntentDTO,
            @PathVariable Long userId) {
        try {
            ExpressedOperationIntentDTO expressedOperationIntentDTO = ExpressedOperationIntentDTO
                    .fromModel(operationIntentService.createOperationIntent(userId, newOperationIntentDTO));
            return ResponseEntity.ok(expressedOperationIntentDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error creating new operation intent: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating new operation intent: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all active operation intents for a user")
    @GetMapping("/actives/{userId}")
    @ApiResponse(responseCode = "200", description = "Active operation intents retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActiveOperationIntentDTO[].class)))
    public ResponseEntity<Object> getActiveOperationIntentsFromUser(@PathVariable Long userId) {
        try {
            List<OperationIntent> activeUserOperationIntents = operationIntentService.getActivesOperationIntentsFromUser(userId);
            List<ActiveOperationIntentDTO> activeUserOperationIntentsDTOs = new ArrayList<>();

            activeUserOperationIntents.forEach(
                    operationIntent -> activeUserOperationIntentsDTOs.add(ActiveOperationIntentDTO.fromModel(operationIntent))
            );
            return ResponseEntity.ok(activeUserOperationIntentsDTOs);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error getting active operation intents for a user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting active operation intents for a user: " + e.getMessage());
        }
    }
}