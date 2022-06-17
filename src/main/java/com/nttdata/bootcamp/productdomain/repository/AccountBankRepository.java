package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.AccountBank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountBankRepository extends ReactiveMongoRepository<AccountBank, String> {
    Mono<AccountBank> findByType(String type);
}
