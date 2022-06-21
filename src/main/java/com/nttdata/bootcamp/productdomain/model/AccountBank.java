package com.nttdata.bootcamp.productdomain.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** AccountBank Document.*/
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
  private Integer numLimitMovements;
  @NotNull
  private Boolean allowCompany;
  @NotNull
  private Boolean allowPerson;
  @NotNull
  private Integer dayMovement;
  @NotNull
  private Boolean needCreditCard;
}
