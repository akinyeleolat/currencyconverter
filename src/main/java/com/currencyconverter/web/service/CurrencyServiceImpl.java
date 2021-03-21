package com.currencyconverter.web.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl {
    @Value("${exchange.api.api_url}")
    private String appUrl;

    @Value("${exchange.api.app_id}")
    private String appID;

    private String resourceUrl;

    private RestTemplate restTemplate;

    public CurrencyServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private String getResourceUrl(){
        return resourceUrl;
    }

    private void setResourceUrl(String endPoint){
        String resourceUrl = appUrl+endPoint+".json?"+"app_id="+appID;
        this.resourceUrl = resourceUrl;
    }


    public Map<String, String> currencyList() {
        String endPoint="currencies";
        this.setResourceUrl(endPoint);
        String resource = this.getResourceUrl();

        return restTemplate.getForObject(resource, Map.class);
    }


    public HashMap<String, HashMap<String, Double>> getHistoricalData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String searchDate = dateFormat.format(date);

        String endPoint="historical/"+searchDate;

        this.setResourceUrl(endPoint);
        String resource = this.getResourceUrl();

        return (HashMap<String, HashMap<String, Double>>) restTemplate.getForObject(resource, Map.class);
    }
}