package com.devsuperior.desafio_CRUD.services;

import com.devsuperior.desafio_CRUD.dto.ClientDTO;
import com.devsuperior.desafio_CRUD.entities.Client;
import com.devsuperior.desafio_CRUD.repositories.ClientRepository;
import com.devsuperior.desafio_CRUD.services.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Serviço responsável por gerenciar operações relacionadas aos clientes.
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Busca um cliente pelo ID.
     *
     * @param id Identificador único do cliente.
     * @return ClientDTO contendo os dados do cliente.
     * @throws ClientNotFoundException caso o cliente não seja encontrado.
     */
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> result = clientRepository.findById(id);
        return result
                .map(this::convertClientToDTO)
                .orElseThrow(() -> new ClientNotFoundException("Cliente inexistente."));
    }

    /**
     * Busca todos os clientes de forma paginada.
     *
     * @param pageable Objeto que contém informações de paginação.
     * @return Página contendo os dados dos clientes no formato DTO.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(this::convertClientToDTO);
    }

    /**
     * Insere um novo cliente no banco de dados.
     *
     * @param clientDTO Objeto contendo os dados do cliente.
     * @return ClientDTO com os dados do cliente salvo.
     */
    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        Client client = clientRepository.save(convertDTOToClient(clientDTO));
        return convertClientToDTO(client);
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param id        Identificador único do cliente.
     * @param clientDTO Objeto contendo os novos dados do cliente.
     * @return ClientDTO com os dados atualizados.
     * @throws ClientNotFoundException caso o cliente não seja encontrado.
     */
    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        validateClientExistenceById(id);
        Client client = clientRepository.getReferenceById(id);
        updateClientData(client, clientDTO);
        clientRepository.save(client);
        return convertClientToDTO(client);
    }

    /**
     * Exclui um cliente pelo ID.
     *
     * @param id Identificador único do cliente.
     * @throws ClientNotFoundException caso o cliente não seja encontrado.
     */
    @Transactional
    public void delete(Long id) {
        validateClientExistenceById(id);
        clientRepository.deleteById(id);
    }

    /**
     * Verifica se o cliente existe no banco de dados pelo ID.
     *
     * @param id Identificador único do cliente.
     * @throws ClientNotFoundException caso o cliente não exista.
     */
    private void validateClientExistenceById(Long id) {
        if (!clientRepository.existsById(id)) throw new ClientNotFoundException("Cliente inexistente.");
    }

    /**
     * Atualiza os dados de uma entidade cliente com base no DTO.
     *
     * @param client    Entidade cliente a ser atualizada.
     * @param clientDTO Objeto contendo os novos dados.
     */
    private void updateClientData(Client client, ClientDTO clientDTO) {
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
    }

    /**
     * Converte um DTO de cliente para a entidade cliente.
     *
     * @param clientDTO Objeto DTO a ser convertido.
     * @return Entidade cliente correspondente.
     */
    private Client convertDTOToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getCpf(),
                clientDTO.getIncome(),
                clientDTO.getBirthDate(),
                clientDTO.getChildren());
    }

    /**
     * Converte uma entidade cliente para um DTO.
     *
     * @param client Entidade cliente a ser convertida.
     * @return Objeto DTO correspondente.
     */
    private ClientDTO convertClientToDTO(Client client) {
        return new ClientDTO(client.getId(),
                client.getName(),
                client.getCpf(),
                client.getIncome(),
                client.getBirthDate(),
                client.getChildren());
    }

}