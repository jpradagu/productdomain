package com.nttdata.bootcamp.productdomain.controller;

import com.nttdata.bootcamp.productdomain.model.AccountBank;
import com.nttdata.bootcamp.productdomain.service.AccountBankService;
import com.nttdata.bootcamp.productdomain.util.exception.ResumenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product/account-bank")
public class AccountBankController {

    private final Logger log = LoggerFactory.getLogger(AccountBankController.class);
    @Autowired
    private AccountBankService accountBankService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountBank>>> findAll() {
        log.info("AccountBankController findAll ->");
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(accountBankService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountBank>> findById(@PathVariable String id) {
        log.info("AccountBankController findById ->");
        return accountBankService.findById(id)
                .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<AccountBank> accountBankMono) {
        log.info("AccountBankController create ->");
        Map<String, Object> result = new HashMap<>();
        return accountBankMono.flatMap(c -> {
            c.setId(null);
            return accountBankService.create(c).map(p -> ResponseEntity
                    .created(URI.create("/api/product/credit-card/".concat(p.getId())))
                    .contentType(MediaType.APPLICATION_JSON).body(result));
        }).onErrorResume(ResumenException::errorResumenException);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> update(@RequestBody Mono<AccountBank> accountBankMono, @PathVariable String id) {
        log.info("AccountBankController update ->");
        Map<String, Object> result = new HashMap<>();
        return accountBankMono.flatMap(c -> accountBankService.update(c, id).flatMap(accountBank -> {
            result.put("data", accountBank);
            return Mono.just(ResponseEntity.ok().body(result));
        })).onErrorResume(ResumenException::errorResumenException);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("CreditCardController delete ->");
        return accountBankService.findById(id)
                .flatMap(e -> accountBankService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
