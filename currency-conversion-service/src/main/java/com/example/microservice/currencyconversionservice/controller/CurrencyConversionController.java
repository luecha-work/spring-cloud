package com.example.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.microservice.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.example.microservice.currencyconversionservice.models.CurrencyConversionBean;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(
			@PathVariable String from,
			@PathVariable String to, 
			@PathVariable BigDecimal quantity ) {
		
		// Feign - Problem 1 
		Map<String, String> urlVariables = new HashMap<>();
		urlVariables.put("from", from);
		urlVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> responseEntity  = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, 
				urlVariables);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		return new CurrencyConversionBean(
				response.getId(), 
				from, 
				to,
				response.getConversionMultiple() ,
				quantity, 
				quantity.multiply(response.getConversionMultiple()), 
				response.getPort());
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(
			@PathVariable String from,
			@PathVariable String to, 
			@PathVariable BigDecimal quantity ) {
		
		CurrencyConversionBean response = proxy.retrievalueExchangeValue(from, to);
		
		return new CurrencyConversionBean(
				response.getId(), 
				from, 
				to,
				response.getConversionMultiple() ,
				quantity, 
				quantity.multiply(response.getConversionMultiple()), 
				response.getPort());
	}
	
	@GetMapping("/currency-conversion-test-feign/{id}")
	public String convertCurrencyTestFeign(@PathVariable String id) {
		String response = proxy.retrievalueExchangeValueTestFeign(id);
		
		return response;
	}

}
