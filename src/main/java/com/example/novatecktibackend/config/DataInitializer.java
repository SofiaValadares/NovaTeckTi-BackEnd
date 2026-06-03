package com.example.novatecktibackend.config;

import com.example.novatecktibackend.entity.Cliente;
import com.example.novatecktibackend.entity.ServicoTi;
import com.example.novatecktibackend.repository.ClienteRepository;
import com.example.novatecktibackend.repository.ServicoTiRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ServicoTiRepository servicoTiRepository;

    public DataInitializer(ClienteRepository clienteRepository, ServicoTiRepository servicoTiRepository) {
        this.clienteRepository = clienteRepository;
        this.servicoTiRepository = servicoTiRepository;
    }

    @Override
    public void run(String... args) {
        if (servicoTiRepository.count() == 0) {
            servicoTiRepository.save(criarServico("Suporte Técnico", "150.00", 1));
            servicoTiRepository.save(criarServico("Desenvolvimento de Sistemas", "2500.00", 14));
            servicoTiRepository.save(criarServico("Consultoria em TI", "800.00", 5));
            servicoTiRepository.save(criarServico("Manutenção Preventiva", "300.00", 3));
            servicoTiRepository.save(criarServico("Segurança da Informação", "1200.00", 7));
        }

        if (!clienteRepository.existsByLoginIgnoreCase("admin@novatech.com")) {
            Cliente admin = new Cliente();
            admin.setNome("Usuário Padrão");
            admin.setLogin("admin@novatech.com");
            admin.setSenha("Admin@123");
            admin.setCpf("000.000.000-00");
            admin.setDataNascimento(LocalDate.of(1990, 1, 1));
            admin.setTelefone("(11) 91234-5678");
            admin.setEstadoCivil("solteiro(a)");
            admin.setEscolaridade("2º grau completo");
            clienteRepository.save(admin);
        }
    }

    private ServicoTi criarServico(String nome, String preco, int prazoDias) {
        ServicoTi servico = new ServicoTi();
        servico.setNome(nome);
        servico.setPreco(new BigDecimal(preco));
        servico.setPrazoDias(prazoDias);
        return servico;
    }
}
