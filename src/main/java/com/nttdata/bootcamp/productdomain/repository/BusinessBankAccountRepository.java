package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.BusinessBankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BusinessBankAccountRepository extends ReactiveMongoRepository<BusinessBankAccount, String> {
    Flux<BusinessBankAccount> findAllByCustomerId(String id);
}
