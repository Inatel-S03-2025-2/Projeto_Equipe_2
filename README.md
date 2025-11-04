#  Dex Market

Sistema backend para gerenciamento e troca de Pokémon entre usuários.  
O projeto visa implementar uma arquitetura modular, aplicando boas práticas de programação orientada a objetos e padrões arquiteturais adequados para o domínio de trocas e registros de Pokémon.

---

##  Estrutura do Projeto
   ```bash
   dex-market/
│
├── src/
│   ├── model/           # Classes de domínio (Pokemon, Treinador, Troca, etc.)
│   ├── repository/      # Classes responsáveis pelo armazenamento e busca de dados
│   ├── service/         # Regras de negócio (ex: validação de trocas)
│   ├── controller/      # Camada de controle entre o usuário e o sistema
│   ├── utils/           # Funções auxiliares (validação, logs, etc.)
│   └── main/            # Classe principal para inicialização e testes
│
├── tests/               # Testes unitários e de integração
│
├── docs/                # Diagramas UML, documentação de arquitetura e APIs
│
├── README.md
└── .gitignore
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

*(Outras tecnologias poderão ser adicionadas conforme o projeto evoluir.)*

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

- Criar repositórios com armazenamento em memória

- Implementar regras de negócio (service)

- Desenvolver controlador inicial

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

O projeto ainda está em estágio inicial.
A estrutura e o código serão ajustados conforme as decisões arquiteturais forem tomadas.
O objetivo atual é garantir uma base sólida para o backend antes da integração com frontend ou banco de dados.
