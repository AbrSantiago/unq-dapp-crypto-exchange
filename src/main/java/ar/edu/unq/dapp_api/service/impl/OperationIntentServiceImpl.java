package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.OperationNotFoundException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.repositories.OperationIntentRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.validation.OperationIntentValidator;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OperationIntentServiceImpl implements OperationIntentService {
    private final OperationIntentRepository operationIntentRepository;
    private final UserService userService;
    private final CryptoService cryptoService;
    private final OperationIntentValidator operationIntentValidator;

    @Autowired
    public OperationIntentServiceImpl(OperationIntentRepository operationIntentRepository, UserService userService, CryptoService cryptoService, OperationIntentValidator operationIntentValidator) {
        this.operationIntentRepository = operationIntentRepository;
        this.userService = userService;
        this.cryptoService = cryptoService;
        this.operationIntentValidator = operationIntentValidator;
    }

    @Override
    public OperationIntent createOperationIntent(Long userId, NewOperationIntentDTO newOperationIntentDTO) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        BigDecimal cryptoPrice = cryptoService.getCryptoCurrencyValue(newOperationIntentDTO.getSymbol().toString()).getPrice();

        operationIntentValidator.validate(newOperationIntentDTO, cryptoPrice);

        BigDecimal operationARSAmount = newOperationIntentDTO.getOperationARSAmount();
        OperationIntent operationIntent = new OperationIntent(
                newOperationIntentDTO.getSymbol(),
                newOperationIntentDTO.getCryptoAmount(),
                cryptoPrice,
                operationARSAmount,
                user,
                newOperationIntentDTO.getType()
        );
        operationIntentRepository.save(operationIntent);

        return operationIntent;
    }

    @Override
    public List<OperationIntent> getActivesOperationIntents() {
        return operationIntentRepository.findActivesOperationIntents(OperationStatus.OPEN);
    }

    @Override
    public OperationIntent getOperationIntentById(Long operationIntentId) {
        return operationIntentRepository.findById(operationIntentId).orElseThrow(OperationNotFoundException::new);
    }

    @Override
    public List<OperationIntent> getActivesOperationIntentsFromUser(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new UserNotFoundException();
        }
        return operationIntentRepository.findActivesOperationIntentsFromUser(userId, OperationStatus.OPEN);
    }
}