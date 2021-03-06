package com.nttdata.bootcamp.productdomain.service;

import com.nttdata.bootcamp.productdomain.model.CreditCard;
import com.nttdata.bootcamp.productdomain.repository.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CreditCard service.
 */
@Service
public class CreditCardService {

  private final Logger log = LoggerFactory.getLogger(CreditCardService.class);

  @Autowired
  private CreditCardRepository accountRepository;

  /**
   * FindAll creditCard.
   */
  public Flux<CreditCard> findAll() {
    log.info("CreditCardService findAll ->");
    return accountRepository.findAll();
  }

  /**
   * Find creditCard.
   */
  public Mono<CreditCard> findById(String id) {
    log.info("CreditCardService findById ->");
    return accountRepository.findById(id);
  }

  /**
   * Create creditCard.
   */
  public Mono<CreditCard> create(CreditCard account) {
    log.info("CreditCardService create ->");
    return accountRepository.findByType(account.getType())
            .flatMap(r -> Mono.error(new RuntimeException("CreditCard exist")))
            .switchIfEmpty(Mono.defer(() -> accountRepository.save(account)))
            .cast(CreditCard.class);
  }

  /**
   * Update creditCard.
   */
  public Mono<CreditCard> update(CreditCard account, String id) {
    log.info("CreditCardService update ->");
    return accountRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Credit card not found")))
            .flatMap(p -> accountRepository
                    .findByType(account.getType()).switchIfEmpty(Mono.defer(() -> {
                      account.setId(id);
                      return accountRepository.save(account);
                    })).flatMap(obj -> {
                      if (obj != null) {
                        if (obj.getId().equals(id)) {
                          account.setId(id);
                          return accountRepository.save(account);
                        } else {
                          return Mono.error(new RuntimeException("Credit card exist other side!"));
                        }
                      } else {
                        account.setId(id);
                        return accountRepository.save(account);
                      }
                    }));
  }

  /**
   * Delete creditCard.
   */
  public Mono<Void> delete(CreditCard customer) {
    return accountRepository.delete(customer);
  }

}
