package com.nttdata.bootcamp.productdomain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "businessBankLoans")
@Data
public class BusinessBankLoan {
    @Id
    private String id;
    private String numberAccount;
    private CustomerEnterprise customer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    private Integer quotaNumber;
    private BigDecimal amount;
}
