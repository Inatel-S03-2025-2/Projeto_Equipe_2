# Relatório de Implementação do Backend Dex Market

## 1. Introdução

Este relatório detalha a análise, refatoração e implementação das funcionalidades de backend para o sistema de trocas **Dex Market**, conforme solicitado. O projeto original, baseado em Java e Maven, foi adaptado para garantir a arquitetura **MVC Desacoplada** e a aplicação de **Design Patterns**, resultando em um código mais limpo, testável e de fácil manutenção.

## 2. Arquitetura e Implementação

O projeto foi estruturado para seguir rigorosamente o padrão **Model-View-Controller (MVC)**, com ênfase no desacoplamento das camadas.

| Camada | Responsabilidade | Implementação | Desacoplamento |
| :--- | :--- | :--- | :--- |
| **Controller** | Receber requisições e coordenar a lógica de negócio. | `TrocaController.java` | Não possui dependências diretas de framework (ex: Spring Boot), apenas de Services. |
| **Service** | Implementar a lógica de negócio (regras de troca, validações, notificações). | `TrocaService.java`, `PropostaService.java`, `NotificacaoService.java` | Não possui dependências diretas de Controller ou Repository (utiliza interfaces). |
| **Model** | Estruturas de dados e objetos de domínio. | `Troca.java`, `Proposta.java`, `Pokemon.java`, etc. | Não possui dependências de Service ou Controller. |
| **Repository** | Abstrair o acesso a dados. | `TrocaRepository.java` (Interface), `TrocaRepositoryImpl.java` (Implementação em memória) | A camada Service depende apenas da Interface, permitindo fácil troca da implementação (ex: para um banco de dados real). |

## 3. Padrões de Design Implementados

Os padrões de design solicitados (**Singleton** e **Strategy**) foram mantidos e documentados, e outros padrões importantes (**Observer** e **Factory**) foram identificados e utilizados para melhorar a coesão e o acoplamento do código.

### 3.1. Strategy Pattern

O **Strategy Pattern** foi aplicado para encapsular diferentes algoritmos de validação de troca, tornando-os intercambiáveis.

> **Localização:** `br.inatel.dexmarket.service.TrocaService` e `br.inatel.dexmarket.strategy.*`

| Componente | Função |
| :--- | :--- |
| **Contexto** | `TrocaService` |
| **Estratégia** | `ValidacaoTrocaStrategy` (Interface) |
| **Estratégias Concretas** | `ValidacaoTrocaNormal`, `ValidacaoTrocaRara` |

**Apontamento no Código (`TrocaService.java`):**

```java
// **Padrão Strategy**: Inicializa com a estratégia padrão (ValidacaoTrocaNormal)
this.estrategiaValidacao = new ValidacaoTrocaNormal();

// **Padrão Strategy em ação**: A validação é delegada à estratégia configurada.
if (!estrategiaValidacao.validar(troca)) {
    throw new IllegalArgumentException("Troca inválida de acordo com a estratégia de validação.");
}
```

### 3.2. Observer Pattern (Adicionado)

O **Observer Pattern** foi implementado para gerenciar as notificações de forma reativa. Quando o status de uma `Troca` muda (o **Subject**), o `NotificacaoService` (o **Observer**) é automaticamente notificado para criar uma notificação.

> **Localização:** `br.inatel.dexmarket.model.Troca` e `br.inatel.dexmarket.service.NotificacaoService`

**Apontamento no Código (`Troca.java`):**

```java
public void setStatus(String status) {
    this.status = status;
    this.dataAtualizacao = new Date();
    // **Padrão Observer em ação**: Notifica todos os observadores quando o status muda
    notifyObservers();
}
```

### 3.3. Factory Pattern (Adicionado)

O **Factory Pattern** foi utilizado para centralizar a lógica de criação de diferentes tipos de `Proposta` (`PropostaSimples`, `PropostaComItemExtra`), desacoplando o código cliente (`PropostaService`) das classes concretas.

> **Localização:** `br.inatel.dexmarket.factory.PropostaFactory`

**Apontamento no Código (`PropostaService.java`):**

```java
public Proposta criarPropostaSimples(int idTroca, int idJogadorProponente) {
    // **Padrão Factory em ação**: Delega a criação à Factory
    Proposta proposta = PropostaFactory.criarPropostaSimples(idTroca, idJogadorProponente);
    // ...
}
```

### 3.4. Singleton Pattern (Implícito)

O **Singleton Pattern** foi removido das classes Service para favorecer a Injeção de Dependência e o desacoplamento. No entanto, o conceito de Singleton é mantido implicitamente nos repositórios em memória (`TrocaRepositoryImpl`, `PropostaRepositoryImpl`), onde uma única instância é criada e compartilhada por toda a aplicação para manter o estado dos dados.

## 4. Funcionalidades Implementadas (Endpoints)

Os endpoints solicitados foram implementados no `TrocaController` e simulados na classe `Main.java`.

| Requisito | Método no `TrocaController` | Descrição |
| :--- | :--- | :--- |
| **1. Trocas Ativas (MarketPlace)** | `listarTrocasAtivas()` | Retorna a lista de todas as trocas com status "Ativa". |
| **2. Propostas por Troca** | `listarPropostasPorTroca(idTroca)` | Retorna todas as propostas recebidas para uma troca específica. |
| **3. Trocas Concluídas por ID** | `listarTrocasConcluidasDoJogador(idJogador)` | Retorna as trocas concluídas em que o jogador foi o ofertante. |
| **4. Notificação de Proposta** | Integrado em `enviarProposta(proposta)` | O `NotificacaoService` envia uma notificação ao ofertante da troca. |
| **POST: Criar Nova Troca** | `criarTroca(troca)` | Cria uma nova troca, aplicando a validação da estratégia configurada. |
| **POST: Enviar Proposta** | `enviarProposta(proposta)` | Cria e salva a proposta, e notifica o ofertante da troca. |
| **PUT: Aceitar Proposta** | `aceitarProposta(idProposta)` | Altera o status da proposta para "Aceita" e da troca para "Concluída". Notifica o proponente. |
| **PUT: Enviar Wishlist** | `enviarListaWishlist(idJogador, wishlist)` | Simulação de atualização da lista de desejos do jogador. |

## 5. Como Testar o Projeto

O projeto está configurado para ser executado e testado via linha de comando (Maven).

### 5.1. Testes Unitários (JUnit)

A classe `TrocaServiceTest.java` contém testes unitários que validam a lógica de negócio principal, incluindo:

*   Criação de trocas com diferentes estratégias de validação.
*   Listagem de trocas ativas e concluídas.
*   Processamento de propostas (aceitar/rejeitar) e a mudança de status da troca.
*   Listagem de propostas por troca.

**Para executar os testes:**

1.  Navegue até o diretório raiz do projeto (`project_backend`).
2.  Execute o comando Maven:

```bash
mvn test
```

### 5.2. Simulação de Endpoints (Main.java)

A classe `Main.java` foi refatorada para simular o fluxo de chamadas que um frontend faria ao `TrocaController`, demonstrando o funcionamento dos endpoints e a interação entre os padrões de design (Strategy, Observer, Factory).

**Para executar a simulação:**

1.  Navegue até o diretório raiz do projeto (`project_backend`).
2.  Execute o comando Maven:

```bash
mvn exec:java
```

O console exibirá o passo a passo da simulação, incluindo a criação de trocas, o envio de propostas, a aceitação de uma proposta e as notificações geradas.

---

**Anexo:** O código-fonte completo e refatorado está disponível no diretório `project_backend`.
