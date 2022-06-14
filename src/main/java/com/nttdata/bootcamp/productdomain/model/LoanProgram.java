package com.nttdata.bootcamp.productdomain.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "loanPrograms")
@Data
public class LoanProgram {

	private String id;
	private Date paymentDate;
	private BigDecimal amount;
	private Boolean isPaid;
	private Date lastdatePayment;
	private Integer position;
	
}
