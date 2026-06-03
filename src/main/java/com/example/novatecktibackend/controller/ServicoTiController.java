package com.example.novatecktibackend.controller;

import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.ServicoTiRequest;
import com.example.novatecktibackend.dto.ServicosTiListaResponse;
import com.example.novatecktibackend.service.ServicoTiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicos-ti")
public class ServicoTiController {

    private final ServicoTiService servicoTiService;

    public ServicoTiController(ServicoTiService servicoTiService) {
        this.servicoTiService = servicoTiService;
    }

    @PostMapping
    public ResponseEntity<OperacaoResponse> cadastrar(@Valid @RequestBody ServicoTiRequest request) {
        return ResponseEntity.ok(servicoTiService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<ServicosTiListaResponse> listar() {
        return ResponseEntity.ok(servicoTiService.listarTodos());
    }
}
