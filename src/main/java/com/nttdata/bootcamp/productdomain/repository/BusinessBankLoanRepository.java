package com.nttdata.bootcamp.productdomain.repository;

import com.nttdata.bootcamp.productdomain.model.BusinessBankLoan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BusinessBankLoanRepository extends ReactiveMongoRepository<BusinessBankLoan, String> {
}
