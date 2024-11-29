package com.devsuperior.desafio_CRUD.services;

import com.devsuperior.desafio_CRUD.dto.ClientDTO;
import com.devsuperior.desafio_CRUD.entities.Client;
import com.devsuperior.desafio_CRUD.repositories.ClientRepository;
import com.devsuperior.desafio_CRUD.services.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDTO findById(Long id){
        Optional<Client> result = clientRepository.findById(id);
        return result
                .map(client -> new ClientDTO(client.getId(), client.getName(), client.getCpf(), client.getIncome(), client.getBirthDate(), client.getChildren()))
                .orElseThrow(() -> new ClientNotFoundException("Cliente inexistente."));
    }

    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(client -> new ClientDTO(client.getId(), client.getName(), client.getCpf(), client.getIncome(), client.getBirthDate(), client.getChildren()));
    }

}
