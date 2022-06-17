package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.CreditAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditAccountRepository extends ReactiveMongoRepository<CreditAccount, String> {
    Mono<CreditAccount> findByType(String type);
}
