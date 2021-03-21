package com.currencyconverter.web.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class UserData {
    private String baseCurrencyInput;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historicalDate;

    public Date getHistoricalDate() {
        return historicalDate;
    }

    public String getBaseCurrencyInput() {
        return baseCurrencyInput;
    }

    public void setHistoricalDate(Date historicalDate) {
        this.historicalDate=historicalDate;
    }

    public void setBaseCurrencyInput(String baseCurrencyInput) {
        this.baseCurrencyInput=baseCurrencyInput;
    }
}
