package com.nttdata.bootcamp.productdomain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Document(collection = "accountBanks")
public class AccountBank {
	@Id
	private String id;
	@NotEmpty
	private String type;
	@NotNull
	private BigDecimal maintenanceCommission;
	@NotNull
	private BigDecimal transactionCommission;
	@NotNull
	private BigDecimal minimumOpeningAmount;
	@NotNull
	private int numLimitMovements;
	@NotNull
	private Boolean allowCompany;
	@NotNull
	private Boolean allowPerson;
	@NotNull
	private int dayMovement;
	@NotNull
	private Boolean needCreditCard;
}
