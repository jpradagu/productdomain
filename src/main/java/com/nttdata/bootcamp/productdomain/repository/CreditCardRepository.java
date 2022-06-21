package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/** CreditCard repository. */
public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
  Mono<CreditCard> findByType(String type);
}
