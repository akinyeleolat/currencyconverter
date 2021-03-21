package com.currencyconverter.web.controller;

import com.currencyconverter.web.model.User;
import com.currencyconverter.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.currencyconverter.web.service.CurrencyServiceImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/")
public class CurrencyController {


    private CurrencyServiceImpl service;


    private UserService userService;


    public CurrencyController(CurrencyServiceImpl service, UserService userService, ObjectMapper mapper) {
        this.service=service;
        this.userService= userService;
    }

    @GetMapping(value="/authuser/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        HashMap<String, HashMap<String, Double>> currencyHistoryData = service.getHistoricalData(new Date());

        String baseCurrency = String.valueOf(currencyHistoryData.get("base"));

        HashMap<String, Double> rates = currencyHistoryData.get("rates");

        Map<String, String> currencyList = service.currencyList();

        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("baseCurrency",baseCurrency);
        modelAndView.addObject("currencyList",currencyList);
        modelAndView.addObject("currencyRateList",rates);
        modelAndView.setViewName("authuser/index");
        return modelAndView;
    }
}
