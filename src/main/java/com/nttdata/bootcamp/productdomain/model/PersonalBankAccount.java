package com.nttdata.bootcamp.productdomain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document(collection = "personalBankAccounts")
@Data
public class PersonalBankAccount {

    @Id
    private String id;
    private PersonalCustomer customer;
    @NotNull
    private String numberAccount;
    private BigDecimal amount;
    private BigDecimal maintenanceCommission;
    private Integer monthlyCommissionLimit;
    @NotNull
    private TypeAccountBank typeAccountBank;
}
