package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.api.dto.CredentialsDTO;
import com.matheuscordeiro.salesapi.api.dto.TokenDTO;
import com.matheuscordeiro.salesapi.domain.entity.Users;
import com.matheuscordeiro.salesapi.exception.InvalidPasswordException;
import com.matheuscordeiro.salesapi.security.jwt.JwtService;
import com.matheuscordeiro.salesapi.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersServiceImpl usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users saver(@RequestBody @Valid Users users ){
        String encryptedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encryptedPassword);
        return usersService.save(users);
    }

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredentialsDTO credentialsDTO){
        try{
            Users users = Users.builder()
                    .login(credentialsDTO.getLogin())
                    .password(credentialsDTO.getPassword()).build();
            UserDetails userAuth = usersService.auth(users);
            String token = jwtService.generateToken(users);
            return new TokenDTO(users.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
