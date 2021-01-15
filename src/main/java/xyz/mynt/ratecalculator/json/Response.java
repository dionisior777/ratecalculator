package xyz.mynt.ratecalculator.json;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonDeserialize
public class Response {

	private Request request;
	private Voucher voucher;
	private BigDecimal grossAmt;
	private BigDecimal discountAmt;
	private BigDecimal netAmt;
	private Boolean rejectFlag;
	private String error;
	
	public Response(Request request) {
		this.request = request;
		this.grossAmt = BigDecimal.ZERO;
		this.discountAmt = BigDecimal.ZERO;
		this.netAmt = BigDecimal.ZERO;
		this.error = "";
		this.rejectFlag = Boolean.FALSE;
	}
	
	public void calculateNetAmt() {
		this.netAmt = this.grossAmt.multiply((new BigDecimal(100.00))
				.subtract(this.discountAmt)
				.divide(new BigDecimal(100.00)))
				.setScale(2, RoundingMode.FLOOR);
	}
}
