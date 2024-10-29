# Sistema de Estoque

Este é um sistema de gerenciamento de estoque desenvolvido em Java utilizando Spring Boot, Thymeleaf e Bootstrap. O sistema permite cadastrar produtos, estoques e associar produtos aos estoques, além de listar e excluir esses registros.

## Funcionalidades

- Cadastro de Produtos
- Cadastro de Estoques
- Cadastro de Produtos no Estoque
- Listagem de Produtos
- Listagem de Estoques
- Listagem de Produtos em Estoque
- Exclusão de Produtos 
- Exclusão de estoques

## Tecnologias Utilizadas

- Java
- Spring Boot
- Thymeleaf
- Bootstrap
- JPA/Hibernate
- MySQL

## Estrutura do Projeto

- `src/main/java/com/estoque/estoque`: Contém os arquivos de código fonte Java.
  - `controllers`: Controladores Spring MVC.
  - `model`: Entidades JPA.
  - `repository`: Repositórios JPA.
  - `service`: Serviços de negócios.
- `src/main/resources/templates`: Contém os arquivos de template Thymeleaf.
  - `fragments`: Fragmentos reutilizáveis de templates.
  - `estoque`: Templates específicos para funcionalidades de estoque.
- `src/main/resources/static`: Contém arquivos estáticos como CSS e JavaScript.

## Configuração do Banco de Dados

O projeto utiliza MySQL como banco de dados. Certifique-se de configurar as propriedades de conexão no arquivo `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/estoque
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



Executando o Projeto

git clone https://github.com/Luizbernardi/ProjetoEstoque.git

cd ProjetoEstoque

/mvnw spring-boot:run

http://localhost:8080

![Index](https://github.com/user-attachments/assets/1f863921-62f5-460b-a610-01bbe1127ed5)


