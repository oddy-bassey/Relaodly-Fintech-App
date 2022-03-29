package com.reloadly.transaction.cmd.api.controllers;

import com.reloadly.bank.core.commands.DepositFundsCommand;
import com.reloadly.bank.core.commands.WithdrawFundsCommand;
import com.reloadly.bank.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    private final CommandGateway commandGateway;

    @Autowired
    public TransactionsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/withdrawFunds/{accountId}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "accountId") String accountId,
                                                      @Valid @RequestBody WithdrawFundsCommand command){

        try{
            command.setId(accountId);
            commandGateway.send(command).get();

            return new ResponseEntity<>(new BaseResponse("withdrawal processing..!"), HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Error occurred while processing request to withdraw from Bank account with id - "+accountId;
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/depositFunds/{accountId}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "accountId") String accountId,
                                                     @Valid @RequestBody DepositFundsCommand command){

        try{
            command.setId(accountId);
            commandGateway.send(command).get();

            return new ResponseEntity<>(new BaseResponse("deposit request processing..!"), HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Error while processing request to deposit into Bank account with id - "+accountId;
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
