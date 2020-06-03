package com.matheuscordeiro.salesapi.service.impl;

import com.matheuscordeiro.salesapi.domain.entity.Users;
import com.matheuscordeiro.salesapi.domain.repository.UsersRepository;
import com.matheuscordeiro.salesapi.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public Users save(Users users){
        return usersRepository.save(users);
    }

    public UserDetails auth(Users users){
        UserDetails user = loadUserByUsername(users.getLogin());
        boolean passwordOk = encoder.matches(users.getPassword(), user.getPassword() );

        if(passwordOk){
            return user;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String[] roles = users.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(users.getLogin())
                .password(users.getPassword())
                .roles(roles)
                .build();
    }
}
