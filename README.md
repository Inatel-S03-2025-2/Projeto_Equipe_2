#  Dex Market

Sistema backend para gerenciamento e troca de Pokémon entre usuários.  
O projeto visa implementar uma arquitetura modular, aplicando boas práticas de programação orientada a objetos e padrões arquiteturais adequados para o domínio de trocas e registros de Pokémon.

---

##  Estrutura do Projeto
   ```bash
dex-market/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/inatel/dexmarket/
│   │   │       ├── controller/      # Camada Controller (MVC) - Coordena as ações
│   │   │       ├── factory/         # Padrão Factory - Criação de Propostas
│   │   │       ├── model/           # Classes de domínio (Pokemon, Troca, Proposta, etc.)
│   │   │       ├── observer/        # Padrão Observer - Interface para notificação
│   │   │       ├── repository/      # Padrão Repository - Abstração do acesso a dados
│   │   │       ├── service/         # Camada Service - Lógica de negócio e regras
│   │   │       └── strategy/        # Padrão Strategy - Validação de Trocas
│   │   └── resources/
│   └── test/
│       └── java/
│           └── br/inatel/dexmarket/
│               └── service/         # Testes Unitários (JUnit)
├── Diagramas/                       # Diagramas UML, documentação de arquitetura e APIs
├── RELATORIO_FINAL.md               # Relatório detalhado do projeto
├── DIFERENCAS_PROJETO.md            # Resumo das diferenças entre o projeto inicial e final
├── README.md
└── pom.xml
```

---

##  Objetivo

O **Dex Market** tem como principal objetivo permitir que usuários:
- Consultem informações sobre Pokémon disponíveis para troca;
- Realizem trocas entre usuários de forma validada e controlada;

---

##  Tecnologias Utilizadas

- **Java** — linguagem principal do projeto
- **Paradigma:** Orientação a Objetos
- **Arquitetura:** MVC (Model-View-Controller)
- **Controle de versão:** Git e GitHub
- **Framework para testes unitários:** JUnit 5

---

## Design Patterns Implementados

### 1. **Padrão Observer** (Comportamental)

**Objetivo:** Implementar um sistema de notificações desacoplado onde mudanças no estado de uma `Troca` notificam automaticamente os `Observers` interessados.

**Componentes:**

- **Interface `Observer`** (`observer/Observer.java`): Define o contrato para observadores.
- **Classe `Troca`** (Subject): Mantém uma lista de `Observers` e notifica quando seu status muda. (ex: de "Ativa" para "Concluída").
- **Classe `NotificacaoService`** (Observer concreto): Implementa `Observer` e reage a mudanças de status, criando um registro de notificação.

**Beneficios:**
- Desacoplamento entre `Troca` e `NotificacaoService`.
- Fácil adicionar novos observadores sem alterar a classe `Troca`
- Notificações automáticas quando eventos ocorrem.

**Exemplo de Uso:**

```java
Troca troca = new Troca(1, "Ativa");
NotificacaoService notificacaoService = new NotificacaoService(notificacaoRepository);

// Anexa o serviço de notificação como observador
troca.attach(notificacaoService);

// Quando o status muda, o observador é notificado automaticamente
troca.setStatus("Concluída"); // Dispara notificacaoService.update(troca)
```

**Benefícios:**

- Desacoplamento entre `Troca` e `NotificacaoService`.
- Fácil adicionar novos observadores sem alterar a classe `Troca`.
- Notificações automáticas quando eventos ocorrem.

---

### 2. **Padrão Factory Method** (Criacional)

**Objetivo:** Centralizar a lógica de criação de diferentes tipos de `Proposta` sem expor as classes concretas ao código cliente `(PropostaService)`.

**Componentes:**

- **Classe Abstrata `Proposta`** (`model/Proposta.java`): Define a estrutura base.
- **Classe `PropostaSimples`** (`model/PropostaSimples.java`): Implementação concreta simples.
- **Classe `PropostaFactory`** (`factory/PropostaFactory.java`): Factory que cria as propostas.

**Exemplo de Uso:**

```java
// Sem Factory (acoplado):
Proposta proposta = new PropostaSimples(1, 2);

// Com Factory (desacoplado):
Proposta proposta = PropostaFactory.criarPropostaSimples(1, 2);
Proposta propostaExtra = PropostaFactory.criarPropostaComItemExtra(1, 2, "Poção Rara", 5);

// Factory genérica:
Proposta proposta = PropostaFactory.criarProposta("simples", 1, 2);
```

**Benefícios:**

- Centraliza a lógica de criação.
- Fácil adicionar novos tipos de propostas.
- Código cliente não conhece as classes concretas.

---

### 3. **Padrão Strategy** (Comportamental)

**Objetivo:** Permitir trocar o algoritmo de validação de trocas em tempo de execução.

**Componentes:**

- **Interface `ValidacaoTrocaStrategy`** (`strategy/ValidacaoTrocaStrategy.java`): Define o contrato.
- **Classe `ValidacaoTrocaNormal`** (`strategy/ValidacaoTrocaNormal.java`): Validação simples.
- **Classe `ValidacaoTrocaRara`** (`strategy/ValidacaoTrocaRara.java`): Validação rigorosa para raros.
- **Classe `TrocaService`**: Utiliza a estratégia para validar trocas.

**Exemplo de Uso:**

```java
TrocaService trocaService = new TrocaService(trocaRepository, propostaRepository);

// Define a estratégia padrão (Normal)
trocaService.setEstrategiaValidacao(new ValidacaoTrocaNormal());
trocaService.criarTroca(trocaNormal);

// Altera para estratégia Rara
trocaService.setEstrategiaValidacao(new ValidacaoTrocaRara());
trocaService.criarTroca(trocaRara);
```

