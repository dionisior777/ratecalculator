package xyz.mynt.ratecalculator.json;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
public class VoucherAPIResponse {
	
	private String code;
	private BigDecimal discount;
	private LocalDate expiry;
	private String error;
	
	public VoucherAPIResponse() {
	}
	

}
