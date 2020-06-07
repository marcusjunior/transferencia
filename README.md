# API de Transferencia

API de transferencia de saldo entre clientes

## Tecnologias utilizadas

* Java 11
* Spring Boot 2.3.0
* Spring Data
* H2 Database
* Lombok
* Swagger
* JUnit
* Docker

## Instalação

Para realizar a instalação manual projeto, executar o comando:

```mvn clean install```

Para realizar a instalação via docker, executar o comando:

```mvn spring-boot:build-image```

Não é necessário configuração adicional de banco dados para executar o projeto.

## Utilização

Para executar a aplicação manualmente, executar a classe: 

```TransferenciaApplication.java```

Para executar a aplicação via container docker, executar o comando: 

```docker run -dp 8080:8080  transferencia:0.0.1-SNAPSHOT```

Após o servidor ser iniciado, a API está disponível para testes via swagger na url:

[http://localhost:8080/swagger-ui.html]

Os endpoints disponíveis na API são:

* `POST - /v1/clientes` - cria um novo cliente

* `GET - /v1/clientes` - busca um cliente pelo numero da conta

* `GET - /v1/clientes/listarTodos` - lista todos os clientes criados

* `GET - /v1/transferencia` - busca todas as transferências relacionadas a um conta

* `POST - /v1/transferencia` - realiza transferência de saldo entre dois clientes

Para executar os testes unitários e integrados, executar o comando:

`mvn clean test`

## Autor

Marcus Júnior
