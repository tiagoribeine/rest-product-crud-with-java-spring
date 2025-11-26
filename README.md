# 🚀 Product Management API

Uma API REST completa para gerenciamento de produtos desenvolvida em Spring Boot com arquitetura em camadas.

## 📋 Sobre o Projeto

API RESTful para operações CRUD (Create, Read, Update, Delete) de produtos, seguindo as melhores práticas de desenvolvimento com Spring Boot.

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.12**
- **Spring Data JPA**
- **MySQL** (Produção) / **H2** (Desenvolvimento)
- **Maven**
- **Postman** (Testes de API)

## 📚 Funcionalidades

- **CRUD Completo** de produtos
- **Criação em lote** de produtos
- **Validações** de dados
- **Tratamento de erros** personalizado
- **Arquitetura em camadas** (Controller, Service, Repository)

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 21
- MySQL
- Maven 3.6+

### Configuração do Banco de Dados

1. **Crie o banco de dados no MySQL:**
```sql
CREATE DATABASE produto_db;

Antes de executar a aplicação é necessário configurar as variáveis de ambiente, para isso execute no Powershell
<pre><code><b>Powershell:</b>
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "Your-MySQL-Password"
</code></pre>
