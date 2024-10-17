package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.repositories.OperationIntentRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.operationIntent.NewOperationIntentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationIntentServiceImpl implements OperationIntentService {

    private final OperationIntentRepository operationIntentRepository;
    private final UserService userService;
    private final CryptoService cryptoService;

    @Autowired
    public OperationIntentServiceImpl(OperationIntentRepository operationIntentRepository, UserService userService, CryptoService cryptoService) {
        this.operationIntentRepository = operationIntentRepository;
        this.userService = userService;
        this.cryptoService = cryptoService;
    }

    @Override
    public OperationIntent createOperationIntent(Long userId, NewOperationIntentDTO newOperationIntentDTO) {
        CryptoSymbol symbol = newOperationIntentDTO.getSymbol();
        Long cryptoAmount = newOperationIntentDTO.getCryptoAmount();
        Float cryptoPrice = cryptoService.getCryptoCurrencyValue(symbol.toString()).getPrice();
        Long operationARSAmount = ((long) 0);
        User user = userService.getUserById(userId);
        IntentionType type = newOperationIntentDTO.getType();

        OperationIntent operationIntent = new OperationIntent(symbol, cryptoAmount, cryptoPrice, operationARSAmount, user, type);
        operationIntentRepository.save(operationIntent);

        return operationIntent;
    }

    @Override
    public List<OperationIntent> getActivesOperationIntents() {
        return operationIntentRepository.findActivesOperationIntents(OperationStatus.OPEN);
    }
}
