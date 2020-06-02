package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.domain.entity.Product;
import com.matheuscordeiro.salesapi.domain.repository.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductRepository productRepository;

    @GetMapping("{id}")
    public Product getById(@PathVariable Integer id){
        return productRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
    }

    @GetMapping
    public List<Product> find(Product filter ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filter, matcher);
        return productRepository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Product save(@RequestBody @Valid Product product ){
        return productRepository.save(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        productRepository
                .findById(id)
                .map( p -> {
                    productRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody Product produto ){
        productRepository
                .findById(id)
                .map( p -> {
                    produto.setId(p.getId());
                    productRepository.save(produto);
                    return produto;
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }
}
