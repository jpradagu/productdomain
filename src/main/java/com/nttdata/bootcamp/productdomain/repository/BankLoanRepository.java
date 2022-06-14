package com.nttdata.bootcamp.productdomain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.productdomain.model.BankLoan;

public interface BankLoanRepository extends ReactiveMongoRepository<BankLoan, String>{

}
