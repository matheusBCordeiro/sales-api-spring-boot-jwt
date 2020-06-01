package com.matheuscordeiro.salesapi.domain.repository;

import com.matheuscordeiro.salesapi.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = " select * from client c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Client> findByName(@Param("name") String name );

    @Query(" delete from Client c where c.name =:name ")
    @Modifying
    void deleteByName(String name);

    boolean existsByName(String name);

    @Query(" select c from Client c left join fetch c.order where c.id = :id  ")
    Client findClientFetchOrder( @Param("id") Integer id );


}
