package com.example.novatecktibackend.repository;

import com.example.novatecktibackend.entity.SolicitacaoServicoTi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SolicitacaoServicoTiRepository extends JpaRepository<SolicitacaoServicoTi, Long> {

    List<SolicitacaoServicoTi> findByClienteLoginIgnoreCaseOrderByIdAsc(String login);

    @Modifying
    @Transactional
    void deleteByClienteId(Long clienteId);
}
