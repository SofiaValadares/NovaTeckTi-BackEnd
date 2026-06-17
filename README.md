# NovaTech TI — Backend (AV2)

API REST em **Spring Boot** para o projeto acadêmico da empresa **NovaTech TI**. Este backend atende à especificação da AV2: persistência em banco de dados, regras de negócio e endpoints consumidos pelo frontend React (evolução do projeto AV1).

**URL base (local):** `http://localhost:8080`

---

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Banco de dados (H2 em memória)](#banco-de-dados-h2-em-memória)
- [Deploy e persistência dos dados](#deploy-e-persistência-dos-dados)
- [Modelo de dados](#modelo-de-dados)
- [Dados iniciais (seed)](#dados-iniciais-seed)
- [Como executar](#como-executar)
- [Console H2](#console-h2)
- [Padrão das respostas](#padrão-das-respostas)
- [Endpoints da API](#endpoints-da-api)
  - [Autenticação](#1-autenticação)
  - [Troca de senha](#2-troca-de-senha)
  - [Cadastro de cliente](#3-cadastro-de-cliente)
  - [Cadastro de serviço de TI](#4-cadastro-de-serviço-de-ti)
  - [Consulta de serviços de TI](#5-consulta-de-serviços-de-ti)
  - [Leitura de solicitações por usuário](#6-leitura-de-solicitações-por-usuário)
  - [Atualização de solicitações](#7-atualização-de-solicitações)
- [CORS](#cors)
- [Testes](#testes)
- [Integração com o frontend](#integração-com-o-frontend)

---

## Sobre o projeto

O sistema permite que **clientes** se cadastrem, façam login, troquem senha e gerenciem **solicitações de serviços de TI** (carrinho). Os **serviços** podem ser consultados e novos serviços podem ser cadastrados.

Funcionalidades implementadas conforme o enunciado:

| Funcionalidade | Descrição resumida |
|----------------|------------------|
| Autenticação | Busca cliente pelo login e compara a senha informada |
| Troca de senha | Valida login + senha atual; em seguida grava a nova senha |
| Cadastro de cliente | Insere cliente; erro se o login já existir |
| Cadastro de serviço de TI | Insere serviço com ID autoincremento |
| Consulta de serviços | Retorna todos os serviços cadastrados |
| Leitura de solicitações | Lista solicitações de um usuário pelo login |
| Atualização de solicitações | Remove todas as solicitações do usuário e grava a lista recebida |

O campo **login** corresponde ao **e-mail** usado no frontend da AV1 (ex.: `admin@novatech.com`).

---

## Tecnologias

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Web** — controllers REST
- **Spring Data JPA** — acesso ao banco
- **Hibernate** — ORM
- **H2** — banco em memória
- **Bean Validation** — validação dos JSONs de entrada

---

## Estrutura do projeto

```
src/main/java/com/example/novatecktibackend/
├── NovateckTiBackendApplication.java   # Classe principal
├── config/
│   ├── DataInitializer.java          # Carga inicial (admin + serviços)
│   └── WebConfig.java                  # CORS
├── controller/                         # Endpoints REST
├── dto/                                # Objetos de entrada/saída (JSON)
├── entity/                             # Tabelas JPA
├── exception/                          # Tratamento global de erros
├── mapper/                             # Conversão entidade → DTO
├── repository/                         # Repositórios Spring Data
└── service/                            # Regras de negócio
```

---

## Banco de dados (H2 em memória)

O projeto usa **H2 Database** configurado em **modo memória**:

```properties
spring.datasource.url=jdbc:h2:mem:novateckti
```

### O que isso significa na prática

| Aspecto | Comportamento |
|---------|----------------|
| Onde ficam os dados | Na **RAM** do processo Java que executa o backend |
| Criação das tabelas | Automática via JPA (`spring.jpa.hibernate.ddl-auto=update`) |
| Arquivo em disco | **Não** — não há `.db` persistente com a configuração atual |
| Reinício da aplicação | Banco **zerado**; só voltam dados do seed (admin + 5 serviços) |
| Cadastros durante o uso | Existirão **enquanto o servidor estiver rodando** |

### Fluxo quando alguém se cadastra

1. O frontend envia `POST /api/clientes`.
2. O Spring salva o registro na tabela `cliente` do H2 **da instância em execução**.
3. Os dados permanecem disponíveis para login, solicitações etc. **até** haver restart, novo deploy ou o processo encerrar.

Isso é adequado para **trabalho acadêmico / demonstração**, onde perder dados após reinício não é um problema. Para produção com persistência permanente, seria necessário trocar para PostgreSQL, MySQL ou H2 em arquivo.

---

## Deploy e persistência dos dados

Em deploy (Render, Railway, máquina local, etc.):

- A API sobe e funciona normalmente.
- **Não** há banco externo: cada instância mantém seu próprio H2 na memória.
- Após **restart** ou **novo deploy**, cadastros e solicitações criados pelos usuários **somem**.
- Na subida, o `DataInitializer` recria:
  - usuário padrão `admin@novatech.com`
  - os 5 serviços de TI da AV1 (se a tabela estiver vazia).

**Múltiplas instâncias** (dois containers, por exemplo) teriam bancos **independentes** — um cadastro em uma instância não apareceria na outra.

O console web do H2 (`/h2-console`) está habilitado; em ambiente público real convém desativá-lo. Para faculdade/demo costuma ser aceitável.

---

## Modelo de dados

### Tabela `cliente`

| Campo | Tipo | Observação |
|-------|------|------------|
| `id` | Long | Chave primária, autoincremento |
| `nome` | String | Nome completo |
| `login` | String | Único (e-mail do cliente) |
| `senha` | String | Armazenada em texto (requisito do enunciado AV2) |
| `cpf` | String | |
| `data_nascimento` | Date | Formato ISO: `AAAA-MM-DD` |
| `telefone` | String | |
| `estado_civil` | String | |
| `escolaridade` | String | |

### Tabela `servico_ti`

| Campo | Tipo | Observação |
|-------|------|------------|
| `id` | Integer | Chave primária, **autoincremento** |
| `nome` | String | Nome do serviço |
| `preco` | Decimal | Ex.: `150.00` |
| `prazo_dias` | Integer | Prazo em dias |

### Tabela `solicitacao_servico_ti`

| Campo | Tipo | Observação |
|-------|------|------------|
| `id` | Long | Chave primária, autoincremento |
| `data_solicitacao` | Date | Data da solicitação |
| `status` | String | Ex.: `EM ELABORAÇÃO` |
| `preco` | Decimal | Preço no momento da solicitação |
| `prazo_conclusao` | Date | Data prevista de conclusão |
| `cliente_id` | FK | Referência a `cliente` |
| `servico_ti_id` | FK | Referência a `servico_ti` |

---

## Dados iniciais (seed)

Na primeira execução (ou após banco vazio), são inseridos:

### Cliente padrão (testes)

| Campo | Valor |
|-------|-------|
| Login | `admin@novatech.com` |
| Senha | `Admin@123` |
| Nome | Usuário Padrão |

### Serviços de TI (AV1)

| Nome | Preço (R$) | Prazo (dias) |
|------|------------|--------------|
| Suporte Técnico | 150,00 | 1 |
| Desenvolvimento de Sistemas | 2.500,00 | 14 |
| Consultoria em TI | 800,00 | 5 |
| Manutenção Preventiva | 300,00 | 3 |
| Segurança da Informação | 1.200,00 | 7 |

Os IDs dos serviços começam em **1** na ordem acima.

---

## Como executar

### Pré-requisitos

- JDK 17 ou superior
- Maven (ou use o wrapper `./mvnw` incluído no projeto)

### Subir a aplicação

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

### Compilar e testar

```bash
./mvnw test
```

---

## Deploy no Render

O projeto já inclui `render.yaml` e perfil `prod` para publicação na [Render](https://render.com).

### Opção A — Blueprint (recomendado)

1. Envie o código para o GitHub.
2. No Render: **New → Blueprint** e conecte o repositório.
3. O Render lê o `render.yaml` e cria o Web Service automaticamente.

### Opção B — Web Service manual

| Campo | Valor |
|-------|--------|
| **Runtime** | Java |
| **Build Command** | `./mvnw clean package -DskipTests` |
| **Start Command** | `java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/novateck-ti-backend-0.0.1-SNAPSHOT.jar` |
| **Health Check Path** | `/api/health` |

Variáveis de ambiente:

| Variável | Valor |
|----------|--------|
| `JAVA_VERSION` | `17` |
| `SPRING_PROFILES_ACTIVE` | `prod` |

### Após o deploy

- URL pública: `https://novateck-ti-backend.onrender.com` (o nome pode variar).
- Teste: `GET https://SUA-URL.onrender.com/api/health` → `{"status":"UP"}`
- API: `https://SUA-URL.onrender.com/api/...`

No **frontend** (Vercel), altere `src/config/api.ts`:

```typescript
export const API_BASE_URL = 'https://SUA-URL.onrender.com';
```

### Observações

- Plano **free** da Render: o serviço **hiberna** após inatividade; a primeira requisição pode demorar ~30–60s.
- Banco **H2 em memória**: cadastros somem após **restart** ou novo deploy (adequado à AV2).
- CORS já permite qualquer origem (`WebConfig`); o front na Vercel funciona sem mudança no back.

---

## Console H2

Com a aplicação rodando:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:novateckti`
- **Usuário:** `sa`
- **Senha:** *(vazio)*

Permite inspecionar tabelas e registros durante desenvolvimento ou demonstração.

---

## Padrão das respostas

### Status da operação

Campo `status` com valores:

- `SUCESSO` — operação concluída conforme esperado
- `ERRO` — falha de negócio ou validação

Quando `status` é `ERRO`, o campo `mensagem` traz o motivo (em português).

### Respostas de operação simples

Usadas em cadastro, troca de senha e atualização de solicitações:

```json
{
  "status": "SUCESSO",
  "mensagem": null
}
```

```json
{
  "status": "ERRO",
  "mensagem": "Já existe um cliente com este login."
}
```

### Validação de campos obrigatórios

Requisições inválidas (campos vazios, tipos incorretos) retornam HTTP **400** com:

```json
{
  "status": "ERRO",
  "mensagem": "login: must not be blank; senha: must not be blank"
}
```

---

## Endpoints da API

Todas as rotas usam prefixo `/api`. Envie e receba **JSON** com header:

```
Content-Type: application/json
```

---

### 1. Autenticação

Verifica se o login existe e se a senha confere.

| Item | Valor |
|------|-------|
| **Método** | `POST` |
| **URL** | `/api/autenticacao` |
| **Corpo** | `login`, `senha` |

#### Requisição

```json
{
  "login": "admin@novatech.com",
  "senha": "Admin@123"
}
```

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `login` | Sim | E-mail/login do cliente (comparação sem diferenciar maiúsculas/minúsculas) |
| `senha` | Sim | Senha em texto, comparada com a gravada no banco |

#### Resposta — sucesso (credenciais válidas)

HTTP `200`

```json
{
  "status": "SUCESSO",
  "autenticado": true,
  "mensagem": null
}
```

#### Resposta — falha (login ou senha inválidos)

HTTP `200` (a operação foi processada; o login não foi aceito)

```json
{
  "status": "ERRO",
  "autenticado": false,
  "mensagem": "Login ou senha inválidos."
}
```

#### Regra de negócio

- Busca o cliente pelo `login`.
- Se não encontrar ou a senha for diferente → `autenticado: false`.
- Não há token JWT; o frontend deve guardar o login da sessão (como na AV1).

---

### 2. Troca de senha

Autentica com a senha atual e, se válida, atualiza para a nova senha.

| Item | Valor |
|------|-------|
| **Método** | `POST` |
| **URL** | `/api/autenticacao/troca-senha` |
| **Corpo** | `login`, `senhaAtual`, `novaSenha` |

#### Requisição

```json
{
  "login": "admin@novatech.com",
  "senhaAtual": "Admin@123",
  "novaSenha": "NovaSenha@456"
}
```

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `login` | Sim | Login do cliente |
| `senhaAtual` | Sim | Senha atual para validação |
| `novaSenha` | Sim | Nova senha a persistir |

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null
}
```

#### Resposta — autenticação inválida

```json
{
  "status": "ERRO",
  "mensagem": "Autenticação inválida. Não foi possível trocar a senha."
}
```

#### Regra de negócio

1. Executa a mesma validação da autenticação (`login` + `senhaAtual`).
2. Se falhar → retorna erro, **sem** alterar o banco.
3. Se passar → atualiza o campo `senha` do cliente e retorna sucesso.

---

### 3. Cadastro de cliente

Inclui um novo cliente na tabela `cliente`.

| Item | Valor |
|------|-------|
| **Método** | `POST` |
| **URL** | `/api/clientes` |

#### Requisição

```json
{
  "nome": "Maria Silva",
  "login": "maria@email.com",
  "senha": "Senha@123",
  "cpf": "123.456.789-09",
  "dataNascimento": "2000-05-15",
  "telefone": "(11) 98765-4321",
  "estadoCivil": "solteiro(a)",
  "escolaridade": "Ensino superior completo"
}
```

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `nome` | Sim | Nome completo |
| `login` | Sim | E-mail/login único |
| `senha` | Sim | Senha do cliente |
| `cpf` | Sim | CPF formatado ou não |
| `dataNascimento` | Sim | Data ISO `AAAA-MM-DD` |
| `telefone` | Sim | Telefone |
| `estadoCivil` | Sim | Estado civil |
| `escolaridade` | Sim | Escolaridade |

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null
}
```

#### Resposta — login duplicado

```json
{
  "status": "ERRO",
  "mensagem": "Já existe um cliente com este login."
}
```

#### Regra de negócio

- Verifica se já existe cliente com o mesmo `login` (ignorando maiúsculas/minúsculas).
- Se existir → erro; caso contrário → grava todos os campos.

---

### 3.1. Consulta de perfil do cliente

Retorna os dados do cliente para exibição no frontend (sem a senha).

| Item | Valor |
|------|-------|
| **Método** | `GET` |
| **URL** | `/api/clientes?login={login}` |

#### Exemplo

```
GET /api/clientes?login=admin@novatech.com
```

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null,
  "cliente": {
    "id": 1,
    "nome": "Usuário Padrão",
    "login": "admin@novatech.com",
    "cpf": "000.000.000-00",
    "dataNascimento": "1990-01-01",
    "telefone": "(11) 91234-5678",
    "estadoCivil": "solteiro(a)",
    "escolaridade": "2º grau completo"
  }
}
```

#### Resposta — cliente não encontrado

```json
{
  "status": "ERRO",
  "mensagem": "Cliente não encontrado para o login informado.",
  "cliente": null
}
```

---

### 4. Cadastro de serviço de TI

Inclui um novo serviço; o **ID é gerado automaticamente** pelo banco.

| Item | Valor |
|------|-------|
| **Método** | `POST` |
| **URL** | `/api/servicos-ti` |

#### Requisição

```json
{
  "nome": "Backup em Nuvem",
  "preco": 450.00,
  "prazoDias": 2
}
```

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `nome` | Sim | Nome do serviço |
| `preco` | Sim | Valor positivo (decimal) |
| `prazoDias` | Sim | Prazo em dias (inteiro positivo) |

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null
}
```

O ID do novo serviço pode ser obtido na consulta seguinte (`GET /api/servicos-ti`).

---

### 5. Consulta de serviços de TI

Retorna **todos** os serviços cadastrados. Não exige corpo nem parâmetros.

| Item | Valor |
|------|-------|
| **Método** | `GET` |
| **URL** | `/api/servicos-ti` |

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null,
  "servicos": [
    {
      "id": 1,
      "nome": "Suporte Técnico",
      "preco": 150.00,
      "prazoDias": 1
    },
    {
      "id": 2,
      "nome": "Desenvolvimento de Sistemas",
      "preco": 2500.00,
      "prazoDias": 14
    }
  ]
}
```

| Campo em `servicos[]` | Descrição |
|-----------------------|-----------|
| `id` | Identificador do serviço (usado nas solicitações) |
| `nome` | Nome do serviço |
| `preco` | Preço |
| `prazoDias` | Prazo em dias |

Usado pelo frontend para popular o carrinho / dropdown de serviços.

---

### 6. Leitura de solicitações por usuário

Lista todas as solicitações de serviços de TI de um cliente.

| Item | Valor |
|------|-------|
| **Método** | `GET` |
| **URL** | `/api/solicitacoes?login={login}` |

#### Parâmetros de query

| Parâmetro | Obrigatório | Descrição |
|-----------|-------------|-----------|
| `login` | Sim | Login (e-mail) do usuário logado |

#### Exemplo

```
GET /api/solicitacoes?login=admin@novatech.com
```

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null,
  "solicitacoes": [
    {
      "id": 1,
      "dataSolicitacao": "2026-06-03",
      "status": "EM ELABORAÇÃO",
      "preco": 150.00,
      "prazoConclusao": "2026-06-04",
      "servicoTiId": 1,
      "nomeServico": "Suporte Técnico"
    }
  ]
}
```

| Campo em `solicitacoes[]` | Descrição |
|---------------------------|-----------|
| `id` | ID da solicitação no banco |
| `dataSolicitacao` | Data em que a solicitação foi feita |
| `status` | Status atual |
| `preco` | Preço registrado na solicitação |
| `prazoConclusao` | Data prevista de conclusão |
| `servicoTiId` | FK para o serviço |
| `nomeServico` | Nome do serviço (facilita exibição no front) |

#### Resposta — cliente não encontrado

```json
{
  "status": "ERRO",
  "mensagem": "Cliente não encontrado para o login informado.",
  "solicitacoes": []
}
```

Se o usuário ainda não tiver solicitações, retorna `SUCESSO` com `solicitacoes: []`.

---

### 7. Atualização de solicitações

Substitui **todas** as solicitações do usuário pela lista enviada (padrão “carrinho”: apagar tudo e reinserir).

| Item | Valor |
|------|-------|
| **Método** | `PUT` |
| **URL** | `/api/solicitacoes` |

#### Requisição

```json
{
  "login": "admin@novatech.com",
  "solicitacoes": [
    {
      "dataSolicitacao": "2026-06-03",
      "status": "EM ELABORAÇÃO",
      "preco": 150.00,
      "prazoConclusao": "2026-06-04",
      "servicoTiId": 1
    },
    {
      "dataSolicitacao": "2026-06-03",
      "status": "EM ELABORAÇÃO",
      "preco": 800.00,
      "prazoConclusao": "2026-06-08",
      "servicoTiId": 3
    }
  ]
}
```

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `login` | Sim | Login do usuário dono das solicitações |
| `solicitacoes` | Sim | Lista (pode ser vazia `[]` para limpar o carrinho) |

Cada item em `solicitacoes`:

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `dataSolicitacao` | Sim | Data ISO |
| `status` | Sim | Ex.: `EM ELABORAÇÃO` |
| `preco` | Sim | Valor positivo |
| `prazoConclusao` | Sim | Data ISO |
| `servicoTiId` | Sim | ID de um serviço existente |
| `id` | Não | Ignorado na inserção (novos registros recebem ID novo) |
| `nomeServico` | Não | Apenas informativo na leitura; não precisa enviar no PUT |

#### Resposta — sucesso

```json
{
  "status": "SUCESSO",
  "mensagem": null
}
```

#### Resposta — cliente inexistente

```json
{
  "status": "ERRO",
  "mensagem": "Cliente não encontrado para o login informado."
}
```

#### Resposta — serviço inexistente

```json
{
  "status": "ERRO",
  "mensagem": "Serviço de TI não encontrado: id 99"
}
```

#### Regra de negócio (ordem)

1. Localiza o cliente pelo `login`.
2. **Remove** todas as solicitações atuais desse cliente.
3. **Insere** cada item da lista `solicitacoes`.
4. Se `solicitacoes` for `[]`, o usuário fica sem solicitações (carrinho vazio).

---

## CORS

Configurado em `WebConfig` para permitir chamadas do frontend em outra origem:

- Rotas: `/api/**`
- Métodos: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`
- Origens: `*` (qualquer origem)

Útil para React em `localhost:5173` ou deploy separado do front.

---

## Testes

Testes de integração em `NovateckTiApiIntegrationTest` cobrem:

- Autenticação do usuário padrão
- Listagem dos 5 serviços iniciais
- Erro ao cadastrar login duplicado
- Atualização e leitura de solicitações

Execute com:

```bash
./mvnw test
```

---

## Integração com o frontend

Mapeamento sugerido (páginas AV1 / AV2 React):

| Página / ação | Endpoint |
|---------------|----------|
| Login | `POST /api/autenticacao` |
| Trocar senha | `POST /api/autenticacao/troca-senha` |
| Cadastro | `POST /api/clientes` |
| Carrinho — carregar serviços | `GET /api/servicos-ti` |
| Carrinho — carregar solicitações | `GET /api/solicitacoes?login=...` |
| Carrinho — salvar/atualizar lista | `PUT /api/solicitacoes` |
| Cadastro de serviço (nova página AV2) | `POST /api/servicos-ti` |

Configure a URL base da API no frontend (`src/config/api.ts`), por exemplo:
- Local: `http://localhost:8080`
- Render: `https://SUA-URL.onrender.com`

---

## Referência rápida — todos os endpoints

| Método | Endpoint | Função |
|--------|----------|--------|
| `GET` | `/api/health` | Health check (Render / monitoramento) |
| `POST` | `/api/autenticacao` | Autenticar cliente |
| `POST` | `/api/autenticacao/troca-senha` | Trocar senha |
| `POST` | `/api/clientes` | Cadastrar cliente |
| `GET` | `/api/clientes?login=` | Consultar perfil do cliente |
| `POST` | `/api/servicos-ti` | Cadastrar serviço de TI |
| `GET` | `/api/servicos-ti` | Listar serviços de TI |
| `GET` | `/api/solicitacoes?login=` | Listar solicitações do usuário |
| `PUT` | `/api/solicitacoes` | Substituir solicitações do usuário |

---

**Projeto:** NovaTech TI — Backend AV2 · **Spring Boot** · Banco **H2 em memória** (adequado a trabalho acadêmico com dados temporários).
