package com.devsuperior.desafio_CRUD.repositories;

import com.devsuperior.desafio_CRUD.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository
        extends JpaRepository<Client, Long> {
}
