package ar.edu.unq.dapp_api.model;

import java.time.LocalDateTime;

public record CryptoQuote(LocalDateTime time, String quote) {}
