package com.nttdata.bootcamp.productdomain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection = "bankAccounts")
@Data
public class BankAccount {
	@Id
	private String id;
	@NotNull
	private TypeAccountBank typeAccountBank;
	@NotNull
	private CustomerType customerType;
	@NotNull
	private String customerId;
	private Object customer;
	@NotNull
	private String numberAccount;
	private BigDecimal amount;
	@NotEmpty
	private List<Headline> headlines;
	private List<String> signatories;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date openingDate;
	private BigDecimal maintenanceCommission;
	private BigDecimal monthlyCommission;
}
