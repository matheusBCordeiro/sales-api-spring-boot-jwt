package com.matheuscordeiro.salesapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "client" )
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(length = 100)
    @NotEmpty(message = "Field name is required")
    @CPF(message = "Please provide a valid CPF")
    private String name;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "Field CPF is required.")
    private String cpf;

    @JsonIgnore
    @OneToMany( mappedBy = "client" , fetch = FetchType.LAZY )
    private Set<Order> order;
}
