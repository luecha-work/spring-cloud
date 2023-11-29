package com.example.microservice.limitsservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.limitsservice.Configuration;
import com.example.microservice.limitsservice.models.LimitConfiguration;

@RestController
public class LimitConfigurationController {
    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitFromConfiguration() {
        return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
