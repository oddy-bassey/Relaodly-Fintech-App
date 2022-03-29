package com.reloadly.transaction.query.api.model;

import com.reloadly.bank.core.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    private String id;
    private String accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal accountBalance;
    private Date createdDate;
}
