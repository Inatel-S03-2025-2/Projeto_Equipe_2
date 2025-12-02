package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Pokemon;
import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.PropostaSimples;
import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.repository.PropostaRepository;
import br.inatel.dexmarket.repository.PropostaRepositoryImpl;
import br.inatel.dexmarket.repository.TrocaRepository;
import br.inatel.dexmarket.repository.TrocaRepositoryImpl;
import br.inatel.dexmarket.strategy.ValidacaoTrocaRara;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrocaServiceTest {

    private TrocaService trocaService;
    private TrocaRepository trocaRepository;
    private PropostaRepository propostaRepository;

    @BeforeEach
    void setUp() {
        // Inicializa repositórios em memória para cada teste
        trocaRepository = new TrocaRepositoryImpl();
        propostaRepository = new PropostaRepositoryImpl();
        trocaService = new TrocaService(trocaRepository, propostaRepository);
    }

    // --- Dados Mock ---
    private Pokemon getPokemon(int id, String nome, int raridade, int dono) {
        return new Pokemon(id, nome, "Tipo", "url", raridade, dono);
    }

    private Troca criarTrocaAtiva(int idOfertante, Pokemon ofertado, Pokemon desejado) {
        Troca troca = new Troca(idOfertante, "Ativa");
        troca.adicionarPokemonOfertado(ofertado);
        troca.adicionarPokemonDesejado(desejado);
        return trocaService.criarTroca(troca);
    }

    // --- Testes de Criar Troca ---

    @Test
    void criarTroca_TrocaValida_DeveRetornarTrocaComId() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca troca = new Troca(1, "Ativa");
        troca.adicionarPokemonOfertado(p1);
        troca.adicionarPokemonDesejado(p2);

        Troca novaTroca = trocaService.criarTroca(troca);

        assertNotNull(novaTroca);
        assertTrue(novaTroca.getIdTroca() > 0);
        assertEquals("Ativa", novaTroca.getStatus());
    }

    @Test
    void criarTroca_TrocaInvalidaComEstrategiaRara_DeveLancarExcecao() {
        // Configura a estratégia de validação para Rara
        trocaService.setEstrategiaValidacao(new ValidacaoTrocaRara());

        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1); // Raridade 1
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2); // Raridade 1
        Troca troca = new Troca(1, "Ativa");
        troca.adicionarPokemonOfertado(p1);
        troca.adicionarPokemonDesejado(p2);

        assertThrows(IllegalArgumentException.class, () -> {
            trocaService.criarTroca(troca);
        });
    }

    // --- Testes de Listar Trocas ---

    @Test
    void listarTrocasAtivas_DeveRetornarApenasTrocasAtivas() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca t1 = criarTrocaAtiva(1, p1, p2);

        Troca t2 = new Troca(2, "Concluída");
        trocaRepository.save(t2); // Salva uma troca concluída diretamente no repo

        List<Troca> ativas = trocaService.listarTrocasAtivas();

        assertEquals(1, ativas.size());
        assertEquals(t1.getIdTroca(), ativas.get(0).getIdTroca());
    }

    @Test
    void listarTrocasConcluidasDoJogador_DeveRetornarTrocasConcluidasDoJogador() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca t1 = criarTrocaAtiva(1, p1, p2);

        // Simula a conclusão da troca
        t1.setStatus("Concluída");
        trocaRepository.update(t1);

        List<Troca> concluidas = trocaService.listarTrocasConcluidasDoJogador(1);

        assertEquals(1, concluidas.size());
        assertEquals(t1.getIdTroca(), concluidas.get(0).getIdTroca());
    }

    // --- Testes de Proposta ---

    @Test
    void processarProposta_AceitarProposta_DeveConcluirTrocaEAceitarProposta() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca troca = criarTrocaAtiva(1, p1, p2);

        Proposta proposta = new PropostaSimples(troca.getIdTroca(), 2);
        proposta.adicionarPokemonOfertado(getPokemon(3, "Squirtle", 1, 2));
        Proposta propostaCriada = propostaRepository.save(proposta);

        Proposta propostaAceita = trocaService.processarProposta(propostaCriada.getIdProposta(), true);
        Troca trocaConcluida = trocaService.buscarTroca(troca.getIdTroca());

        assertEquals("Aceita", propostaAceita.getStatus());
        assertEquals("Concluída", trocaConcluida.getStatus());
    }

    @Test
    void processarProposta_RejeitarProposta_DeveRejeitarPropostaENaoAlterarTroca() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca troca = criarTrocaAtiva(1, p1, p2);

        Proposta proposta = new PropostaSimples(troca.getIdTroca(), 2);
        proposta.adicionarPokemonOfertado(getPokemon(3, "Squirtle", 1, 2));
        Proposta propostaCriada = propostaRepository.save(proposta);

        Proposta propostaRejeitada = trocaService.processarProposta(propostaCriada.getIdProposta(), false);
        Troca trocaOriginal = trocaService.buscarTroca(troca.getIdTroca());

        assertEquals("Rejeitada", propostaRejeitada.getStatus());
        assertEquals("Ativa", trocaOriginal.getStatus());
    }

    @Test
    void listarPropostasPorTroca_DeveRetornarPropostasCorretas() {
        Pokemon p1 = getPokemon(1, "Pikachu", 1, 1);
        Pokemon p2 = getPokemon(2, "Charmander", 1, 2);
        Troca troca1 = criarTrocaAtiva(1, p1, p2);
        Troca troca2 = criarTrocaAtiva(2, p2, p1);

        Proposta proposta1 = new PropostaSimples(troca1.getIdTroca(), 2);
        proposta1.adicionarPokemonOfertado(getPokemon(3, "Squirtle", 1, 2));
        propostaRepository.save(proposta1);

        Proposta proposta2 = new PropostaSimples(troca1.getIdTroca(), 3);
        proposta2.adicionarPokemonOfertado(getPokemon(4, "Bulbasaur", 1, 3));
        propostaRepository.save(proposta2);

        Proposta proposta3 = new PropostaSimples(troca2.getIdTroca(), 1);
        proposta3.adicionarPokemonOfertado(getPokemon(5, "Jigglypuff", 1, 1));
        propostaRepository.save(proposta3);

        List<Proposta> propostasTroca1 = trocaService.listarPropostasPorTroca(troca1.getIdTroca());

        assertEquals(2, propostasTroca1.size());
        assertTrue(propostasTroca1.stream().anyMatch(p -> p.getIdJogadorProponente() == 2));
        assertTrue(propostasTroca1.stream().anyMatch(p -> p.getIdJogadorProponente() == 3));
    }
}
