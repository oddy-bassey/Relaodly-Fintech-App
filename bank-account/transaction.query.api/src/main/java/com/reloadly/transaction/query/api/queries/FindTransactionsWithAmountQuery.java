package com.reloadly.transaction.query.api.queries;

import com.reloadly.transaction.query.api.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class FindTransactionsWithAmountQuery {

    private EqualityType equalityType;
    private BigDecimal amount;
}
