package com.reloadly.transaction.processor.api.queries;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindTransactionByIdQuery {

    private String transactionId;
}
