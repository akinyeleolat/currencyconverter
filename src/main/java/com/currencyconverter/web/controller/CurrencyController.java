package com.currencyconverter.web.controller;

import com.currencyconverter.web.dto.MessageDTO;
import com.currencyconverter.web.model.SearchData;
import com.currencyconverter.web.model.User;
import com.currencyconverter.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.tools.internal.ws.processor.model.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import com.currencyconverter.web.service.CurrencyServiceImpl;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ExceptionHandler;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Controller
@RequestMapping("/")
public class CurrencyController {


    private CurrencyServiceImpl service;


    private UserService userService;

    private static DecimalFormat formatter = new DecimalFormat("#.####");

    private ObjectMapper mapper;


    public CurrencyController(CurrencyServiceImpl service, UserService userService, ObjectMapper mapper) {
        this.service=service;
        this.userService= userService;
        this.mapper = mapper;
    }

    @GetMapping(value="/authuser/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        HashMap<String, HashMap<String, Double>> currencyHistoryData = service.getHistoricalData(new Date());

        String baseCurrency = String.valueOf(currencyHistoryData.get("base"));

        HashMap<String, Double> rates = currencyHistoryData.get("rates");

        Date timeStamp = new Date();

        Map<String, String> currencyList = service.currencyList();


        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("baseCurrency",baseCurrency);
        modelAndView.addObject("valueDate",timeStamp);
        modelAndView.addObject("currencyList",currencyList);
        modelAndView.addObject("currencyRateList",rates);
        modelAndView.addObject("userData", new SearchData());
        modelAndView.addObject("recentSearches", service.getAllRecentSearches());
        modelAndView.setViewName("authuser/index");
        return modelAndView;
    }

    @PostMapping(value="/authuser/home")
    public ModelAndView processHistoricalData(SearchData searchDataInput, BindingResult bindingResult) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        Date historicalDate;

        historicalDate = searchDataInput.getHistoricalDate() == null? new Date(): searchDataInput.getHistoricalDate();


        HashMap<String, HashMap<String, Double>> currencyHistoryData = service.getHistoricalData(historicalDate);

        String baseCurrency = String.valueOf(currencyHistoryData.get("base"));


        HashMap<String, Double> rates = currencyHistoryData.get("rates");

        Map<String, String> currencyList = service.currencyList();



        if(searchDataInput.getAmount() != null){
            String baseCurrencyInput = searchDataInput.getBaseCurrencyInput();

            String expectedCurrencyInput = searchDataInput.getExpectedCurrencyInput();
            Double amount = searchDataInput.getAmount();
            SearchData conversionResult = service.currencyConverter(baseCurrencyInput,expectedCurrencyInput, amount);

            Map<String, String> results = new HashMap<>();
            results.put("converted",formatter.format(conversionResult.getExpectedAmount())+" "+conversionResult.getExpectedCurrencyInput());
            results.put("amount", formatter.format(conversionResult.getAmount())+ " "+conversionResult.getBaseCurrencyInput());
            results.put("rates", formatter.format(conversionResult.getRates())+" "+conversionResult.getExpectedCurrencyInput()+"_"+conversionResult.getBaseCurrencyInput());

            modelAndView.addObject("conversionResults", results);
        }


        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("baseCurrency",baseCurrency);
        modelAndView.addObject("valueDate",historicalDate);
        modelAndView.addObject("currencyList",currencyList);
        modelAndView.addObject("currencyRateList",rates);
        modelAndView.addObject("userData", new SearchData());
        modelAndView.addObject("recentSearches", service.getAllRecentSearches());

        modelAndView.setViewName("authuser/index");
        return modelAndView;
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleClientError(HttpClientErrorException ex, Model model) throws IOException {
        MessageDTO dto = mapper.readValue(ex.getResponseBodyAsByteArray(), MessageDTO.class);
        model.addAttribute("error", (dto.getDescription() == null? dto.getMessage(): dto.getDescription()));
        return home();
    }
}
