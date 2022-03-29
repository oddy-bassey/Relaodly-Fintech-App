package com.reloadly.bankacc.query.api.controller;

import com.reloadly.bankacc.query.api.dto.AccountLookupResponse;
import com.reloadly.bankacc.query.api.dto.EqualityType;
import com.reloadly.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountByIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.reloadly.bankacc.query.api.queries.FindAllAccountsQuery;
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
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public AccountLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){

        try{
            var response = queryGateway
                    .query(new FindAllAccountsQuery(), ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get all accounts request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id){

        try{
            var response = queryGateway
                    .query(new FindAccountByIdQuery(id), ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get account by id request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolderId/{accountHolderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable(value = "accountHolderId") String accountHolderId){
        try{
            var response = queryGateway
                    .query(new FindAccountByHolderIdQuery(accountHolderId), ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get account by holder id request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> withBalance(
            @PathVariable(value = "equalityType") EqualityType equalityType,
            @PathVariable(value = "balance") BigDecimal balance){

        try{
            var query = FindAccountsWithBalanceQuery.builder()
                    .equalityType(equalityType)
                    .balance(balance)
                    .build();

            var response = queryGateway
                    .query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Failed to complete get account with balance request";
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
