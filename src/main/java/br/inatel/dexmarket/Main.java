package br.inatel.dexmarket;

import br.inatel.dexmarket.model.*;
import br.inatel.dexmarket.controller.TrocaController;
import br.inatel.dexmarket.service.TrocaService;
import br.inatel.dexmarket.service.PropostaService;
import br.inatel.dexmarket.service.NotificacaoService;
import br.inatel.dexmarket.repository.*;
import br.inatel.dexmarket.strategy.ValidacaoTrocaNormal;
import br.inatel.dexmarket.strategy.ValidacaoTrocaRara;

import java.util.List;

/**
 * Classe Main - Ponto de Entrada da Aplicação
 * Demonstra o uso dos padrões de design implementados:
 * - Observer (Notificações)
 * - Factory Method (Criação de Propostas)
 * - Strategy (Validação de Trocas)
 * - Repository (Acesso a Dados)
 * - MVC Desacoplado (Controller, Service, Model)
 * - Injeção de Dependência
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("DEX MARKET - Sistema de Trocas Pokémon");
        System.out.println("========================================\n");

        // ========== INICIALIZAÇÃO DOS REPOSITÓRIOS ==========
        System.out.println(">>> Inicializando repositórios...");
        PokemonRepository pokemonRepository = new PokemonRepositoryImpl();
        TrocaRepository trocaRepository = new TrocaRepositoryImpl();
        PropostaRepository propostaRepository = new PropostaRepositoryImpl();
        NotificacaoRepository notificacaoRepository = new NotificacaoRepositoryImpl();

        // ========== INICIALIZAÇÃO DOS SERVICES ==========
        System.out.println(">>> Inicializando services...");
        TrocaService trocaService = new TrocaService(trocaRepository, propostaRepository);
        PropostaService propostaService = new PropostaService(propostaRepository);
        NotificacaoService notificacaoService = new NotificacaoService(notificacaoRepository);

        // ========== INICIALIZAÇÃO DO CONTROLLER ==========
        System.out.println(">>> Inicializando controller...");
        TrocaController trocaController = new TrocaController(trocaService, propostaService, notificacaoService);

        System.out.println("\n========================================\n");

        // ========== CRIAÇÃO DE POKÉMONS DE EXEMPLO ==========
        System.out.println(">>> Criando Pokémons de exemplo...\n");
        Pokemon pikachu = new Pokemon(1, "Pikachu", "Elétrico", "url_pikachu.png", 3, 1);
        Pokemon charizard = new Pokemon(2, "Charizard", "Fogo", "url_charizard.png", 4, 2);
        Pokemon dragonite = new Pokemon(3, "Dragonite", "Dragão", "url_dragonite.png", 5, 1);
        Pokemon mewtwo = new Pokemon(4, "Mewtwo", "Psíquico", "url_mewtwo.png", 5, 3);

        pokemonRepository.save(pikachu);
        pokemonRepository.save(charizard);
        pokemonRepository.save(dragonite);
        pokemonRepository.save(mewtwo);

        System.out.println("\n========================================\n");

        // ========== DEMONSTRAÇÃO: PADRÃO STRATEGY ==========
        System.out.println(">>> DEMONSTRAÇÃO: Padrão Strategy (Validação de Trocas)\n");

        // Criando uma troca normal
        System.out.println("--- Troca Normal ---");
        Troca trocaNormal = new Troca(1, "Ativa");
        trocaNormal.adicionarPokemonOfertado(pikachu);
        trocaNormal.adicionarPokemonDesejado(charizard);

        // Usando a estratégia padrão (Normal)
        trocaService.setEstrategiaValidacao(new ValidacaoTrocaNormal());
        Troca trocaCriada = trocaController.criarTroca(trocaNormal);

        System.out.println("\n--- Troca Rara ---");
        Troca trocaRara = new Troca(2, "Ativa");
        trocaRara.adicionarPokemonOfertado(dragonite);
        trocaRara.adicionarPokemonDesejado(mewtwo);

        // Alterando para a estratégia Rara
        trocaService.setEstrategiaValidacao(new ValidacaoTrocaRara());
        Troca trocaRaraCriada = trocaController.criarTroca(trocaRara);

        System.out.println("\n========================================\n");

        // ========== DEMONSTRAÇÃO: PADRÃO OBSERVER ==========
        System.out.println(">>> DEMONSTRAÇÃO: Padrão Observer (Notificações)\n");

        // Anexando o NotificacaoService como Observer à Troca
        trocaCriada.attach(notificacaoService);
        trocaRaraCriada.attach(notificacaoService);

        // Atualizando o status da troca (dispara o Observer)
        System.out.println("--- Atualizando status da troca normal para 'Concluída' ---");
        trocaController.atualizarStatusTroca(trocaCriada.getIdTroca(), "Concluída");

        System.out.println("\n========================================\n");

        // ========== DEMONSTRAÇÃO: PADRÃO FACTORY METHOD ==========
        System.out.println(">>> DEMONSTRAÇÃO: Padrão Factory Method (Criação de Propostas)\n");

        // Criando propostas simples
        System.out.println("--- Criando Proposta Simples ---");
        Proposta propostaSimples = trocaController.enviarPropostaSimples(trocaRaraCriada.getIdTroca(), 3);
        propostaSimples.adicionarPokemonOfertado(mewtwo);

        // Criando proposta com item extra
        System.out.println("\n--- Criando Proposta com Item Extra ---");
        Proposta propostaComItemExtra = trocaController.enviarPropostaComItemExtra(
            trocaRaraCriada.getIdTroca(), 3, "Poção Rara", 5
        );
        propostaComItemExtra.adicionarPokemonOfertado(mewtwo);

        System.out.println("\n========================================\n");

        // ========== DEMONSTRAÇÃO: LISTAGEM E CONSULTAS ==========
        System.out.println(">>> DEMONSTRAÇÃO: Listagem e Consultas\n");

        System.out.println("--- Trocas Ativas ---");
        List<Troca> trocasAtivas = trocaController.listarTrocasAtivas();
        for (Troca troca : trocasAtivas) {
            System.out.println("  " + troca);
        }

        System.out.println("\n--- Propostas da Troca Rara ---");
        List<Proposta> propostas = trocaController.listarPropostasDeUmaTroca(trocaRaraCriada.getIdTroca());
        for (Proposta proposta : propostas) {
            System.out.println("  " + proposta.getDescricao());
        }

        System.out.println("\n--- Notificações do Jogador 1 ---");
        List<?> notificacoes = trocaController.listarNotificacoesDoJogador(1);
        for (Object notif : notificacoes) {
            System.out.println("  " + notif);
        }

        System.out.println("\n========================================\n");

        // ========== DEMONSTRAÇÃO: ACEITAÇÃO DE PROPOSTA ==========
        System.out.println(">>> DEMONSTRAÇÃO: Aceitação de Proposta\n");

        if (!propostas.isEmpty()) {
            Proposta propostaParaAceitar = propostas.get(0);
            System.out.println("--- Aceitando Proposta ---");
            trocaController.aceitarProposta(propostaParaAceitar.getIdProposta());

            System.out.println("\n--- Notificações do Jogador 3 (Proponente) ---");
            List<?> notificacoesProponente = trocaController.listarNotificacoesDoJogador(3);
            for (Object notif : notificacoesProponente) {
                System.out.println("  " + notif);
            }
        }

        System.out.println("\n========================================");
        System.out.println("FIM DA DEMONSTRAÇÃO");
        System.out.println("========================================");
    }
}
