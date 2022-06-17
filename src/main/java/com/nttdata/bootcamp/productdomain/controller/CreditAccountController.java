package com.nttdata.bootcamp.productdomain.controller;

import com.nttdata.bootcamp.productdomain.model.CreditAccount;
import com.nttdata.bootcamp.productdomain.service.CreditAccountService;
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
@RequestMapping("/api/product/credit-account")
public class CreditAccountController {

    private final Logger log = LoggerFactory.getLogger(CreditAccountController.class);

    @Autowired
    private CreditAccountService creditAccountService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CreditAccount>>> findAll() {
        log.info("CreditAccountController findAll ->");
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(creditAccountService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CreditAccount>> findById(@PathVariable String id) {
        log.info("CreditAccountController findById ->");
        return creditAccountService.findById(id)
                .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<CreditAccount> creditAccountMono) {
        log.info("CreditAccountController create ->");
        Map<String, Object> result = new HashMap<>();
        return creditAccountMono.flatMap(c -> {
            c.setId(null);
            return creditAccountService.create(c).map(p -> ResponseEntity
                    .created(URI.create("/api/product/credit-card/".concat(p.getId())))
                    .contentType(MediaType.APPLICATION_JSON).body(result));
        }).onErrorResume(ResumenException::errorResumenException);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> update(@RequestBody Mono<CreditAccount> creditAccountMono, @PathVariable String id){
        log.info("CreditAccountController update ->");
        Map<String, Object> result = new HashMap<>();
        return creditAccountMono.flatMap(c -> creditAccountService.update(c, id).flatMap(creditCard -> {
            result.put("data", creditCard);
            return Mono.just(ResponseEntity.ok().body(result));
        })).onErrorResume(ResumenException::errorResumenException);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        log.info("CreditAccountController delete ->");
        return creditAccountService.findById(id)
                .flatMap(e -> creditAccountService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
