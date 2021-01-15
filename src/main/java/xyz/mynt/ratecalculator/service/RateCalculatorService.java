package xyz.mynt.ratecalculator.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import xyz.mynt.ratecalculator.exception.RateCalculatorServiceException;
import xyz.mynt.ratecalculator.exception.VoucherAPIException;
import xyz.mynt.ratecalculator.json.Request;
import xyz.mynt.ratecalculator.json.Response;
import xyz.mynt.ratecalculator.json.Voucher;
import xyz.mynt.ratecalculator.json.VoucherAPIResponse;
import xyz.mynt.ratecalculator.model.Rule;
import xyz.mynt.ratecalculator.repository.RuleRepository;

@Service
@Slf4j
public class RateCalculatorService {

	@Autowired
	private RuleRepository repository;

	@Autowired
	private VoucherAPIClient voucherAPIClient;

	public Response calculate(Request request) throws RateCalculatorServiceException {
		Response response = new Response(request);
		try {
			List<Rule> rules = repository.findAll();
			Map<Integer, Rule> rulesMap = new HashMap<>();
			rules.stream().forEach(r -> rulesMap.put(r.getPriority(), r));
			for (int i = 1; i <= rulesMap.size(); i++) {
				Rule rule = rulesMap.get(i);
				if (Rule.TYPE_WEIGHT.equals(rule.getType()) 
					&& request.getWeight().compareTo(rule.getMin()) >= 0
					&& null == rule.getMax()) {
					if (rule.getRejectFlag()) {
						response.setRejectFlag(Boolean.TRUE);
						response.setError("Parcel exceeded maximum limit");
					}
					break;
				} else if (Rule.TYPE_WEIGHT.equals(rule.getType()) 
					&& request.getWeight().compareTo(rule.getMin()) >= 0
					&& null != rule.getMax() 
					&& request.getWeight().compareTo(rule.getMax()) <= 0) {
					if (rule.getRejectFlag()) {
						response.setRejectFlag(Boolean.TRUE);
					} else {
						response.setGrossAmt(rule.getRate().multiply(request.getWeight()));
					}
					break;
				} else if (Rule.TYPE_VOLUME.equals(rule.getType()) 
					&& request.getVolume().compareTo(rule.getMin()) >= 0
					&& (null == rule.getMax() || request.getVolume().compareTo(rule.getMax()) <= 0)) {
					if (rule.getRejectFlag()) {
						response.setRejectFlag(Boolean.TRUE);
					} else {
						response.setGrossAmt(rule.getRate().multiply(request.getVolume()));
					}
					break;
				}
			}
			if (!response.getRejectFlag()) {
				if (!request.getVoucherCode().isEmpty()) {
					Voucher voucher = new Voucher(request.getVoucherCode());
					VoucherAPIResponse voucherAPIResponse = voucherAPIClient.request(request.getVoucherCode());
					if (null == voucherAPIResponse.getError() || voucherAPIResponse.getError().isEmpty()) {
						response.setDiscountAmt(voucherAPIResponse.getDiscount());
						voucher.setIsValid(Boolean.TRUE);
					} else {
						voucher.setError(voucherAPIResponse.getError());
					}
					response.setVoucher(voucher);
				}
				response.calculateNetAmt();
			}
			return response;
		} catch (VoucherAPIException e) {
			log.error("Error while calling Voucher API: " + e.getMessage());
			throw new RateCalculatorServiceException(e);
		} catch (Exception e) {
			log.error("Error whie calculating rate: " + e.getMessage());
			throw new RateCalculatorServiceException(e);
		}
	}

}
