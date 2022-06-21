package com.nttdata.bootcamp.productdomain.service;

import com.nttdata.bootcamp.productdomain.model.AccountBank;
import com.nttdata.bootcamp.productdomain.repository.AccountBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * AccountBank service.
 */
@Service
public class AccountBankService {

  private final Logger log = LoggerFactory.getLogger(AccountBankService.class);

  @Autowired
  private AccountBankRepository accountBankRepository;

  /**
   * FindAll accountBank.
   */
  public Flux<AccountBank> findAll() {
    log.info("AccountBankService findAll ->");
    return accountBankRepository.findAll();
  }

  public Mono<AccountBank> findById(String id) {
    log.info("AccountBankService findById ->");
    return accountBankRepository.findById(id);
  }

  /**
   * create accountBank.
   */
  public Mono<AccountBank> create(AccountBank accountBank) {
    log.info("AccountBankService create ->");
    return accountBankRepository.findByType(accountBank.getType())
            .flatMap(r -> Mono.error(new RuntimeException("Account bank exist!")))
            .switchIfEmpty(Mono.defer(() -> accountBankRepository.save(accountBank)))
            .cast(AccountBank.class);
  }

  /**
   * update accountBank.
   */
  public Mono<AccountBank> update(AccountBank accountBank, String id) {
    log.info("AccountBankService update ->");
    return accountBankRepository
            .findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Account bank not found")))
            .flatMap(p -> accountBankRepository.findByType(accountBank.getType())
                    .switchIfEmpty(Mono.defer(() -> {
                      accountBank.setId(id);
                      return accountBankRepository.save(accountBank);
                    })).flatMap(obj -> {
                      if (obj != null) {
                        if (obj.getId().equals(id)) {
                          accountBank.setId(id);
                          return accountBankRepository.save(accountBank);
                        } else {
                          return Mono.error(new RuntimeException("Account bank exist other side!"));
                        }
                      } else {
                        accountBank.setId(id);
                        return accountBankRepository.save(accountBank);
                      }
                    }));
  }

  /**
   * delete accountBank.
   */
  public Mono<Void> delete(AccountBank accountBank) {
    log.info("AccountBankService delete ->");
    return accountBankRepository.delete(accountBank);
  }

}
