package com.nttdata.bootcamp.productdomain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.productdomain.model.PersonalBankLoan;
import com.nttdata.bootcamp.productdomain.repository.PersonalBankLoanRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalBankLoanService {
	
	@Autowired
	private PersonalBankLoanRepository loanRepository;
	
	public Flux<PersonalBankLoan> findAll() {
		return loanRepository.findAll();
	}

	public Mono<PersonalBankLoan> findById(String id) {
		return loanRepository.findById(id);
	}

	public Mono<PersonalBankLoan> save(PersonalBankLoan loan) {
		return loanRepository.save(loan);
	}
	
	public Mono<Void> delete(PersonalBankLoan loan) {
		return loanRepository.delete(loan);
	}

}
