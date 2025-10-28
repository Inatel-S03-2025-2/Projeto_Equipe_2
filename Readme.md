---

# 📘 `README.pt-BR.md` (Português — versão completa)

```markdown
# DexMarket — Plataforma de Troca de Pokémons (API + Frontend)

[![Status](https://img.shields.io/badge/status-prototype-yellow)](#)
[![Backend](https://img.shields.io/badge/backend-Spring%20Boot-orange)](#)
[![Frontend](https://img.shields.io/badge/frontend-Vue3-blue)](#)
[![DB](https://img.shields.io/badge/db-PostgreSQL-blueviolet)](#)
[![License: MIT](https://img.shields.io/badge/license-MIT-green)](#)

---

## Índice
- [Visão Geral](#visão-geral)
- [Stack Tecnológica](#stack-tecnológica)
- [Status do Projeto](#status-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Estrutura do Repositório](#estrutura-do-repositório)
- [Configuração e Execução Local (Dev)](#configuração-e-execução-local-dev)
  - [Variáveis de ambiente](#variáveis-de-ambiente)
  - [Docker Compose (infra)](#docker-compose-infra)
  - [Rodando Backend (Spring Boot)](#rodando-backend-spring-boot)
  - [Rodando Frontend (Vue 3 + Vite)](#rodando-frontend-vue-3--vite)
- [Banco de Dados, Migrations e Seeds](#banco-de-dados-migrations-e-seeds)
- [API — Endpoints Principais (Resumo)](#api--endpoints-principais-resumo)
- [WebSocket / Tempo Real (STOMP)](#websocket--tempo-real-stomp)
- [Testes](#testes)
- [CI / CD (GitHub Actions)](#ci--cd-github-actions)
- [Deploy sugerido](#deploy-sugerido)
- [Documentação (OpenAPI / Swagger)](#documentação-openapi--swagger)
- [Geração de clientes / tipagens para frontend](#geração-de-clientes--tipagens-para-frontend)
- [Contribuição](#contribuição)
- [Roadmap curto](#roadmap-curto)
- [Manutenção e Contatos](#manutenção-e-contatos)
- [Licença](#licença)
- [Apêndice — comandos úteis](#apêndice---comandos-úteis)

---

## Visão Geral
DexMarket é uma **plataforma protótipo** para troca de Pokémons entre jogadores com **notificações em tempo real**. Objetivo: demonstrar um fluxo completo (abrir troca, propor, aceitar) com qualidade (testes, CI/CD) e facilidade de deploy.

---

## Stack Tecnológica
**Backend**
- Java 17+ + Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (migrations)
- Spring WebSocket (STOMP + SockJS)
- Springdoc OpenAPI (Swagger)
- JWT (autenticação mínima / mock)
- Testes: JUnit 5, Mockito, Testcontainers

**Frontend**
- Vue 3 + Vite + TypeScript
- Tailwind CSS
- Pinia (estado global)
- Axios (REST) + @stomp/stompjs / sockjs-client (WebSocket)
- Testes unitários: Vitest + Vue Testing Library
- E2E: Cypress (sugestão)

**Infra / DevOps**
- Monorepo com `/backend` e `/frontend`
- Docker & Docker Compose
- CI: GitHub Actions
- Deploy sugerido: Railway/Render (backend) + Vercel/Netlify (frontend)

---

## Status do Projeto
- **Status atual**: protótipo / desenvolvimento.
- Objetivos já definidos: CRUD de trocas e propostas, WebSocket de notificações, testes automatizados, pipeline CI.

---

## Pré-requisitos
- Git
- Docker & Docker Compose
- Java 17+ (local)
- Maven (ou usar wrapper `./mvnw`)
- Node 18+
- pnpm / npm / yarn

---

## Estrutura do Repositório (sugestão)
```

