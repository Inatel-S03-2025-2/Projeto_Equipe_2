package br.inatel.dexmarket.strategy;

import br.inatel.dexmarket.model.Troca;

/**
 * Classe ValidacaoTrocaNormal - Implementação de ValidacaoTrocaStrategy
 * Valida uma troca normal (sem requisitos especiais).
 */
public class ValidacaoTrocaNormal implements ValidacaoTrocaStrategy {

    @Override
    public boolean validar(Troca troca) {
        // Validação 1: Troca deve ter pelo menos um Pokémon ofertado e um desejado
        if (troca.getPokemonsOfertados() == null || troca.getPokemonsOfertados().isEmpty()) {
            System.out.println("Erro: Troca deve ter pelo menos um Pokémon ofertado.");
            return false;
        }

        if (troca.getPokemonsDesejados() == null || troca.getPokemonsDesejados().isEmpty()) {
            System.out.println("Erro: Troca deve ter pelo menos um Pokémon desejado.");
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

        System.out.println("Troca normal validada com sucesso.");
        return true;
    }

    @Override
    public String getDescricao() {
        return "Validação Normal - Sem requisitos especiais";
    }
}
