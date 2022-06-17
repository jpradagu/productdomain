package com.nttdata.bootcamp.productdomain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "creditAccounts")
public class CreditAccount {
    @Id
    private String id;
    @NotBlank
    private String type;
    @NotNull
    private BigDecimal interestRateMonth;
    @NotNull
    private Boolean allowCompany;
    @NotNull
    private Boolean allowPerson;
    @NotNull
    private Boolean needCreditCard;
}
