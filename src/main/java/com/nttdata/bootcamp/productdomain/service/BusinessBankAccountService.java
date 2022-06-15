package com.nttdata.bootcamp.productdomain.service;

import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.productdomain.model.BusinessBankAccount;
import com.nttdata.bootcamp.productdomain.model.CommercialCustomer;
import com.nttdata.bootcamp.productdomain.model.TypeAccountBank;
import com.nttdata.bootcamp.productdomain.repository.BusinessBankAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessBankAccountService {
	@Value("${maintenance.fee.checkingAccount}")
	private Double maintanceFeeCheckingAccount;

	@Value("${monthly.commission.limit.checkingAccount}")
	private Integer monthlyCommissionLimitCheckingAccount;

	@Autowired
	private BusinessBankAccountRepository accountRepository;

	@Autowired
	private WebClient.Builder webClient;

	public BusinessBankAccountService() {
	}

	public Flux<BusinessBankAccount> findAll() {
		return accountRepository.findAll();
	}

	public Mono<BusinessBankAccount> findById(String id) {
		return accountRepository.findById(id);
	}

	public Mono<BusinessBankAccount> save(BusinessBankAccount account) {
		return webClient.build().get()
				.uri("http://customerdomain/api/customer/enterprise/{id}",
						Collections.singletonMap("id", account.getCustomer().getId()))
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(CommercialCustomer.class).flatMap(e -> {
					if (account.getTypeAccountBank() == TypeAccountBank.CHECKING_ACCOUNT) {
						account.setMaintenanceCommission(new BigDecimal(maintanceFeeCheckingAccount));
						account.setMonthlyCommissionLimit(monthlyCommissionLimitCheckingAccount);
						account.setCustomer(e);
						return accountRepository.save(account);
					} else {
						return Mono.empty();
					}
				});
	}

	public Mono<Void> delete(BusinessBankAccount customer) {
		return accountRepository.delete(customer);
	}

	public Flux<BusinessBankAccount> findAllByCustomer(String id) {
		return webClient.build().get()
				.uri("http://customerdomain/api/customer/enterprise/{id}", Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(CommercialCustomer.class)
				.flatMap(p -> accountRepository.findAllByCustomerId(p.getId()));
	}
}
