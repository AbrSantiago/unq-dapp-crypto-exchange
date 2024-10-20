package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.OperationNotFoundException;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.repositories.OperationIntentRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import ar.edu.unq.dapp_api.service.integration.DollarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OperationIntentServiceImpl implements OperationIntentService {

    private final OperationIntentRepository operationIntentRepository;
    private final UserService userService;
    private final CryptoService cryptoService;
    private final DollarService dollarService;

    @Autowired
    public OperationIntentServiceImpl(OperationIntentRepository operationIntentRepository, UserService userService, CryptoService cryptoService, DollarService dollarService) {
        this.operationIntentRepository = operationIntentRepository;
        this.userService = userService;
        this.cryptoService = cryptoService;
        this.dollarService = dollarService;
    }

    @Override
    public OperationIntent createOperationIntent(Long userId, NewOperationIntentDTO newOperationIntentDTO) {
        CryptoSymbol symbol = newOperationIntentDTO.getSymbol();
        IntentionType type = newOperationIntentDTO.getType();
        BigDecimal cryptoAmount = newOperationIntentDTO.getCryptoAmount();
        BigDecimal cryptoPrice = cryptoService.getCryptoCurrencyValue(symbol.toString()).getPrice();

        BigDecimal dollarValue = type == IntentionType.BUY
                ? dollarService.getDollarBuyValue()
                : dollarService.getDollarSellValue();

        BigDecimal operationARSAmount = cryptoAmount
                .multiply(cryptoPrice)
                .multiply(dollarValue);
        User user = userService.getUserById(userId);

        OperationIntent operationIntent = new OperationIntent(symbol, cryptoAmount, cryptoPrice, operationARSAmount, user, type);
        operationIntentRepository.save(operationIntent);

        return operationIntent;
    }

    @Override
    public List<OperationIntent> getActivesOperationIntents() {
        return operationIntentRepository.findActivesOperationIntents(OperationStatus.OPEN);
    }

    @Override
    public OperationIntent getOperationIntentById(Long operationIntentId) {
        return operationIntentRepository.findById(operationIntentId).orElseThrow(() -> new OperationNotFoundException("Operation intent not found"));
    }

    @Override
    public List<OperationIntent> getActivesOperationIntentsFromUser(Long userId) {
        return operationIntentRepository.findActivesOperationIntentsFromUser(userId, OperationStatus.OPEN);
    }

}
