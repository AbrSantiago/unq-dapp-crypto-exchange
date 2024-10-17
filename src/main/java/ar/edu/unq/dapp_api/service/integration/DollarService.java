package ar.edu.unq.dapp_api.service.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DollarService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.dollar.api.url:NONE}")
    private String dollarApiURL;


    public Double getDollarSellValue() {
        Map<String, Object> response = restTemplate.getForObject(dollarApiURL, Map.class);

        return response != null ? ((Number) response.get("venta")).doubleValue() : null;
    }

    public Double getDollarBuyValue() {
        Map<String, Object> response = restTemplate.getForObject(dollarApiURL, Map.class);

        return response != null ? ((Number) response.get("compra")).doubleValue() : null;
    }
}

