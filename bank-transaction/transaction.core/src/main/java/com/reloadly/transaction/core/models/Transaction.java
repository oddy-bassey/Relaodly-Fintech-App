package com.reloadly.transaction.cmd.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
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
    private String senderAccountId;
    private String receiverAccountId;
    private BigDecimal amount;
    private Date createdDate;
    private TransactionStatus transactionStatus;
}
