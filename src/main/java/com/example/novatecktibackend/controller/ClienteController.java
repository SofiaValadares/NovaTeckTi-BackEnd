package com.example.novatecktibackend.controller;

import com.example.novatecktibackend.dto.ClienteRequest;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<OperacaoResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.cadastrar(request));
    }
}