/ (monorepo)
├─ backend/
│  ├─ src/main/java/...
│  ├─ src/main/resources/
│  │   └─ db/migration/    # Flyway migrations
│  ├─ src/test/...
│  ├─ Dockerfile
│  └─ pom.xml
├─ frontend/
│  ├─ src/
│  ├─ cypress/
│  ├─ Dockerfile
│  └─ package.json
├─ docker-compose.yml
└─ .github/workflows/

````

---

## Configuração e Execução Local (Dev)

### Variáveis de ambiente (exemplo `.env`)
Crie um `.env` na raiz (ou em cada serviço conforme sua preferência):

```ini
# Banco
POSTGRES_USER=dex
POSTGRES_PASSWORD=dexpass
POSTGRES_DB=dexmarket
POSTGRES_PORT=5432

# Backend
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/dexmarket
SPRING_DATASOURCE_USERNAME=dex
SPRING_DATASOURCE_PASSWORD=dexpass
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# JWT (exemplo)
JWT_SECRET=troca-super-secreta
JWT_EXPIRATION_MS=3600000
````

> **Nota**: Em desenvolvimento local com Docker Compose, o host do DB no backend será `db` (nome do serviço). Em execução sem Docker, use `localhost`.

---

### Docker Compose (infra mínima)

Exemplo rápido de `docker-compose.yml` para desenvolvimento:

```yaml
version: "3.8"
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_USER: dex
      POSTGRES_PASSWORD: dexpass
      POSTGRES_DB: dexmarket
    ports:
      - "5432:5432"
    volumes:
      - db-data:/var/lib/postgresql/data
  pgadmin:
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@dex.local
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "8081:80"
volumes:
  db-data:
