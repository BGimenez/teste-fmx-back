# Teste FMX - Backend

Essa API foi construida como forma de prova técnica para empresa FMX.
Foi utilizado uma estrutura de aplicação simples, pensando em uma arquitetura monolítica.

Nela, foi utilizado o framework Spring com a linguagem de programação Java (JDK 17). Para o banco de dados, foi utilizado o PostgreSQL para a API e H2 para os testes da aplicação .

Para tornar a execução do banco de dados mais simples, sem a necessidade de instalação do mesmo e de um SGBD (Sistema de Gerenciamento de Banco de dados), foi utilizado a plataforma de serviço de virtualização Docker.

## Pré-requisitos para a execução da API
Para executar a API é necessário ter os seguintes programas instalados na máquina:
* Java JDK 17+
* Docker 20.10+
* Gradle (Esse é opcional, caso esteja executando em uma IDE como IntelliJ ou Eclipse)

## Executando o projeto
Para a execução da API, dever-se-á seguir os seguintes passos:

1. Abrir o código fonte na IDE de sua preferência;
2. Realizar os imports das dependências do projeto;
3. Abrir o terminal, na pasta raiz do projeto, e executar o seguinte comando (é necessário ter o Docker e o Docker-compose instalados na máquina):
    
    `docker-compose -f stack.yml up`
    
    Obs.: Esse processo poderá demorar um pouco caso não tenha as imagens baixadas na máquina.
    
    
4. No navegador (de preferencia o Chrome), acessar a URL `http://localhost:8077/` e seguir as seguintes etapas (esse processo é somente para a primeira execução):
      1. No campo **System** selecionar **PostgreSQL**;
      2. No campo **Username** informar **postgres**;
      3. No campo **Password** informar **postgres**;
      4. Clicar no botão **Login**;
      5. Clicar no bbotão **Criar base de dados**;
      6. No campo, informar **dbg** e clicar em salvar;
      7. Na lateral esquerda, clicar em **Comando SQL**;
      8. Na caixa de texto, inserir o comando abaixo e clicar em **Executar**:
      
      
          `insert into permissao (descricao, padrao) values ('Acesso ao Sistema', true);`
          
          
5. Na IDE, executar a aplicação.


Obs.: Caso queira executar a aplicação pelo terminal, deve serguir os seguintes passos:

1. Navegar, no terminal, até a pasta raiz da aplicação;
2. Executar o comando para gerar os arquivos de build da aplicação:

    `./gradlew build`
    
3. Logo após, executar o comando para executar a aplicação:

    `./gradlew run`
