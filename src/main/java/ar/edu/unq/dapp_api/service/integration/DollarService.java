package ar.edu.unq.dapp_api.service.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class DollarService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.dollar.api.url:NONE}")
    private String dollarApiURL;


    public BigDecimal getDollarSellValue() {
        Map<String, Object> response = restTemplate.getForObject(dollarApiURL, Map.class);

        if (response != null && response.containsKey("venta")) {
            return BigDecimal.valueOf(((Number) response.get("venta")).doubleValue());
        }

        return null;
    }

    public BigDecimal getDollarBuyValue() {
        Map<String, Object> response = restTemplate.getForObject(dollarApiURL, Map.class);

        if (response != null && response.containsKey("compra")) {
            return BigDecimal.valueOf(((Number) response.get("compra")).doubleValue());
        }

        return null;
    }
}