```

Subir infra:

```bash
docker compose up -d
```

---

### Rodando Backend (dev)

No diretório `backend/`:

```bash
# usando wrapper do maven (recomendado)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# ou
./mvnw clean package -DskipTests
java -jar target/*.jar
```

Configurações úteis: `application-dev.yml` com datasource apontando para o DB do docker.

---

### Rodando Frontend (dev)

No diretório `frontend/`:

```bash
pnpm install
pnpm dev
```

App dev por padrão: `http://localhost:5173` (Vite).

---

## Banco de Dados, Migrations e Seeds

* Migrations: usar Flyway (scripts em `src/main/resources/db/migration`).
* Seeds: scripts SQL em `src/main/resources/db/seeds/` ou comandos `data-loader` no profile `dev`.
* Comando Flyway (Maven):

```bash
./mvnw flyway:migrate
```

---

## API — Endpoints Principais (Resumo)

Base: `http://localhost:8080/api`

### Jogadores

* `POST /api/players` — criar jogador
* `GET /api/players/:id` — obter jogador

### Pokémons

* `POST /api/pokemons` — cadastrar pokemon
* `GET /api/pokemons` — listar pokémons

### Trocas

* `POST /api/trades` — abrir troca (oferecer pokemon)
* `GET /api/trades` — listar trocas
* `GET /api/trades/:id` — detalhes

### Propostas

* `POST /api/trades/:id/proposals` — propor troca
* `GET /api/trades/:id/proposals` — listar propostas
* `POST /api/proposals/:id/accept` — aceitar proposta
* `POST /api/proposals/:id/reject` — recusar proposta

### Notificações

* `GET /api/notifications` — listar notificações do jogador
* `POST /api/notifications/:id/read` — marcar como lida

> Payloads e contratos completos são gerados via OpenAPI/Swagger.

---

## WebSocket / Tempo Real (STOMP)

* Endpoint WebSocket: `ws://<HOST>:8080/ws` (com SockJS)
* Tópicos:

  * `/topic/trades/{tradeId}` — atualizações da trade
  * `/user/queue/notifications` — notificações privadas por usuário

Fluxo:

1. Cliente conecta via STOMP/SockJS.
2. Subscrição `/user/queue/notifications`.
3. Backend envia mensagens privadas com `SimpMessagingTemplate.convertToUser(...)`.

Cliente JS: usar `@stomp/stompjs` + `sockjs-client`.

---

## Testes

**Backend**

* Unitários: JUnit5 + Mockito
* Integração: Testcontainers (levanta Postgres real no CI)
* Executar:

```bash
./mvnw test
```

**Frontend**

* Unitários: Vitest

```bash
pnpm test
```

* E2E: Cypress

```bash
pnpm cypress open
```

---

## CI / CD (GitHub Actions)

Sugestão de workflow:

* `pull_request`: executar lint + unit tests (backend e frontend) → bloquear merge se falhar.
* `main` branch: build & test → deploy automático:

  * Backend → Railway / Render (build jar e deploy)
  * Frontend → Vercel / Netlify

Dica: separar workflows `ci-backend.yml` e `ci-frontend.yml` para execução paralela.

---

## Deploy sugerido

* **Backend**: Railway / Render / Heroku (variáveis de ambiente: DB_URL, JWT secret).
* **Frontend**: Vercel / Netlify (deploy direto do GitHub).
* Habilitar HTTPS e variáveis secretas.

---

## Documentação (OpenAPI / Swagger)

* Springdoc OpenAPI gera `/v3/api-docs` e Swagger UI em `/swagger-ui.html`.
* Exportar `openapi.json` para gerar clientes TS.

---

## Geração de clientes / tipagens para frontend

Exemplo com OpenAPI Generator (gera cliente Axios + tipagens TS):

```bash
openapi-generator-cli generate -i http://localhost:8080/v3/api-docs -g typescript-axios -o frontend/src/api
```

---

## Contribuição

* Abra issues para bugs / features.
* Branch por tarefa: `feature/<nome>`, PR com descrição e checklist.
* Tests obrigatórios para mudanças significativas.
* Siga `CODE_OF_CONDUCT.md` e `CONTRIBUTING.md`.

---

## Roadmap curto

1. CRUD completo para Trades & Proposals com validações e testes.
2. Integração WebSocket + UI de notificações.
3. Testes E2E cobrindo fluxo crítico.
4. Deploy automático em staging e produção.
5. Melhorias: autorização granular, auditoria, internacionalização.

---
Abra uma issue no repo para entrar em contato com os mantenedores.

---

## Licença

MIT — ver arquivo `LICENSE`.

---

## Apêndice — comandos úteis

```bash
# Infra
docker compose up -d

# Backend (dev)
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Backend (build)
./mvnw clean package -DskipTests
java -jar target/*.jar

# Flyway migrate
./mvnw flyway:migrate

# Frontend (dev)
cd frontend
pnpm install
pnpm dev

# Frontend (build)
pnpm build

# Tests backend
./mvnw test

# Tests frontend
pnpm test

# Cypress E2E
pnpm cypress open
```

---

> Se quiser, eu posso gerar arquivos complementares prontos para o repositório: `docker-compose.yml`, `application-dev.yml` (exemplo), template `.github/workflows/ci.yml` (backend + frontend), ou o `openapi.json` stub para iniciar o frontend. Basta pedir.

````

---

# 📘 `README.en-US.md` (English — complete version)

```markdown
# DexMarket — Pokémon Trading Platform (API + Frontend)

[![Status](https://img.shields.io/badge/status-prototype-yellow)](#)
[![Backend](https://img.shields.io/badge/backend-Spring%20Boot-orange)](#)
[![Frontend](https://img.shields.io/badge/frontend-Vue3-blue)](#)
[![DB](https://img.shields.io/badge/db-PostgreSQL-blueviolet)](#)
[![License: MIT](https://img.shields.io/badge/license-MIT-green)](#)

---

## Table of Contents
- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Status](#project-status)
- [Prerequisites](#prerequisites)
- [Repository Structure](#repository-structure)
- [Local Setup & Development](#local-setup--development)
  - [Environment variables](#environment-variables)
  - [Docker Compose (infra)](#docker-compose-infra)
  - [Running Backend (Spring Boot)](#running-backend-spring-boot)
  - [Running Frontend (Vue 3 + Vite)](#running-frontend-vue-3--vite)
- [Database, Migrations & Seeds](#database-migrations--seeds)
- [API — Main Endpoints (Summary)](#api--main-endpoints-summary)
- [WebSocket / Real-time (STOMP)](#websocket--real-time-stomp)
- [Testing](#testing)
- [CI / CD (GitHub Actions)](#ci--cd-github-actions)
- [Suggested Deploy](#suggested-deploy)
- [Documentation (OpenAPI / Swagger)](#documentation-openapi--swagger)
- [Generating clients / frontend typings](#generating-clients--frontend-typings)
- [Contributing](#contributing)
- [Short Roadmap](#short-roadmap)
- [Maintenance & Contacts](#maintenance--contacts)
- [License](#license)
- [Appendix — Useful commands](#appendix---useful-commands)

---

## Overview
DexMarket is a prototype platform for trading Pokémon between players, with **real-time notifications**. It demonstrates a full flow (open trade → propose → accept) with automated tests, CI, and deploy guidance.

---

## Tech Stack

**Backend**
- Java 17+ + Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (migrations)
- Spring WebSocket (STOMP + SockJS)
- Springdoc OpenAPI (Swagger)
- JWT (simple auth/mock)
- Testing: JUnit 5, Mockito, Testcontainers

**Frontend**
- Vue 3 + Vite + TypeScript
- Tailwind CSS
- Pinia (state management)
- Axios (REST) + @stomp/stompjs / sockjs-client (WebSocket)
- Unit tests: Vitest + Vue Testing Library
- E2E: Cypress (recommended)

**Infra / DevOps**
- Monorepo with `/backend` and `/frontend`
- Docker & Docker Compose
- CI: GitHub Actions
- Suggested Deploy: Railway/Render (backend) + Vercel/Netlify (frontend)

---

## Project Status
- **Current**: prototype / in development
- Core goals: trades & proposals CRUD, WebSocket notifications, automated tests, CI pipeline.

---

## Prerequisites
- Git
- Docker & Docker Compose
- Java 17+
- Maven (or use `./mvnw`)
- Node 18+
- pnpm / npm / yarn

---

## Repository Structure (recommended)
````

/ (monorepo)
├─ backend/
│  ├─ src/main/java/...
│  ├─ src/main/resources/
│  │   └─ db/migration/
│  ├─ src/test/...
│  ├─ Dockerfile
│  └─ pom.xml
├─ frontend/
│  ├─ src/
│  ├─ cypress/
│  ├─ Dockerfile
│  └─ package.json
├─ docker-compose.yml
└─ .github/workflows/

````

---

## Local Setup & Development

### Environment variables (example `.env`)
```ini
# Database
POSTGRES_USER=dex
POSTGRES_PASSWORD=dexpass
POSTGRES_DB=dexmarket
POSTGRES_PORT=5432

# Backend
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/dexmarket
SPRING_DATASOURCE_USERNAME=dex
SPRING_DATASOURCE_PASSWORD=dexpass
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# JWT example
JWT_SECRET=super-secret-exchange
JWT_EXPIRATION_MS=3600000
````

> Note: when using Docker Compose, the DB host for backend is `db`. For local non-docker runs use `localhost`.

---

### Docker Compose (minimal infra)

`docker-compose.yml` example:

```yaml
version: "3.8"
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_USER: dex
      POSTGRES_PASSWORD: dexpass
      POSTGRES_DB: dexmarket
    ports:
      - "5432:5432"
    volumes:
      - db-data:/var/lib/postgresql/data
  pgadmin:
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@dex.local
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "8081:80"
volumes:
  db-data:
```

Start infra:

```bash
docker compose up -d
```

---

### Running Backend (dev)

From `backend/`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
# or build & run
./mvnw clean package -DskipTests
java -jar target/*.jar
```

---

### Running Frontend (dev)

From `frontend/`:

```bash
pnpm install
pnpm dev
# default: http://localhost:5173
```

---

## Database, Migrations & Seeds

* Use Flyway for migrations (`src/main/resources/db/migration`).
* Seed data can be stored under `src/main/resources/db/seeds` and loaded in dev.
* Flyway via Maven:

```bash
./mvnw flyway:migrate
```

---

## API — Main Endpoints (Summary)

Base: `http://localhost:8080/api`

**Players**

* `POST /api/players` — create player
* `GET /api/players/:id` — get player

**Pokemons**

* `POST /api/pokemons` — create pokemon
* `GET /api/pokemons` — list

**Trades**

* `POST /api/trades` — open trade
* `GET /api/trades` — list trades
* `GET /api/trades/:id` — trade details

**Proposals**

* `POST /api/trades/:id/proposals` — propose to trade
* `GET /api/trades/:id/proposals` — list proposals
* `POST /api/proposals/:id/accept` — accept proposal
* `POST /api/proposals/:id/reject` — reject

**Notifications**

* `GET /api/notifications` — list player's notifications
* `POST /api/notifications/:id/read` — mark as read

> Full request/response contracts are generated by OpenAPI/Swagger.

---

## WebSocket / Real-time (STOMP)

* WebSocket endpoint: `ws://<HOST>:8080/ws` (SockJS)
* Topics:

  * `/topic/trades/{tradeId}` — trade updates
  * `/user/queue/notifications` — private notifications to user

Typical flow:

1. Client connects via STOMP/SockJS.
2. Subscribe to `/user/queue/notifications`.
3. Backend sends messages to specific users with `SimpMessagingTemplate.convertToUser(...)`.

Client libs: `@stomp/stompjs` + `sockjs-client`.

---

## Testing

**Backend**

* Unit: `./mvnw test` (JUnit5 + Mockito)
* Integration: Testcontainers (start a real Postgres for tests)

**Frontend**

* Unit: `pnpm test` (Vitest)
* E2E: Cypress:

```bash
pnpm cypress open
```

---

## CI / CD (GitHub Actions)

Recommended flows:

* `pull_request` check: lint + unit tests for backend & frontend.
* `main` branch: build & test → deploy to hosting providers (Railway/Vercel).
* Separate workflows: `ci-backend.yml`, `ci-frontend.yml`.

---

## Suggested Deploy

* Backend: Railway / Render / Heroku (set env variables: DB URL, JWT secret).
* Frontend: Vercel / Netlify.
* Configure HTTPS and secrets management.

---

## Documentation (OpenAPI / Swagger)

* Springdoc provides `/v3/api-docs` and Swagger UI (`/swagger-ui.html`).
* Export `openapi.json` to generate clients for frontend.

---

## Generating clients / frontend typings

Using OpenAPI Generator to produce a TypeScript Axios client:

```bash
openapi-generator-cli generate -i http://localhost:8080/v3/api-docs -g typescript-axios -o frontend/src/api
```

---

## Contributing

* Open issues for bugs / features.
* Branch per feature: `feature/<name>`.
* Tests required for significant changes.
* Follow `CODE_OF_CONDUCT.md` and `CONTRIBUTING.md`.

---

## Short Roadmap

1. Complete CRUD for Trades & Proposals with validations and tests.
2. WebSocket integration and notifications UI.
3. Full E2E tests covering core flow.
4. Automated deployments (staging & production).
5. Enhancements: fine-grained auth, audit, i18n.

---
Open an issue in the repo to contact maintainers.

---

## License

MIT — see `LICENSE`.

---

## Appendix — Useful commands

```bash
# Infra
docker compose up -d

# Backend (dev)
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Backend build
./mvnw clean package -DskipTests
java -jar target/*.jar

# Flyway
./mvnw flyway:migrate

# Frontend (dev)
cd frontend
pnpm install
pnpm dev

# Frontend build
pnpm build

# Backend tests
./mvnw test

# Frontend tests
pnpm test

# Cypress E2E
