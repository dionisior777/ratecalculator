package xyz.mynt.ratecalculator.exception;

public class RateCalculatorServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RateCalculatorServiceException(Exception e) {
		super(e);
	}

}
