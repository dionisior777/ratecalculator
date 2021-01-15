package xyz.mynt.ratecalculator.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rule", schema="mynt")
@Getter
@Setter
public class Rule {
	
	public static final String TYPE_WEIGHT = "WEIGHT";
	public static final String TYPE_VOLUME = "VOLUME";
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "rule_name")
	private String ruleName;

	@Column(name = "priority")
	private Integer priority;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "min")
	private BigDecimal min;
	
	@Column(name = "max")
	private BigDecimal max;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "reject_flag")
	private Boolean rejectFlag; 
}
