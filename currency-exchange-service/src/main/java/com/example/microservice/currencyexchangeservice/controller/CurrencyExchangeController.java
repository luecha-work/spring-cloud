package com.example.microservice.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.currencyexchangeservice.model.ExchangeValue;
import com.example.microservice.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	//	TODO: Using env
	@Autowired
	private Environment environment;
	
	//	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	public CurrencyExchangeController(ExchangeValueRepository exchangeValueRepository) {
		super();
		this.exchangeValueRepository = exchangeValueRepository;
}



	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrievalueExchangeValue(@PathVariable String from, @PathVariable String to) {
		System.out.println("ExchangeValue");
		
	 ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
		
		exchangeValue.setPort(
				Integer.parseInt(environment.getProperty("local.server.port")));
		
		return exchangeValue;
	}
	
	@GetMapping("/currency-exchange-test-feign/{id}")
	public String retrievalueExchangeValueTestFeign(@PathVariable String id) {
		return "Hello " + id;
	}
}
