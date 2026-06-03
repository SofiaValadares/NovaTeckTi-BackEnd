package com.example.novatecktibackend.controller;

import com.example.novatecktibackend.dto.AutenticacaoRequest;
import com.example.novatecktibackend.dto.AutenticacaoResponse;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.TrocaSenhaRequest;
import com.example.novatecktibackend.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<AutenticacaoResponse> autenticar(@Valid @RequestBody AutenticacaoRequest request) {
        AutenticacaoResponse response = autenticacaoService.autenticarComStatus(
                request.getLogin(),
                request.getSenha()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/troca-senha")
    public ResponseEntity<OperacaoResponse> trocarSenha(@Valid @RequestBody TrocaSenhaRequest request) {
        OperacaoResponse response = autenticacaoService.trocarSenha(
                request.getLogin(),
                request.getSenhaAtual(),
                request.getNovaSenha()
        );
        return ResponseEntity.ok(response);
    }
}
