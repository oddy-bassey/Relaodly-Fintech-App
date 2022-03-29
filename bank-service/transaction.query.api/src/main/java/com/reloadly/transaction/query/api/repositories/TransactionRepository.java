package com.reloadly.transaction.query.api.repositories;

import com.reloadly.transaction.query.api.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    Optional<Transaction> findByAccountId(String accountId);
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);
    List<Transaction> findByAmountLessThan(BigDecimal amount);
}
