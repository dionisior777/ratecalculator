package xyz.mynt.ratecalculator.exception;

public class VoucherAPIException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public VoucherAPIException(Exception e) {
		super(e);
	}
}
