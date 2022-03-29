package com.reloadly.transaction.processor.api.queries;

import com.reloadly.transaction.processor.api.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class FindTransactionsWithBalanceQuery {

    private EqualityType equalityType;
    private BigDecimal balance;
}
