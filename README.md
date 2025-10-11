# Food Service Manager

Tech Challenge da pós-graduação em Arquitetura e Desenvolvimento Java (ADJ) da PÓS TECH da FIAP. 
Ele tem como objetivo a criação de um sistema de gestão para um grupo de restaurantes.

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [API Endpoints](#api-endpoints)
- [Documentação da API](#documentação-da-api)

## Sobre o Projeto

O Food Service Manager é um sistema de gestão para restaurantes, permitindo o cadastro de usuários, restaurantes, itens de cardápio e tipos de usuários.
O objetivo é unificar a administração de múltiplos estabelecimentos e oferecer uma base para expansão futura com pedidos e avaliações de clientes.

## Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot** - Framework web
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **Lombok** - Redução de boilerplate

### DevOps
- **Docker & Docker Compose** - Containerização
- **Maven** - Gerenciamento de dependências
- **Swagger/OpenAPI** - Documentação da API


## Configuração do Ambiente

### Pré-requisitos

- **Java 21+**
- **Docker & Docker Compose**
- **Git**

## Como Executar

### Docker Compose (Recomendado)

```bash
# Clone o repositório


# Execute com Docker Compose
docker-compose up -d

# Verifique os logs
docker-compose logs -f app
```

### Acesso

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **PostgreSQL**: localhost:5432

para rodar no terminal:
psql -h localhost -U root -d food-service-manager   



## Documentação da API

### Documentação completa

arquivo: documentacao_completa.md

A documentação completa da API está disponível via **Swagger UI**:

- **URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Collection do Postman

arquivo: Collection Tech Challenge Clean Arch.json



## Equipe

- **Desenvolvedora**: Cauana Dias Costa
- **Desenvolvedor**: Ivan Domingos Moreira
- **Desenvolvedor**: Jociel Alves de Jesus
- **Desenvolvedor**: Wellington Feijó da Costa
