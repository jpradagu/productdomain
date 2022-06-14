package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.PersonalBankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface PersonalBankAccountRepository extends ReactiveMongoRepository<PersonalBankAccount, String>{
    Flux<PersonalBankAccount> findAllByCustomerId(String id);
}
