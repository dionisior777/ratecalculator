package xyz.mynt.ratecalculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import xyz.mynt.ratecalculator.exception.VoucherAPIException;
import xyz.mynt.ratecalculator.json.VoucherAPIResponse;

@Service
@Slf4j
public class VoucherAPIClient {

	@Value( "${voucher.api.url}" )
	private String url;
	
	@Value( "${voucher.api.key}" )
	private String key;
	
	public VoucherAPIResponse request(String voucherCode) throws VoucherAPIException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
					.path(voucherCode)
			        .queryParam("key", key);
			
			return restTemplate.getForObject(builder.toUriString(), VoucherAPIResponse.class);
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			if (status == HttpStatus.BAD_REQUEST) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				try {
					return objectMapper.readValue(e.getResponseBodyAsString().getBytes(), VoucherAPIResponse.class);
				} catch (Exception e1) {
					log.error("Error while parsing Voucher API message: " + e.getResponseBodyAsString());
					throw new VoucherAPIException(e);
				}
			}
			log.error("Error while calling Voucher API: " + e.getMessage());
		} catch (RestClientException e) {
			log.error(e.getMessage());
			throw new VoucherAPIException(e);
		}
		return null;
	}
	
	
}
