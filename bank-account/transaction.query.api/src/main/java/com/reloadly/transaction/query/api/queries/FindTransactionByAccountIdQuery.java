package com.reloadly.transaction.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindTransactionByAccountIdQuery {

    private String accountId;
}
