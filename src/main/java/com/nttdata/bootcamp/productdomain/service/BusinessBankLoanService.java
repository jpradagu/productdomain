package com.nttdata.bootcamp.productdomain.service;

import com.nttdata.bootcamp.productdomain.model.BusinessBankLoan;
import com.nttdata.bootcamp.productdomain.repository.BusinessBankLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessBankLoanService {
	
	@Autowired
	private BusinessBankLoanRepository loanRepository;
	
	public Flux<BusinessBankLoan> findAll() {
		return loanRepository.findAll();
	}

	public Mono<BusinessBankLoan> findById(String id) {
		return loanRepository.findById(id);
	}

	public Mono<BusinessBankLoan> save(BusinessBankLoan loan) {
		return loanRepository.save(loan);
	}
	
	public Mono<Void> delete(BusinessBankLoan loan) {
		return loanRepository.delete(loan);
	}

}
