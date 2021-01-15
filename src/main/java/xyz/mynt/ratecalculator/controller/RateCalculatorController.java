package xyz.mynt.ratecalculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import xyz.mynt.ratecalculator.exception.RateCalculatorServiceException;
import xyz.mynt.ratecalculator.json.Request;
import xyz.mynt.ratecalculator.json.Response;
import xyz.mynt.ratecalculator.service.RateCalculatorService;

@RestController
@RequestMapping("/rate/calculator")
@Slf4j
public class RateCalculatorController {

	@Autowired
	private RateCalculatorService service;
	
	@PostMapping(path = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> calculate(@RequestBody Request request) {
		try {
			Response response = service.calculate(request);
			return ResponseEntity.ok(response);
		} catch (RateCalculatorServiceException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
		}
	}
}
