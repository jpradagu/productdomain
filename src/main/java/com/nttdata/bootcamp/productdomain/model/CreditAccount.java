package com.nttdata.bootcamp.productdomain.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** CreditAccount Document. */
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
