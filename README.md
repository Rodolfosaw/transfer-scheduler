# Transfer Scheduler

## Descrição
Sistema de agendamento de transferências financeiras com cálculo dinâmico de taxas.

## Tecnologias
- **Back-end**: Java 11, Spring Boot, Spring Data JPA, H2
- **Front-end**: Vue 3, Vite, Axios

## Como rodar

### Back-end
1. Java 11 instalado.
2. `mvn clean spring-boot:run`
3. API disponível em `http://localhost:8080/api/transfers`.

### Front-end
- Node.js instalado.
- `npm install`
- `npm install -D @vitejs/plugin-vue`  # necessário para o vite.config.js
- `npm run dev`
- Acesse http://localhost:3000 (ou porta indicada pelo Vite).

## Decisões arquiteturais
- **Service Layer**: separação da lógica de negócio (cálculo de taxa).
- **H2 em memória**: mantém simples sem necessidade de configurar BD externo.
- **Vue + Vite**: startup rápido e bundle leve.
