package com.reloadly.transaction.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindTransactionByIdQuery {

    private String transactionId;
}
