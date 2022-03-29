package com.reloadly.bankacc.cmd.api.controllers;


import com.reloadly.bankacc.cmd.api.commands.CloseAccountCommand;
import com.reloadly.bank.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/closeBankAccount")
public class CloseAccountController {

    private final CommandGateway commandGateway;

    public CloseAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id){

        try{
            CloseAccountCommand command = CloseAccountCommand.builder()
                    .id(id)
                    .build();
            commandGateway.send(command).get();

            return new ResponseEntity<>(new BaseResponse("bank account successfully closed!"), HttpStatus.OK);
        }catch(Exception e){
            var safeErrorMessage = "Error occurred while processing request to close Bank account for id - "+id;
            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
