package br.inatel.dexmarket.strategy;

import br.inatel.dexmarket.model.Troca;

/**
 * Classe ValidacaoTrocaRara - Implementação de ValidacaoTrocaStrategy
 * Valida uma troca de Pokémons raros com requisitos mais rigorosos.
 */
public class ValidacaoTrocaRara implements ValidacaoTrocaStrategy {

    @Override
    public boolean validar(Troca troca) {
        // Validação 1: Troca deve ter pelo menos um Pokémon ofertado e um desejado
        if (troca.getPokemonsOfertados() == null || troca.getPokemonsOfertados().isEmpty()) {
            System.out.println("Erro: Troca rara deve ter pelo menos um Pokémon ofertado.");
            return false;
        }

        if (troca.getPokemonsDesejados() == null || troca.getPokemonsDesejados().isEmpty()) {
            System.out.println("Erro: Troca rara deve ter pelo menos um Pokémon desejado.");
            return false;
        }

        // Validação 2: Não é possível trocar o mesmo Pokémon
        for (var ofertado : troca.getPokemonsOfertados()) {
            for (var desejado : troca.getPokemonsDesejados()) {
                if (ofertado.getIdPokemon() == desejado.getIdPokemon()) {
                    System.out.println("Erro: Não é possível trocar o mesmo Pokémon.");
                    return false;
                }
            }
        }

        // Validação 3 (Específica para Trocas Raras): Todos os Pokémons devem ter raridade >= 4
        for (var pokemon : troca.getPokemonsOfertados()) {
            if (pokemon.getRaridade() < 4) {
                System.out.println("Erro: Todos os Pokémons ofertados devem ter raridade >= 4 (Raro ou Lendário).");
                return false;
            }
        }

        for (var pokemon : troca.getPokemonsDesejados()) {
            if (pokemon.getRaridade() < 4) {
                System.out.println("Erro: Todos os Pokémons desejados devem ter raridade >= 4 (Raro ou Lendário).");
                return false;
            }
        }

        System.out.println("Troca rara validada com sucesso.");
        return true;
    }

    @Override
    public String getDescricao() {
        return "Validação Rara - Apenas Pokémons com raridade >= 4";
    }
}
