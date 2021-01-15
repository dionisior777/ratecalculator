package xyz.mynt.ratecalculator.json;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonDeserialize
public class Request {
	
	private BigDecimal weight;
	private BigDecimal height;
	private BigDecimal width;
	private BigDecimal length;
	private String voucherCode;
	
	public BigDecimal getVolume() {
		return this.height.multiply(this.weight).multiply(this.length);
	}
}
