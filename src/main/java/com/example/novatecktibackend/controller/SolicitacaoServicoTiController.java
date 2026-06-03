package com.example.novatecktibackend.controller;

import com.example.novatecktibackend.dto.AtualizarSolicitacoesRequest;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.SolicitacoesListaResponse;
import com.example.novatecktibackend.service.SolicitacaoServicoTiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoServicoTiController {

    private final SolicitacaoServicoTiService solicitacaoService;

    public SolicitacaoServicoTiController(SolicitacaoServicoTiService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public ResponseEntity<SolicitacoesListaResponse> listarPorLogin(@RequestParam String login) {
        return ResponseEntity.ok(solicitacaoService.listarPorLogin(login));
    }

    @PutMapping
    public ResponseEntity<OperacaoResponse> atualizar(@Valid @RequestBody AtualizarSolicitacoesRequest request) {
        return ResponseEntity.ok(solicitacaoService.atualizarSolicitacoes(request));
    }
}
