package model;

import java.util.Map;

public class ExchangeRates {
    private final Map<String, Double> rates;

    public ExchangeRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}

