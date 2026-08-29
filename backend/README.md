# Full-Stack Developer Portfolio Ecosystem

[![Java Version](https://shields.io)](https://java.net)
[![Spring Boot](https://shields.io)](https://spring.io)
[![Database](https://shields.io)](https://postgresql.org)
[![Testing](https://shields.io)](https://junit.org)

Este ecossistema robusto e de alta performance foi desenvolvido para gerenciar e expor de forma unificada as informações profissionais, histórico acadêmico, competências técnicas e portfólio de projetos do desenvolvedor. O sistema conta com uma arquitetura modular segregada, camadas rígidas de segurança contra abusos e **100% de cobertura de testes automatizados** nas regras críticas de negócio e persistência.

---

## 🏗️ Arquitetura do Backend (Spring Boot)

O backend foi estruturado seguindo o padrão de **Arquitetura em Camadas** e os princípios do **SOLID**, garantindo baixo acoplamento e alta coesão:

```text
📂 com.portfolio
 ┣ 📂 config           # Configurações globais (CORS, Swagger/OpenAPI, MVC)
 ┣ 📂 controller       # Controladores REST divididos em rotas públicas e administrativas
 ┣ 📂 dto              # Objetos de Transferência de Dados (Requests e Responses)
 ┣ 📂 entity           # Entidades relacionais mapeadas com o Hibernate/JPA
 ┣ 📂 exception        # Classes de exceções customizadas e o Global Exception Handler
 ┣ 📂 mapper           # Conversores automatizados de Entidades ↔ DTOs
 ┣ 📂 repository       # Camada de Persistência com queries JPQL otimizadas
 ┗ 📂 security         # Filtros de Rate Limiting por IP e Autenticação por Chave
```

---

### 🛡️ Funcionalidades de Segurança e Infraestrutura
* **Filtro Administrativo de Autenticação (`AdminAuthFilter`):** Bloqueia acessos não autorizados a rotas administrativas através da validação do cabeçalho estrito `X-Admin-Key`.
* **Filtro de Rate Limiting (`RateLimitFilter`):** Mecanismo concorrente em memória (`ConcurrentHashMap`) que barra ataques de spam no formulário de contato público, limitando a 5 requisições por minuto por IP.
* **Resolvedor Customizado de Erros (`SecurityErrorResolver`):** Intercepta e formata respostas de falhas de autenticação em payloads JSON padronizados com codificação UTF-8.
* **Motor de Armazenamento Seguro (`FileStorageService`):** Sistema nativo de upload em disco rígido com validação estrita de tamanho máximo (5MB), renomeação automatizada via UUID e lista branca de extensões permitidas (`jpg`, `jpeg`, `png`, `webp`, `gif`).

---

## 🧪 Estratégia e Cobertura de Testes Automatizados

O sistema foi blindado utilizando **JUnit 5** e **Mockito** em todas as esferas críticas da aplicação:

1. **Testes Web Sliced (Camada de Controle - `MockMvc`):**
    * Validação rigorosa dos códigos de status HTTP (200, 201, 204, 400, 413, 429, 500).
    * Verificação de payloads em arrays e objetos estruturados através de expressões `JsonPath`.
    * Teste de comportamento do `GlobalExceptionHandler` simulando falhas de restrição.

2. **Testes Unitários (Camada de Negócios - `Mockito`):**
    * Validação de fluxos lógicos e aplicação de regras de negócio como o **Soft Delete** (`active = false`).
    * Captura detalhada de mutações de estado nas entidades via `ArgumentCaptor`.

3. **Testes de Integração de Dados (Camada de Persistência - `DataJpaTest`):**
    * Execução de queries JPQL customizadas em banco Sandbox relacional.
    * Validação de ordenações duplas combinadas (ex: `sortOrder ASC, startedAt DESC`) e restrições relacionais de integridade (`NOT NULL`).

---

## ⚙️ Pré-requisitos e Configuração Local

### 1. Banco de Dados (PostgreSQL)
Certifique-se de ter uma instância do PostgreSQL activa na sua máquina. Crie um banco de dados chamado `portfolio`:
```sql
CREATE DATABASE portfolio;
```

### 2. Variáveis de Ambiente e Configurações (`application.yml`)
Configure as suas propriedades de acesso ou defina variáveis no sistema:
* `SPRING_DATASOURCE_URL`: `jdbc:postgresql://localhost:5432/portfolio`
* `SPRING_DATASOURCE_USERNAME`: *Seu usuário do banco*
* `SPRING_DATASOURCE_PASSWORD`: *Sua senha do banco*
* `APP_ADMIN_SECRET_KEY`: *Sua chave secreta para as rotas administrativas*

---

## 🚀 Como Executar o Projeto

### Rodar os Testes Automatizados
Para certificar que toda a malha de testes está perfeitamente verde e validada, execute:
```powershell
./mvnw test
```

### Iniciar o Servidor Backend
Inicie a aplicação utilizando o Maven wrapper nativo do projeto:
```powershell
./mvnw spring-boot:run
```

Assim que o console exibir a inicialização do Tomcat na porta `8080`, as migrações automáticas do **Flyway** serão aplicadas ao banco de dados PostgreSQL.

### Interagir com a API (Swagger UI)
Com o servidor rodando, abra o seu navegador e explore os endpoints públicos e administrativos na interface gráfica interativa do OpenAPI/Swagger:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## 🎨 Próxima Fase: Frontend em React
O ecossistema está preparado para receber e alimentar de forma assíncrona a interface visual em React que será hospedada no diretório `/frontend`. As origens de CORS globais já se encontram liberadas para o consumo de `http://localhost:3000`.
