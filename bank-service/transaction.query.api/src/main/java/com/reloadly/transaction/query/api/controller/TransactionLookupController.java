package com.reloadly.transaction.query.api.controller;

import com.reloadly.transaction.query.api.dto.EqualityType;
import com.reloadly.transaction.query.api.dto.TransactionLookupResponse;
import com.reloadly.transaction.query.api.queries.FindAllTransactionsQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByAccountIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionsWithAmountQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/transactionLookup")
public class TransactionLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public TransactionLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<TransactionLookupResponse> getAllTransactions(){

        try{
            var response = queryGateway
                    .query(new FindAllTransactionsQuery(), ResponseTypes.instanceOf(TransactionLookupResponse.class)).join();

            if (response == null || response.getTransactions() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get all transactions request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new TransactionLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byTransactionId/{transactionId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<TransactionLookupResponse> getAccountById(@PathVariable(value = "transactionId") String transactionId){

        try{
            var response = queryGateway
                    .query(new FindTransactionByIdQuery(transactionId), ResponseTypes.instanceOf(TransactionLookupResponse.class)).join();

            if (response == null || response.getTransactions() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get transactions by transaction id request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new TransactionLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byAccountId/{accountId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<TransactionLookupResponse> getAccountByHolderId(@PathVariable(value = "accountId") String accountId){
        try{
            var response = queryGateway
                    .query(new FindTransactionByAccountIdQuery(accountId), ResponseTypes.instanceOf(TransactionLookupResponse.class)).join();

            if (response == null || response.getTransactions() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get transactions by account id request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new TransactionLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{amount}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<TransactionLookupResponse> withBalance(
            @PathVariable(value = "equalityType") EqualityType equalityType,
            @PathVariable(value = "amount") BigDecimal amount){

        try{
            var query = FindTransactionsWithAmountQuery.builder()
                    .equalityType(equalityType)
                    .amount(amount)
                    .build();

            var response = queryGateway
                    .query(query, ResponseTypes.instanceOf(TransactionLookupResponse.class)).join();

            if (response == null || response.getTransactions() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get transactions with amount request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new TransactionLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
