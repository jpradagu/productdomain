package com.nttdata.bootcamp.productdomain.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.productdomain.model.BankAccount;
import com.nttdata.bootcamp.productdomain.model.CustomerEnterprise;
import com.nttdata.bootcamp.productdomain.model.CustomerPersonal;
import com.nttdata.bootcamp.productdomain.model.CustomerType;
import com.nttdata.bootcamp.productdomain.repository.BankAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private WebClient.Builder webClient;

    public Flux<BankAccount> findAll() {
        return accountRepository.findAll();
    }

    public Mono<BankAccount> findById(String id) {
        return accountRepository.findById(id);
    }

    public Mono<BankAccount> save(BankAccount account) {
        if (account.getCustomerType() == CustomerType.ENTERPRISE) {
            return webClient.build().get()
                    .uri("http://customerdomain/api/customer/enterprise/{id}", Collections.singletonMap("id", account.getCustomerId()))
                    .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(CustomerEnterprise.class).flatMap(e -> {
                        account.setCustomer(e);
                        return accountRepository.save(account);
                    });
        } else {
            return webClient.build().get()
                    .uri("http://customerdomain/api/customer/personal/{id}", Collections.singletonMap("id", account.getCustomerId()))
                    .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(CustomerPersonal.class).flatMap(p -> {
                        account.setCustomer(p);
                        return accountRepository.save(account);
                    });
        }

    }

    public Mono<Void> delete(BankAccount customer) {
        return accountRepository.delete(customer);
    }

    public Flux<BankAccount> findAllByCustomer(String id) {
        return webClient.build().get().uri("http://customerdomain/api/customer/enterprise/{id}", Collections.singletonMap("id", id))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(CustomerEnterprise.class)
                .flatMap(p -> accountRepository.findAllByCustomerIdAndCustomerType(p.getId(), CustomerType.ENTERPRISE));
    }
}
