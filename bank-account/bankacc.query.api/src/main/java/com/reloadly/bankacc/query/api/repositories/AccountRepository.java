package com.reloadly.bankacc.query.api.repositories;

import com.reloadly.bankacc.core.models.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolderId(String accountHolderId);
    List<BankAccount> findByBalanceGreaterThan(BigDecimal balance);
    List<BankAccount> findByBalanceLessThan(BigDecimal balance);
}
