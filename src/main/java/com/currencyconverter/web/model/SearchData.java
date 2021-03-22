package com.currencyconverter.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "searches")
@Builder
public class SearchData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "base_currency")
    @NotEmpty(message = "*Please provide base currency")
    private String baseCurrencyInput;

    @Column(name = "expected_currency")
    @NotEmpty(message = "*Please provide expected currency")
    private String expectedCurrencyInput;

    @Column(name = "amount")
    @NotNull( message = "*Please provide amount")
    private Double amount;

    @Column(name = "expected_amount")
    private Double expectedAmount;

    @Column(name = "rates")
    private Double rates;

    @Column(name = "historical_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historicalDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt=createdAt;
    }
}
