# 🍽️ Food Service Manager – Clean Architecture

## 🧭 1. Visão Geral do Projeto

O **Food Service Manager** é um sistema de gestão para restaurantes, permitindo o cadastro de **usuários**, **restaurantes**, **itens de cardápio** e **tipos de usuários**.

O objetivo é **unificar a administração de múltiplos estabelecimentos** e oferecer uma base para **expansão futura**, incluindo funcionalidades como **pedidos** e **avaliações de clientes**.

📦 **Repositório GitHub:** [Cauana/food-service-manager-clean-arch](https://github.com/Cauana/food-service-manager-clean-arch)

---

## ⚙️ 2. Infraestrutura do Projeto

### 🗂️ Estrutura de Pastas

| Caminho | Descrição |
|----------|------------|
| `src/main/java/` | Código-fonte principal (Java) |
| `src/main/resources/` | Arquivos de configuração, scripts SQL, propriedades |
| `src/test/java/` | Testes automatizados |
| `target/` | Artefatos gerados pelo Maven (build) |
| `.env` | Variáveis de ambiente para banco de dados |
| `docker-compose.yml` | Orquestração dos containers (aplicação e banco de dados) |
| `Dockerfile` | Build da imagem da aplicação |
| `pom.xml` | Gerenciamento de dependências Maven |
| `collection tech challenge clean arq` | Collection com testes de integração (Insomnia) |

---

### 🗄️ Banco de Dados

- **Banco:** PostgreSQL rodando em container Docker  
- **Inicialização automática:** via `data.sql` em `src/main/resources/data.sql`  
- **Dados iniciais:** tipos de usuário “Dono de restaurante” e “Cliente”  
- **Configuração:** via variáveis de ambiente (`.env`) e `application.properties`

#### Acesso ao banco no terminal:
```bash
psql -h localhost -U root -d food-service-manager
```

---

### 💻 Backend

- **Linguagem:** Java 21  
- **Framework:** Spring Boot  
- **Persistência:** Spring Data JPA  
- **Utilitários:** Lombok  
- **Documentação:** Swagger/OpenAPI (acesso via `/swagger-ui.html`)

#### Execução do Projeto
```bash
docker-compose up -d
```

---

## 🧱 3. Clean Architecture no Food Service Manager

### 🧩 Camadas Principais

#### **Domain (Domínio)**
- Local: `domain/`  
- Contém as entidades de negócio (ex: `Usuario`, `Restaurante`, `ItemCardapio`)
- Não depende de frameworks ou tecnologias externas.

#### **Use Case (Casos de Uso)**
- Local: `usecase/`  
- Implementa as regras de negócio e orquestra entidades.  
- Exemplo: `CadastrarUsuarioUseCaseImpl`, `CadastrarItemCardapioUseCaseImpl`  
- Depende apenas do **Domínio** e dos **Gateways**.

#### **Gateway (Portas)**
- Local: `gateway/`  
- Define interfaces de comunicação com sistemas externos (banco, APIs).  
- Permite **inversão de dependência**: os casos de uso dependem de interfaces, não de implementações.

#### **Infra (Infraestrutura)**
- Local: `infra/`  
- Implementa os gateways (ex: repositórios, mapeadores, controllers REST).  
- Depende das camadas internas, **nunca o contrário**.

---

### 🔁 Fluxo de Dependências

```
Controllers (REST)
       ↓
Use Cases
       ↓
Domain
       ↓
Gateways (interfaces)
       ↓
Infra (implementações)
```

As dependências **sempre apontam para dentro**, conforme a **regra da Clean Architecture**.

---

### 🌟 Vantagens da Arquitetura

- **Testabilidade:** casos de uso e domínio podem ser testados isoladamente.  
- **Manutenção:** mudanças em frameworks ou banco não afetam regras de negócio.  
- **Escalabilidade:** fácil adicionar novas interfaces (ex: outro banco ou API externa).

---

### 💡 Exemplos no Projeto

- O controller REST (`UsuarioApiController`) chama um caso de uso (`CadastrarUsuarioUseCaseImpl`), que manipula entidades do domínio e usa gateways para persistência.  
- Os gateways são **interfaces**, implementadas na camada **infra** (`UsuarioRepository`).  
- **DTOs** transportam dados entre camadas sem acoplar o domínio à infraestrutura.

---

## 🌐 4. Endpoints da API – Food Service Manager

### 🧍 Usuário (`usuario-api-controller`)

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| GET | `/usuarios/{id}` | Retorna um usuário específico pelo ID |
| PUT | `/usuarios/{id}` | Atualiza os dados de um usuário existente |
| DELETE | `/usuarios/{id}` | Remove um usuário do sistema |
| GET | `/usuarios` | Lista todos os usuários cadastrados |
| POST | `/usuarios` | Cria um novo usuário |

---

### 👥 Tipo de Usuário (`tipo-usuario-api-controller`)

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| GET | `/tipo-usuario/{id}` | Retorna um tipo de usuário pelo ID |
| PUT | `/tipo-usuario/{id}` | Atualiza um tipo de usuário existente |
| DELETE | `/tipo-usuario/{id}` | Exclui um tipo de usuário |
| GET | `/tipo-usuario` | Lista todos os tipos de usuário |
| POST | `/tipo-usuario` | Cria um novo tipo de usuário |

---

### 🏠 Restaurante (`restaurante-api-controller`)

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| GET | `/restaurante/{id}` | Retorna um restaurante específico pelo ID |
| PUT | `/restaurante/{id}` | Atualiza os dados de um restaurante |
| DELETE | `/restaurante/{id}` | Remove um restaurante |
| GET | `/restaurante` | Lista todos os restaurantes cadastrados |
| POST | `/restaurante` | Cadastra um novo restaurante |

---

### 🍝 Item de Cardápio (`item-cardapio-api-controller`)

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| GET | `/item-cardapio/{id}` | Retorna um item do cardápio pelo ID |
| GET | `/item-cardapio/restaurante/{id}` | Retorna todos itens do cardápio de um restaurante pelo seu ID |
| PUT | `/item-cardapio/{id}` | Atualiza um item do cardápio existente |
| DELETE | `/item-cardapio/{id}` | Remove um item do cardápio |
| GET | `/item-cardapio` | Lista todos os itens do cardápio |
| POST | `/item-cardapio` | Cria um novo item de cardápio |

---

### 🔐 Autenticação (`login-api-controller`)

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| POST | `/auth/login` | Realiza o login e retorna token/autenticação |
| POST | `/auth/logout` | Faz logout e invalida o token de sessão |
