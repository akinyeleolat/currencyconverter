package com.currencyconverter.web.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.currencyconverter.web.model.SearchData;
import com.currencyconverter.web.repository.SearchDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl {
    @Value("${exchange.api.api_url}")
    private String appUrl;

    @Value("${exchange.api.app_id}")
    private String appID;

    @Value("${currency.converter.api_url}")
    private String currencyConverterApiUrl;

    @Value("${currency.converter.api_key}")
    private String currencyConverterApiKey;

    private String resourceUrl;

    private String currencyConverterResource;

    private RestTemplate restTemplate;

    private SearchDataRepository searchDataRepository;

    public CurrencyServiceImpl(RestTemplateBuilder restTemplateBuilder, SearchDataRepository searchDataRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.searchDataRepository = searchDataRepository;
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

    public SearchData currencyConverter (String baseCurrency, String expectedCurrency, Double amount){
        currencyConverterResource = currencyConverterApiUrl+baseCurrency+"_"+expectedCurrency+"&compact=ultra&apiKey="+currencyConverterApiKey;

        this.resourceUrl = currencyConverterResource;

        Map result =  restTemplate.getForObject(resourceUrl,Map.class);

        assert result != null;
        Double rate = Double.parseDouble(String.valueOf(result.values().toArray()[0]));

        SearchData newData = new SearchData();

        newData.setAmount(amount);
        newData.setRates(rate);
        newData.setBaseCurrencyInput(baseCurrency);
        newData.setExpectedCurrencyInput(expectedCurrency);

        Double expectedAmount = rate * amount;

        newData.setExpectedAmount(expectedAmount);

        return saveSearchData(newData);
    }

    public SearchData saveSearchData(SearchData searchData){
        return searchDataRepository.save(searchData);
    }

    public Page<SearchData> getAllRecentSearches(){
        return searchDataRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    //TODO:  convert timestamp, cache resource, implement jmx
}
