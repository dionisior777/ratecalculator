package xyz.mynt.ratecalculator.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonDeserialize
public class Voucher {
	
	private String code;
	private Boolean isValid;
	private String error;
	
	public Voucher(String code) {
		this.code = code;
		this.isValid = false;
		this.error = "";
	}
}
