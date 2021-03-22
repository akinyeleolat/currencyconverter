package com.currencyconverter.web.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class UserData {
    private String baseCurrencyInput;

    private String expectedCurrencyInput;

    private Double amount;

    private Double expectedAmount;

    private Double rates;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historicalDate;

    public Date getHistoricalDate() {
        return historicalDate;
    }

    public void setHistoricalDate(Date historicalDate) {
        this.historicalDate=historicalDate;
    }

    public String getBaseCurrencyInput() {
        return baseCurrencyInput;
    }

    public void setBaseCurrencyInput(String baseCurrencyInput) {
        this.baseCurrencyInput=baseCurrencyInput;
    }

    public String getExpectedCurrencyInput() {
        return expectedCurrencyInput;
    }

    public void setExpectedCurrencyInput(String expectedCurrencyInput) {
        this.expectedCurrencyInput=expectedCurrencyInput;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount=amount;
    }


    public Double getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Double expectedAmount) {
        this.expectedAmount=expectedAmount;
    }

    public Double getRates() {
        return rates;
    }

    public void setRates(Double rates) {
        this.rates=rates;
    }

}
