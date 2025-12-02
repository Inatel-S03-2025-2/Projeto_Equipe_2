package br.inatel.dexmarket;

import br.inatel.dexmarket.controller.TrocaController;
import br.inatel.dexmarket.model.Pokemon;
import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.Treinador;
import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.model.PropostaSimples;
import br.inatel.dexmarket.repository.*;
import br.inatel.dexmarket.service.NotificacaoService;
import br.inatel.dexmarket.service.PropostaService;
import br.inatel.dexmarket.service.TrocaService;
import br.inatel.dexmarket.strategy.ValidacaoTrocaNormal;
import br.inatel.dexmarket.strategy.ValidacaoTrocaRara;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- DEX MARKET BACKEND SIMULATION ---");

        // 1. Inicialização das Repositories (In-Memory)
        TrocaRepository trocaRepository = new TrocaRepositoryImpl();
        PropostaRepository propostaRepository = new PropostaRepositoryImpl();
        NotificacaoRepository notificacaoRepository = new NotificacaoRepositoryImpl();
        PokemonRepository pokemonRepository = new PokemonRepositoryImpl();
        // TreinadorRepository (simulado, pois não existe)

        // 2. Inicialização dos Services (Injeção de Dependência Manual)
        NotificacaoService notificacaoService = new NotificacaoService(notificacaoRepository);
        PropostaService propostaService = new PropostaService(propostaRepository);
        TrocaService trocaService = new TrocaService(trocaRepository, propostaRepository);

        // 3. Inicialização do Controller (Injeção de Dependência Manual)
        TrocaController trocaController = new TrocaController(trocaService, propostaService, notificacaoService);

        // --- Configuração de Dados Mock ---
        // Treinadores
        Treinador treinador1 = new Treinador(1, "Ash Ketchum", "ash@kanto.com");
        Treinador treinador2 = new Treinador(2, "Misty", "misty@kanto.com");
        Treinador treinador3 = new Treinador(3, "Brock", "brock@kanto.com");

        // Pokemons
        Pokemon p1 = new Pokemon(10, "Pikachu", "Eletrico", "url_p1", 1, 1);
        Pokemon p2 = new Pokemon(20, "Charizard", "Fogo/Voador", "url_p2", 3, 2); // Raro
        Pokemon p3 = new Pokemon(30, "Starmie", "Agua/Psiquico", "url_p3", 2, 1);
        Pokemon p4 = new Pokemon(40, "Psyduck", "Agua", "url_p4", 1, 3);
        Pokemon p5 = new Pokemon(50, "Mewtwo", "Psiquico", "url_p5", 5, 2); // Ultra Raro

        // Simulação de Repositório de Treinadores (para buscar o nome)
        TreinadorRepository treinadorRepository = new TreinadorRepository() {
            @Override
            public Treinador findById(int id) {
                if (id == 1) return treinador1;
                if (id == 2) return treinador2;
                if (id == 3) return treinador3;
                return null;
            }
            // Outros métodos não implementados
            @Override
            public Treinador save(Treinador entity) { return entity; }
            @Override
            public List<Treinador> findAll() { return Arrays.asList(treinador1, treinador2, treinador3); }
            @Override
            public boolean delete(int id) { return true; }
        };

        // --- SIMULAÇÃO DE ENDPOINTS ---

        System.out.println("\n--- 1. POST: Criar Nova Troca (Troca Ativa) ---");
        Troca troca1 = new Troca(1, "Ativa"); // Ash (1) oferece Pikachu (10) e deseja Starmie (30)
        troca1.adicionarPokemonOfertado(p1);
        troca1.adicionarPokemonDesejado(p3);
        Troca trocaCriada1 = trocaController.criarTroca(troca1);
        System.out.println("Troca Criada: " + trocaCriada1);

        Troca troca2 = new Troca(2, "Ativa"); // Misty (2) oferece Charizard (20) e deseja Mewtwo (50)
        troca2.adicionarPokemonOfertado(p2);
        troca2.adicionarPokemonDesejado(p5);
        Troca trocaCriada2 = trocaController.criarTroca(troca2);
        System.out.println("Troca Criada: " + trocaCriada2);

        // Demonstração do Padrão Strategy (Troca Rara)
        System.out.println("\n--- Demonstração do Padrão Strategy (Validação Rara) ---");
        trocaService.setEstrategiaValidacao(new ValidacaoTrocaRara());
        Troca troca3 = new Troca(3, "Ativa"); // Brock (3) oferece Psyduck (40) e deseja Pikachu (10)
        troca3.adicionarPokemonOfertado(p4);
        troca3.adicionarPokemonDesejado(p1);
        try {
            Troca trocaCriada3 = trocaService.criarTroca(troca3);
            System.out.println("Troca Criada (Rara): " + trocaCriada3);
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        trocaService.setEstrategiaValidacao(new ValidacaoTrocaNormal()); // Volta para a estratégia normal

        System.out.println("\n--- 2. GET: Listar Trocas Ativas (MarketPlace) ---");
        List<Troca> trocasAtivas = trocaController.listarTrocasAtivas();
        System.out.println("Trocas Ativas no MarketPlace: " + trocasAtivas.size());
        trocasAtivas.forEach(t -> System.out.println("  - " + t));

        System.out.println("\n--- 3. POST: Enviar Proposta (Troca 1) ---");
        PropostaSimples proposta1 = new PropostaSimples(troca1.getIdTroca(), 2); // Misty (2) propõe para Ash (1)
        proposta1.adicionarPokemonOfertado(p4);
        Proposta propostaCriada1 = trocaController.enviarProposta(proposta1);
        System.out.println("Proposta Criada: " + propostaCriada1);

        PropostaSimples proposta2 = new PropostaSimples(troca1.getIdTroca(), 3); // Brock (3) propõe para Ash (1)
        proposta2.adicionarPokemonOfertado(p4);
        Proposta propostaCriada2 = trocaController.enviarProposta(proposta2);
        System.out.println("Proposta Criada: " + propostaCriada2);

        System.out.println("\n--- 4. GET: Listar Propostas da Troca 1 ---");
        List<Proposta> propostasTroca1 = trocaController.listarPropostasPorTroca(troca1.getIdTroca());
        System.out.println("Propostas para Troca #" + troca1.getIdTroca() + ": " + propostasTroca1.size());
        propostasTroca1.forEach(p -> System.out.println("  - " + p));

        System.out.println("\n--- 5. PUT: Aceitar Proposta 1 (Troca 1) ---");
        Proposta propostaAceita = trocaController.aceitarProposta(propostaCriada1.getIdProposta());
        System.out.println("Proposta Aceita: " + propostaAceita);
        Troca trocaConcluida = trocaService.buscarTroca(troca1.getIdTroca());
        System.out.println("Status da Troca 1: " + trocaConcluida.getStatus());

        System.out.println("\n--- 6. GET: Listar Trocas Concluídas do Jogador 1 (Ash) ---");
        List<Troca> trocasConcluidasAsh = trocaController.listarTrocasConcluidasDoJogador(1);
        System.out.println("Trocas Concluídas de Ash: " + trocasConcluidasAsh.size());
        trocasConcluidasAsh.forEach(t -> System.out.println("  - " + t));

        System.out.println("\n--- 7. Notificações (Observer Pattern) ---");
        System.out.println("Notificações de Ash (1):");
        notificacaoService.listarNotificacoesDoJogador(1).forEach(n -> System.out.println("  - " + n));
        System.out.println("Notificações de Misty (2):");
        notificacaoService.listarNotificacoesDoJogador(2).forEach(n -> System.out.println("  - " + n));
        
        System.out.println("\n--- 8. PUT: Enviar Lista de Wishlist (Simulação) ---");
        trocaController.enviarListaWishlist(1, Arrays.asList("Mewtwo", "Lugia"));
    }
}
