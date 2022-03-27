package com.reloadly.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotEmpty(message = "firstname is mandatory")
    private String firstName;

    @NotEmpty(message = "lastname is mandatory")
    private String lastName;

    @Email(message = "please provide a valid email address")
    private String email;

    @Valid
    @NotNull(message = "no user details were supplied")
    private Account account;
}
