package com.reloadly.user.cmd.api.controllers;

import com.reloadly.user.cmd.api.commands.UpdateUserCommand;
import com.reloadly.user.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/updateUser")
public class UpdateUserController {

    private final CommandGateway commandGateway;

    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @RequestBody @Valid UpdateUserCommand command){
        try{
            command.setId(id);
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("user updated successfully"), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            var safeErrorMessage = "Error occurred while processing update user request for id - "+id;

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
























