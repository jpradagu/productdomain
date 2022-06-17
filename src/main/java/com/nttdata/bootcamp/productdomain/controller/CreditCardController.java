package com.nttdata.bootcamp.productdomain.controller;

import com.nttdata.bootcamp.productdomain.model.CreditCard;
import com.nttdata.bootcamp.productdomain.service.CreditCardService;
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
@RequestMapping("/api/product/credit-card")
public class CreditCardController {

	private final Logger log = LoggerFactory.getLogger(CreditCardController.class);
	@Autowired
	private CreditCardService creditCardService;

	@GetMapping
	public Mono<ResponseEntity<Flux<CreditCard>>> findAll() {
		log.info("CreditCardController findAll ->");
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(creditCardService.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<CreditCard>> findById(@PathVariable String id) {
		log.info("CreditCardController findById ->");
		return creditCardService.findById(id)
				.map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<CreditCard> creditCardMono) {
		log.info("CreditCardController create ->");
		Map<String, Object> result = new HashMap<>();
		return creditCardMono.flatMap(c -> {
			c.setId(null);
			return creditCardService.create(c).map(p -> ResponseEntity
					.created(URI.create("/api/product/credit-card/".concat(p.getId())))
					.contentType(MediaType.APPLICATION_JSON).body(result));
		}).onErrorResume(ResumenException::errorResumenException);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Map<String, Object>>> update(@Valid @RequestBody Mono<CreditCard> creditCardMono, @PathVariable String id) {
		log.info("CreditCardController update ->");
		Map<String, Object> result = new HashMap<>();
		return creditCardMono.flatMap(c -> creditCardService.update(c, id).flatMap(creditCard -> {
			result.put("data", creditCard);
			return Mono.just(ResponseEntity.ok().body(result));
		})).onErrorResume(ResumenException::errorResumenException);
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		log.info("CreditCardController delete ->");
		return creditCardService.findById(id)
				.flatMap(e -> creditCardService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
