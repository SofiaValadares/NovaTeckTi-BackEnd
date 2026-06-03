package com.example.novatecktibackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NovateckTiApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void autenticacaoComUsuarioPadrao() throws Exception {
        mockMvc.perform(post("/api/autenticacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"login":"admin@novatech.com","senha":"Admin@123"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autenticado").value(true))
                .andExpect(jsonPath("$.status").value("SUCESSO"));
    }

    @Test
    void listarServicosTi() throws Exception {
        mockMvc.perform(get("/api/servicos-ti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCESSO"))
                .andExpect(jsonPath("$.servicos", hasSize(5)));
    }

    @Test
    void cadastroClienteDuplicadoRetornaErro() throws Exception {
        String body = """
                {
                  "nome":"Teste",
                  "login":"admin@novatech.com",
                  "senha":"Admin@123",
                  "cpf":"111.111.111-11",
                  "dataNascimento":"2000-01-01",
                  "telefone":"(11) 99999-9999",
                  "estadoCivil":"solteiro(a)",
                  "escolaridade":"2º grau completo"
                }
                """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ERRO"));
    }

    @Test
    void atualizarSolicitacoesDoUsuario() throws Exception {
        mockMvc.perform(put("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "login":"admin@novatech.com",
                                  "solicitacoes":[
                                    {
                                      "dataSolicitacao":"2026-06-03",
                                      "status":"EM ELABORAÇÃO",
                                      "preco":150.00,
                                      "prazoConclusao":"2026-06-04",
                                      "servicoTiId":1
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCESSO"));

        mockMvc.perform(get("/api/solicitacoes").param("login", "admin@novatech.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCESSO"))
                .andExpect(jsonPath("$.solicitacoes", hasSize(1)));
    }
}
