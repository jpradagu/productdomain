package com.nttdata.bootcamp.productdomain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.productdomain.model.BankLoan;
import com.nttdata.bootcamp.productdomain.repository.BankLoanRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankLoanService {
	
	@Autowired
	private BankLoanRepository loanRepository;
	
	public Flux<BankLoan> findAll() {
		return loanRepository.findAll();
	}

	public Mono<BankLoan> findById(String id) {
		return loanRepository.findById(id);
	}

	public Mono<BankLoan> save(BankLoan loan) {
		return loanRepository.save(loan);
	}
	
	public Mono<Void> delete(BankLoan loan) {
		return loanRepository.delete(loan);
	}

}
