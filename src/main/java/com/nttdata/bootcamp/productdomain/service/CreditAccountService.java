package com.nttdata.bootcamp.productdomain.service;

import com.nttdata.bootcamp.productdomain.model.CreditAccount;
import com.nttdata.bootcamp.productdomain.repository.CreditAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CreditAccount service.
 */
@Service
public class CreditAccountService {

  private final Logger log = LoggerFactory.getLogger(CreditAccountService.class);

  @Autowired
  private CreditAccountRepository creditAccountRepository;

  /**
   * FindAll creditAccount.
   */
  public Flux<CreditAccount> findAll() {
    log.info("CreditAccountService findAll ->");
    return creditAccountRepository.findAll();
  }

  public Mono<CreditAccount> findById(String id) {
    log.info("CreditAccountService findById ->");
    return creditAccountRepository.findById(id);
  }

  /**
   * create creditAccount.
   */
  public Mono<CreditAccount> create(CreditAccount creditAccount) {
    log.info("CreditAccountService create ->");
    return creditAccountRepository.findByType(creditAccount.getType())
            .flatMap(r -> Mono.error(new RuntimeException("Credit account exist!")))
            .switchIfEmpty(Mono.defer(() -> creditAccountRepository.save(creditAccount)))
            .cast(CreditAccount.class);

  }

  /**
   * update creditAccount.
   */
  public Mono<CreditAccount> update(CreditAccount creditAccount, String id) {
    log.info("CreditAccountService update ->");
    return creditAccountRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Credit account not found")))
            .flatMap(p -> creditAccountRepository.findByType(creditAccount.getType())
                    .switchIfEmpty(Mono.defer(() -> {
                      creditAccount.setId(id);
                      return creditAccountRepository.save(creditAccount);
                    }))
                    .flatMap(obj -> {
                      if (obj != null) {
                        if (obj.getId().equals(id)) {
                          creditAccount.setId(id);
                          return creditAccountRepository.save(creditAccount);
                        } else {
                          return Mono
                                  .error(new RuntimeException("Credit account exist other side!"));
                        }
                      } else {
                        creditAccount.setId(id);
                        return creditAccountRepository.save(creditAccount);
                      }
                    }));

  }

  /**
   * delete CreditAccount.
   */
  public Mono<Void> delete(CreditAccount customer) {
    log.info("CreditAccountService delete ->");
    return creditAccountRepository.delete(customer);
  }

}