**Benefícios:**

- Algoritmos de validação podem ser trocados em tempo de execução.
- Fácil adicionar novas estratégias de validação.
- Código cliente não precisa conhecer os detalhes de cada estratégia.

---

### 4. **Padrão Repository** (Estrutural)

**Objetivo:** Abstrair a lógica de acesso a dados, permitindo trocar a implementação de persistência sem afetar o código de negócio.

**Componentes:**

- **Interfaces de Repository**: `PokemonRepository`, `TrocaRepository`, `PropostaRepository`, `NotificacaoRepository`.
- **Implementações em Memória**: `PokemonRepositoryImpl`, `TrocaRepositoryImpl`, `PropostaRepositoryImpl`, `NotificacaoRepositoryImpl`.

**Exemplo de Uso:**

```java
// Interface (contrato)
PokemonRepository pokemonRepository = new PokemonRepositoryImpl();

// Usar o repositório
Pokemon pokemon = pokemonRepository.findById(1);
pokemonRepository.save(pokemon);

// Trocar implementação (ex: para banco de dados) sem alterar o código cliente
// PokemonRepository pokemonRepository = new PokemonRepositoryJPA();
```

**Benefícios:**

- Desacoplamento entre lógica de negócio e persistência.
- Fácil trocar de banco de dados (em memória, SQL, NoSQL, etc.).
- Facilita testes unitários com mocks.

---

### 5. **Padrão Injeção de Dependência (DI)** (Estrutural)

**Objetivo:** Fornecer dependências através do construtor, promovendo desacoplamento e testabilidade.

**Implementação:** Todas as classes `Controller` e `Service` recebem suas dependências (outros Services ou Repositories) via construtor. A classe `Main.java` é responsável por instanciar e "injetar" essas dependências manualmente.

**Exemplo:**

```java
// Sem DI (acoplado):
public class TrocaService {
    private TrocaRepository trocaRepository = new TrocaRepositoryImpl();
}

// Com DI (desacoplado - Implementação no projeto):
public class TrocaService {
    private TrocaRepository trocaRepository;
    
    public TrocaService(TrocaRepository trocaRepository, PropostaRepository propostaRepository) {
        this.trocaRepository = trocaRepository;
        this.propostaRepository = propostaRepository;
    }
}
```

---

### **6. Padrão Singleton (Implícito)**

**Objetivo:** Garantir que uma classe tenha apenas uma instância e fornecer um ponto de acesso global a ela.

**Implementação:** O padrão Singleton foi removido das classes Service para favorecer a Injeção de Dependência. No entanto, o conceito é mantido implicitamente nos Repositórios em memória `(*RepositoryImpl)`, onde uma única instância é criada e compartilhada por toda a aplicação para manter o estado dos dados.

---
##  Funcionalidades Implementadas (Endpoints Simulados)

Os seguintes endpoints foram implementados no `TrocaController` e simulados na classe `Main.java`:

| Requisito | Método no `TrocaController` | Descrição |
| :--- | :--- | :--- |
| **GET: Trocas Ativas (MarketPlace)** | `listarTrocasAtivas()` | Retorna a lista de todas as trocas com status "Ativa". |
| **GET: Propostas por Troca** | `listarPropostasPorTroca(idTroca)` | Retorna todas as propostas recebidas para uma troca específica. |
| **GET: Trocas Concluídas por ID** | `listarTrocasConcluidasDoJogador(idJogador)` | Retorna as trocas concluídas em que o jogador foi o ofertante. |
| **POST: Criar Nova Troca** | `criarTroca(troca)` | Cria uma nova troca, aplicando a validação da estratégia configurada. |
| **POST: Enviar Proposta** | `enviarProposta(proposta)` | Cria e salva a proposta, e notifica o ofertante da troca (Padrão Observer). |
| **PUT: Aceitar Proposta** | `aceitarProposta(idProposta)` | Altera o status da proposta para "Aceita" e o da troca para "Concluída". Notifica o proponente. |
| **PUT: Enviar Wishlist** | `enviarListaWishlist(idJogador, wishlist)` | Simulação de atualização da lista de desejos do jogador. |

---
##  Padrões e Boas Práticas

- Separação clara entre **camadas** (Model, Repository, Service, Controller)
- Aplicação dos **princípios SOLID**
- Uso de **tratamento de exceções** com `try-catch` e mensagens descritivas
- Classes modeladas de forma coesa e com responsabilidade única
- Encapsulamento e uso correto de **getters/setters**
- **Documentação de código**

---

##  Instalação e Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/dex-market.git

2. Abra o projeto com sua IDE favorita:  
   (ex.: IntelliJ IDEA, Eclipse, VS Code...)


3. Compile e execute a classe `Main` localizada em `src/main/`

---

##  Próximos Passos

- Adicionar testes unitários (JUnit)

- Escrever documentação técnica e diagrama UML atualizado

---

##  Equipe

Projeto desenvolvido como parte das atividades da disciplina de Arquitetura de Software. Atualmente, o repositório ainda está em estruturação.

Integrantes:

- Eduardo Dias Andrade
- Guilherme da Silva Fernandes Almeida
- João Pedro da Silva Escobar de Oliveira
- João Paulo Fonseca Bernardo
- Matheus Rangel de Lima
- Vinícius Santos Araújo

---

##  Licença

Este projeto é de uso acadêmico e não possui licença comercial no momento.

---

##  Observações

A estrutura e o código estão sendo ajustados conforme as decisões arquiteturais são tomadas.

O objetivo atual é garantir uma base sólida para o backend antes da integração com frontend ou banco de dados.
