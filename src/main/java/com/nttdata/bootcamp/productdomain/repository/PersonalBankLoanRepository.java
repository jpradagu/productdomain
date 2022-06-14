package com.nttdata.bootcamp.productdomain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.productdomain.model.PersonalBankLoan;

public interface PersonalBankLoanRepository extends ReactiveMongoRepository<PersonalBankLoan, String>{

}
