package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.CustomerType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.productdomain.model.BankAccount;
import reactor.core.publisher.Flux;

public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String>{

    Flux<BankAccount> findAllByCustomerIdAndCustomerType(String customerId, CustomerType customerType);
}
