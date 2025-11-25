package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.model.Pokemon;
import br.inatel.dexmarket.strategy.TradeStrategy;
import br.inatel.dexmarket.strategy.NormalTrade;

import java.util.ArrayList;
import java.util.List;

// Service com Singleton e Strategy aprimorados
public class TrocaService {
    private TrocaRepository trocaRepository;
    private PropostaRepository propostaRepository;
    private ValidacaoTrocaStrategy estrategiaValidacao;

    // -------- SINGLETON (Thread-safe + double-checked locking) --------
    private static volatile TrocaService instance;

    private TrocaService() {}

    public static TrocaService getInstance() {
        if (instance == null) {
            synchronized (TrocaService.class) {
                if (instance == null) {
                    instance = new TrocaService();
                }
            }
        }

        // Salva a troca no repositório
        return trocaRepository.save(troca);
    }

    /**
     * Busca uma troca pelo ID.
     * 
     * @param idTroca ID da troca
     * @return A troca encontrada
     */
    public Troca buscarTroca(int idTroca) {
        return trocaRepository.findById(idTroca);
    }

    /**
     * Lista todas as trocas ativas.
     * 
     * @return Lista de trocas com status "Ativa"
     */
    public List<Troca> listarTrocasAtivas() {
        return trocaRepository.findByStatus("Ativa");
    }

    // -------- ATRIBUTOS DE NEGÓCIO --------
    private TradeStrategy strategy = new NormalTrade(); // estratégia padrão
    private final List<Troca> historicoTrocas = new ArrayList<>();

    // -------- MÉTODOS PRINCIPAIS --------

    /** Define a estratégia da troca. */
    public void setStrategy(TradeStrategy strategy) {
        this.strategy = strategy != null ? strategy : new NormalTrade();
        log("Estratégia definida: " + this.strategy.getClass().getSimpleName());
    }

    /** Executa a troca aplicando Strategy + validações */
    public void realizarTroca(Troca troca) {

        validarTroca(troca);
        log("Iniciando troca usando: " + strategy.getClass().getSimpleName());

        strategy.executarTroca(troca.getPokemonA(), troca.getPokemonB());

        historicoTrocas.add(troca);
        log("Troca concluída com sucesso!");
    }

    // -------- VALIDAÇÕES E LÓGICA DE NEGÓCIO --------

    /** Validação completa da troca */
    public void validarTroca(Troca troca) {

        if (troca == null)
            throw new IllegalArgumentException("Objeto Troca não pode ser nulo.");

        Pokemon a = troca.getPokemonA();
        Pokemon b = troca.getPokemonB();

        if (a == null || b == null)
            throw new IllegalArgumentException("Pokémons da troca não podem ser nulos.");

        if (a.equals(b))
            throw new IllegalArgumentException("Não é possível trocar o mesmo Pokémon!");

        if (a.getRaridade() < 0 || b.getRaridade() < 0)
            throw new IllegalArgumentException("Raridade inválida nos Pokémons.");

        log("Troca validada.");
    }

    /** Exemplo útil ao projeto: calcula benefício relativo entre os Pokémons */
    public int calcularBeneficioTroca(Troca troca) {
        int beneficio = troca.getPokemonB().getRaridade() - troca.getPokemonA().getRaridade();

        log("Benefício da troca: " + beneficio);
        return beneficio;
    }

    // -------- HISTÓRICO --------

    public List<Troca> getHistoricoTrocas() {
        return List.copyOf(historicoTrocas);
    }

    // -------- UTILITÁRIOS INTERNOS --------

    private void log(String msg) {
        System.out.println("[TrocaService] " + msg);
    }
}
