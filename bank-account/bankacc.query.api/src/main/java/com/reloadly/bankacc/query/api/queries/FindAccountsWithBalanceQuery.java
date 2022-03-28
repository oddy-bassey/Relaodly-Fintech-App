package com.reloadly.bankacc.query.api.queries;

import com.reloadly.bankacc.query.api.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class FindAccountsWithBalanceQuery {

    private EqualityType equalityType;
    private BigDecimal balance;
}
