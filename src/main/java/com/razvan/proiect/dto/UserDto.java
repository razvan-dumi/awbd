package com.razvan.proiect.dto;

import com.razvan.proiect.validators.PasswordMatches;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
@PasswordMatches
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
}
