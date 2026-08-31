# 🌐 Portfolio Frontend — Single Page Application (SPA)

Este repositório compreende a camada de interface (Frontend) do **Portfolio Corporativo**, uma Single Page Application (SPA) moderna, responsiva, estritamente tipada e de alta performance. O ecossistema foi projetado para expor as seções públicas a visitantes e fornecer um painel administrativo completo e seguro para o gerenciamento dinâmico de dados em tempo real.

---

## 🛠️ Stack Tecnológica

O ecossistema do frontend foi construído utilizando as ferramentas mais robustas e modernas do desenvolvimento web:

*   **Runtime & Bundler:** [Vite](https://vite.dev) — Garante builds instantâneos e Hot Module Replacement (HMR) extremamente veloz.
*   **Linguagem:** [TypeScript](https://typescriptlang.org) — Injeção de tipagem estrita e contratos de dados seguros em toda a aplicação.
*   **Framework Core:** [React 19](https://react.dev) — Utilização de componentes baseados em funções e gerenciamento nativo de estados.
*   **Estilização:** [Tailwind CSS v4](https://tailwindcss.com) — Design responsivo utilitário integrado com variáveis utilitárias de tema dinâmico.
*   **Gerenciamento de Estado de Rede:** [@tanstack/react-query](https://tanstack.com) (TanStack Query) — Cache assíncrono, mutações em lote e sincronização automatizada com o Spring Boot.
*   **Formulários:** [React Hook Form](https://react-hook-form.com) — Manipulação otimizada de inputs e estados de validação sem re-renderizações desnecessárias.
*   **Roteamento:** [React Router DOM](https://reactrouter.com) — Controle de rotas públicas e estruturas hierárquicas de painéis protegidos.
*   **Ícones:** [Lucide React](https://lucide.dev) — Pacote de ícones vetoriais de alta qualidade e escalabilidade.

---

## 📐 Arquitetura de Pastas e Componentes

A estrutura foi projetada seguindo os padrões de **Clean Architecture** e **Modularização**, separando as responsabilidades de visualização, lógica de rede e regras visuais:

```text
frontend/
├── public/                 # Arquivos estáticos públicos (favicon, ícones)
├── src/
│   ├── assets/             # Imagens e recursos gráficos (hero, svgs)
│   ├── components/         # Componentes React divididos por escopo
│   │   ├── admin/          # Painel administrativo (tabelas, modais, formulários de submódulos)
│   │   ├── icons/          # Ícones personalizados baseados em tecnologias (TechIcon)
│   │   ├── layout/         # Layouts estruturais (AdminLayout, PublicLayout)
│   │   ├── navigation/     # Elementos de navegação global (Navbar, Footer)
│   │   ├── public/         # Seções públicas (Home, About, Projects, Contact)
│   │   └── ui/             # Componentes reutilizáveis de interface (Badge, Spinner, ErrorBoundary)
│   ├── context/            # Contextos globais e controle de estados compartilhados (ThemeContext)
│   ├── hooks/              # Custom hooks assíncronos (useProfile, useProjects, useSkills, etc.)
│   ├── pages/              # Páginas principais que envelopam os escopos do sistema
│   ├── routes/             # Configuração e proteção da árvore de rotas públicas e administrativas
│   ├── services/           # Camada de comunicação HTTP pura com os barramentos da API
│   ├── styles/             # Definição de estilos globais e variáveis de cores CSS (globals.css)
│   ├── types/              # Contratos de interfaces TypeScript e tipagens centrais do sistema
│   ├── utils/              # Funções utilitárias auxiliares e resolvedores de links (api.ts)
│   ├── App.tsx             # Componente raiz e injeção de provedores globais
│   └── main.tsx            # Ponto de entrada oficial da aplicação React
├── package.json            # Scripts de automação e gerenciamento de dependências
├── tsconfig.json           # Configurações estritas do compilador TypeScript
└── vite.config.ts          # Configurações do empacotador Vite e regras de proxy local
```

---

## ⚡ Fluxos Técnicos Implementados

### 1. Separação de Responsabilidades (Fatiamento de Componentes)
Para atender às exigências de qualidade de código do **ESLint** (`@typescript-eslint/no-explicit-any` e `@typescript-eslint/no-unused-vars`), todas as telas administrativas foram fatiadas em subcomponentes isolados:
*   **`Table`**: Responsável exclusiva pela renderização pura do grid de listagem.
*   **`FormModal`**: Gerenciador isolado de formulários do `react-hook-form` e tratamento seguro de payloads antes do envio ao banco.
*   **`Page Shell`**: Componente de página mestre encarregado de injetar as queries e mutações assíncronas do TanStack Query.

### 2. Integração e Alinhamento de Rede com o Context-Path
O frontend opera em perfeita sincronia com o `context-path: /api` configurado no backend Spring Boot. O arquivo `vite.config.ts` utiliza um barramento de proxy inteligente que intercepta chamadas de dados e mídias de forma centralizada:
*   **Dados:** As requisições HTTP são disparadas via Axios apontando para a base `/api`.
*   **Mídias e Uploads:** O utilitário `resolveAssetUrl` normatiza caminhos relativos gerados pelo banco de dados para `/api/uploads/**`, assegurando que o navegador consuma os avatares e capturas de tela multiplataforma direto do sistema de arquivos do servidor.

### 3. Mecânicas Avançadas de Interface e UX
*   **Scroll Restorer**: Injeção do componente global `ScrollHandler` para forçar o reset da janela do navegador para o topo em transições de rotas.
*   **Error Boundaries**: Telas de contingência acopladas à malha do roteador para capturar falhas isoladas de renderização sem derrubar a aplicação.
*   **Modo Escuro Dinâmico**: Provedor `ThemeContext` chaveando classes CSS utilitárias em tempo real.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
Certifique-se de possuir o **Node.js** instalado na sua máquina (versão 18 ou superior recomendada).

### 1. Instalar as Dependências
Navegue até a pasta do frontend e instale os pacotes necessários:
```powershell
cd frontend
npm install
```

### 2. Configurar as Variáveis de Ambiente
Crie um arquivo `.env` ou `.env.local` na raiz do diretório do frontend se desejar alterar o endereço de produção. Em ambiente de desenvolvimento local, o sistema adota automaticamente o padrão do proxy do Vite para apontar para o servidor Java.

### 3. Rodar a Aplicação em Modo de Desenvolvimento
Inicie o servidor local do Vite:
```powershell
npm run dev
```
O console exibirá o endereço de escuta local. Por padrão, a aplicação estará disponível em:
👉 **`http://localhost:3000/`**

### 4. Endereços Úteis no Navegador
*   **Seção Pública Principal:** `http://localhost:3000/`
*   **Página Sobre Mim:** `http://localhost:3000/about`
*   **Tela de Autenticação Administrativa:** `http://localhost:3000/admin/login`
*   **Dashboard Gerencial Interno:** `http://localhost:3000/admin`
