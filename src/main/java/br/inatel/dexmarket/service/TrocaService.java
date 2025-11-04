package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.strategy.TradeStrategy;
import br.inatel.dexmarket.strategy.NormalTrade;
import br.inatel.dexmarket.strategy.RareTrade;

// Service com Singleton e Strategy
public class TrocaService {

    // -------- SINGLETON --------
    private static TrocaService instance;

    private TrocaService() {} // Construtor privado

    public static TrocaService getInstance() {
        if (instance == null) {
            instance = new TrocaService();
        }
        return instance;
    }

    // -------- STRATEGY --------
    private TradeStrategy strategy;

    // Define a estratégia em tempo de execução
    public void setStrategy(TradeStrategy strategy) {
        this.strategy = strategy;
    }

    // Executa a troca de acordo com a estratégia definida
    public void executarTroca(Troca troca) {
        if (strategy == null) {
            System.out.println("Nenhuma estratégia definida! Usando troca padrão.");
            strategy = new NormalTrade();
        }
        strategy.executarTroca(troca.getPokemonA(), troca.getPokemonB());
    }

    // Exemplo de método auxiliar de negócio
    public void validarTroca(Troca troca) {
        if (troca.getPokemonA().equals(troca.getPokemonB())) {
            throw new IllegalArgumentException("Não é possível trocar o mesmo Pokémon!");
        }
        System.out.println("Troca validada com sucesso.");
    }
}