package com.devsuperior.desafio_CRUD.controllers;

import com.devsuperior.desafio_CRUD.dto.ClientDTO;
import com.devsuperior.desafio_CRUD.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        ClientDTO clientDTO = clientService.findById(id);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable){
        Page<ClientDTO> clientDTOS = clientService.findAll(pageable);
        return ResponseEntity.ok(clientDTOS);
    }

}
