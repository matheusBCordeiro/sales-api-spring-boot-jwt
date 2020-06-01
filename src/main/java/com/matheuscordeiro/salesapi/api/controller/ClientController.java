package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.domain.entity.Client;
import com.matheuscordeiro.salesapi.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("{id}")
    public Client getClientById( @PathVariable Integer id ){
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Client save( @RequestBody Client client ){
        return clientRepository.save(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        clientRepository.findById(id)
                .map( client -> {
                    clientRepository.delete(client );
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente not found") );
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody Client client ){
        clientRepository
                .findById(id)
                .map( clientExisting -> {
                    client.setId(clientExisting.getId());
                    clientRepository.save(client);
                    return clientExisting;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found") );
    }

    @GetMapping
    public List<Client> find(Client filter ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return clientRepository.findAll(example);
    }
}
