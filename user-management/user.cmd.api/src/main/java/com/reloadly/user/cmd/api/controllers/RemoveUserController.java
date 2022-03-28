package com.reloadly.user.cmd.api.controllers;

import com.reloadly.user.cmd.api.commands.RemoveUserCommand;
import com.reloadly.user.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/removeUser")
public class RemoveUserController {

    private final CommandGateway commandGateway;

    public RemoveUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable(value = "id") String id){
        try{
            commandGateway.sendAndWait(new RemoveUserCommand(id));

            return new ResponseEntity<>(new BaseResponse("user successfully removed"), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            var safeErrorMessage = "Error occurred while processing remove user request for id - "+id;

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
