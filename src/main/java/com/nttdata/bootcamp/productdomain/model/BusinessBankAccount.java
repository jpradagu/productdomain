package com.nttdata.bootcamp.productdomain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "businessBankAccounts")
@Data
public class BusinessBankAccount {
    @Id
    private String id;
    private CustomerEnterprise customer;
    private String numberAccount;
    private BigDecimal amount;
    @NotEmpty
    private List<Headline> headlines;
    private List<String> signatories;
    private BigDecimal maintenanceCommission;
    private Integer monthlyCommissionLimit;
    @NotNull
    private TypeAccountBank typeAccountBank;
}
