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
                .map(this::convertClientToDTO)
                .orElseThrow(() -> new ClientNotFoundException("Cliente inexistente."));
    }

    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(this::convertClientToDTO);
    }

    public ClientDTO insert(ClientDTO clientDTO){
        Client client = clientRepository.save(convertDTOToClient(clientDTO));
        return convertClientToDTO(client);
    }

    private Client convertDTOToClient(ClientDTO clientDTO){
        return new Client(clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getCpf(),
                clientDTO.getIncome(),
                clientDTO.getBirthDate(),
                clientDTO.getChildren());
    }

    private ClientDTO convertClientToDTO(Client client){
        return new ClientDTO(client.getId(),
                client.getName(),
                client.getCpf(),
                client.getIncome(),
                client.getBirthDate(),
                client.getChildren());
    }

}
