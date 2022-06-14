package com.nttdata.bootcamp.productdomain.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection = "bankLoans")
@Data
public class BankLoan {
	@Id
	private String id;
	@NotNull
	private String creditCard;
	@NotNull
	private TypeAccountCredit typeAccountCredit;
	@NotNull
	private CustomerType customerType;
	@NotNull
	private String customerId;
	private Object customer;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	private Integer quotaNumber;
	private BigDecimal amount;
}
