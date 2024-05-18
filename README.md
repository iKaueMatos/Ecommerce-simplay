# Ecommerce simplay

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/usuario/repositorio/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)

## Sumário

- [📄 Descrição](##descrição)
- [🛠️ Tecnologias Utilizadas](#tecnologias-utilizadas)
- [⚙️ Configuração do Ambiente](#configuração-do-ambiente)
- [🚀 Execução](#execução)
- [🐳 Ambiente de Desenvolvimento com Docker](#ambiente-de-desenvolvimento-com-docker)
- [🌐 Ambiente de Produção com Docker](##ambiente-de-produção-com-docker)
- [🧪 Testes](#testes)
- [🤝 Contribuições](#contribuições)
- [📏 Padrões de Código](#padrões-de-código)
- [📚 Documentação](##documentação)
- [📜 Licença](##licença)
- [📞 Contato](##contato)

## 📄 Descrição
olá devs tudo bem ? com vocês hoje estou divulgando aqui um projeto que tinha sido criado por mim e pelo colega a algum tempo e não estavamos utilizando nenhum padrão de projeto, contudo decidimos refatorar e utilizar padrões de projeto e também atualizar o projeto em geral para conhecimento que foi adquirido ao longo desse periodo de estudos.


## 🛠️ Tecnologias Utilizadas

- **Linguagens de Programação:**
  - Java (Spring Boot para construção de microserviços robustos e escaláveis)
- **Banco de Dados:**
  - MySQL (para dados relacionais)
- **Infraestrutura:**
  - Docker (contenedorização de aplicações)

## ⚙️ Configuração do Ambiente

### Pré-requisitos

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/)
- JDK 11+ (para serviços Spring Boot)

### Passo a Passo

1. Clone o repositório:
    ```bash
      https://github.com/iKaueMatos/Nova-painel-backEnd
    ```

2. Configure as variáveis de ambiente criando um arquivo `application-dev.properties` na raiz do projeto e defina as variáveis necessárias:
    ```env
    DATABASE_URL=mysql://usuario:senha@localhost:5432/meubanco
    ```

3. Instale as dependências:

    - Para serviços Java com Spring Boot:
      ```bash
      ./mvnw install
      ```

4. Inicie os contêineres Docker:
    ```bash
    docker-compose up -d
    ```

## 🚀 Execução

Para iniciar um microserviço específico, siga o exemplo abaixo:

1. Navegue até o diretório do serviço desejado:
    - Para o serviço de autenticação (Spring Boot):
      ```bash
      cd services/auth_service
      ```

    - Para o serviço de relatórios (Python):
      ```bash
      cd services/report_service
      ```

2. Inicie o serviço:
    - Para serviços Java com Spring Boot:
      ```bash
      ./mvnw spring-boot:run
      ```

3. Acesse o serviço através da URL:
    ```
    http://localhost:8000
    ```

## 🐳 Ambiente de Desenvolvimento com Docker

### Configuração

1. Certifique-se de que o Docker e o Docker Compose estão instalados em seu sistema.

2. Clone o repositório e navegue até a pasta do projeto, matendo o projeto docker-dev junto com os microserviços:
    ```bash
    git clone git@github.com:Nova-Software-Organization/docker-dev.git
    cd docker-dev
    ```

3. Crie e configure o arquivo `.env` com as variáveis de ambiente necessárias (veja a seção [Configuração do Ambiente](##configuração-do-ambiente)).

4. Suba os contêineres Docker para o ambiente de desenvolvimento:
    ```bash
    docker-compose -f docker-compose.dev.yml up -d
    ```

5. Para visualizar os logs dos contêineres em tempo real:
    ```bash
    docker-compose logs -f
    ```

### Desenvolvimento

Durante o desenvolvimento, você pode acessar os serviços individuais através dos contêineres Docker. Utilize volumes do Docker para sincronizar o código local com os contêineres para um desenvolvimento mais rápido.

Para acessar um contêiner específico e realizar comandos diretamente nele:
```bash
docker-compose exec <nome_do_servico> /bin/sh
```

## 🌐 Ambiente de Produção com Docker | Somente para ADM

### Configuração

1. Certifique-se de que o Docker e o Docker Compose estão instalados no servidor de produção.

2. Clone o repositório no servidor de produção:
    ```bash
    git clone git@github.com:Nova-Software-Organization/docker-dev.git
    cd repositorio
    ```

3. Configure as variáveis de ambiente necessárias em um arquivo `.env.prod`:
    ```env
    DATABASE_URL=postgresql://usuario:senha@db_producao:5432/meubanco
    ```

4. Suba os contêineres Docker para o ambiente de produção:
    ```bash
    docker-compose -f docker-compose.prod.yml up -d
    ```

### Monitoramento e Logs

Para monitorar os serviços e visualizar logs:
```bash
docker-compose -f docker-compose.prod.yml logs -f
```

### Atualizações e Deploy Contínuo

Para aplicar atualizações e realizar deploy contínuo:
1. Puxe as últimas mudanças do repositório:
    ```bash
    git pull origin main
    ```

2. Recrie os contêineres com as novas mudanças:
    ```bash
    docker-compose -f docker-compose.prod.yml up -d --build
    ```

## 🧪 Testes

Execute os testes automatizados utilizando os seguintes comandos:

- Para serviços Java com Spring Boot:
  ```bash
  ./mvnw test
  ```

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor, siga os passos abaixo para contribuir:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`).
4. Push para a branch (`git push origin feature/nova-feature`

## 📜 Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 📞 Contato
Para dúvidas e suporte, entre em contato com ikauedeveloper@gmail.com
