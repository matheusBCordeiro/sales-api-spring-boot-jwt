package com.matheuscordeiro.salesapi.domain.repository;

import com.matheuscordeiro.salesapi.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLogin(String login);
}
