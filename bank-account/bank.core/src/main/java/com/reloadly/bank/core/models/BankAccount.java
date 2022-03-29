package com.reloadly.bank.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {

    @Id
    private String id;
    private String accountHolderId;
    private String accountName;
    private String email;
    private Date creationDate;
    private AccountType accountType;
    private BigDecimal balance;
}
