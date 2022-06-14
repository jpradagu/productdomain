package com.nttdata.bootcamp.productdomain.service;

import com.nttdata.bootcamp.productdomain.model.CustomerPersonal;
import com.nttdata.bootcamp.productdomain.model.PersonalBankAccount;
import com.nttdata.bootcamp.productdomain.repository.PersonalBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class PersonalBankAccountService {

    @Value("${maintenance.fee.savings}")
    private Double maintanceFeeSavings;

    @Value("${maintenance.fee.checkingAccount}")
    private Double maintanceFeeCheckingAccount;

    @Value("${maintenance.fee.fixedTerm}")
    private Double maintanceFeeFixedTerm;

    @Value("${monthly.commission.limit.savings}")
    private Integer monthlyCommissionLimitSavings;

    @Value("${monthly.commission.limit.checkingAccount}")
    private Integer monthlyCommissionLimitCheckingAccount;

    @Value("${monthly.commission.limit.fixedTerm}")
    private Integer monthlyCommissionLimitFixedTerm;

    @Autowired
    private PersonalBankAccountRepository accountRepository;

    @Autowired
    private WebClient.Builder webClient;

    public Flux<PersonalBankAccount> findAll() {
        return accountRepository.findAll();
    }

    public Mono<PersonalBankAccount> findById(String id) {
        return accountRepository.findById(id);
    }

    public Mono<PersonalBankAccount> save(PersonalBankAccount account) {
        return webClient.build().get()
                .uri("http://customerdomain/api/customer/personal/{id}",
                        Collections.singletonMap("id", account.getCustomer().getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(CustomerPersonal.class)
                .flatMap(p -> accountRepository.findAllByCustomerId(p.getId()).collectList().flatMap(r -> {
                    if (r.size() == 0) {
                        switch (account.getTypeAccountBank()) {
                            case SAVINGS:
                                account.setMaintenanceCommission(new BigDecimal(maintanceFeeSavings));
                                account.setMonthlyCommissionLimit(monthlyCommissionLimitSavings);
                                break;
                            case CHECKING_ACCOUNT:
                                account.setMaintenanceCommission(new BigDecimal(maintanceFeeCheckingAccount));
                                account.setMonthlyCommissionLimit(monthlyCommissionLimitCheckingAccount);
                                break;
                            default:
                                account.setMaintenanceCommission(new BigDecimal(maintanceFeeFixedTerm));
                                account.setMonthlyCommissionLimit(monthlyCommissionLimitFixedTerm);
                        }
                        account.setCustomer(p);
                        return accountRepository.save(account);
                    } else {
                        return Mono.empty();
                    }
                }));
    }

    public Mono<Void> delete(PersonalBankAccount customer) {
        return accountRepository.delete(customer);
    }

    public Flux<PersonalBankAccount> findAllByCustomer(String id) {
        return webClient.build().get()
                .uri("http://customerdomain/api/customer/personal/{id}", Collections.singletonMap("id", id))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(CustomerPersonal.class)
                .flatMap(p -> accountRepository.findAllByCustomerId(p.getId()));
    }
}
