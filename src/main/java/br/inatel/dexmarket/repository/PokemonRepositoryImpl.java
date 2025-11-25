package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Pokemon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe PokemonRepositoryImpl - Implementação de PokemonRepository
 * Implementação em memória do repositório de Pokémons.
 * 
 * Nota: Esta é uma implementação simples para fins acadêmicos.
 * Em produção, seria substituída por uma implementação com banco de dados real.
 */
public class PokemonRepositoryImpl implements PokemonRepository {
    private Map<Integer, Pokemon> pokemonMap = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Pokemon save(Pokemon pokemon) {
        if (pokemon.getIdPokemon() == 0) {
            pokemon.setIdPokemon(proximoId++);
        }
        pokemonMap.put(pokemon.getIdPokemon(), pokemon);
        System.out.println("Pokémon salvo: " + pokemon.getNome() + " (ID: " + pokemon.getIdPokemon() + ")");
        return pokemon;
    }

    @Override
    public Pokemon findById(int id) {
        return pokemonMap.get(id);
    }

    @Override
    public List<Pokemon> findAll() {
        return new ArrayList<>(pokemonMap.values());
    }

    @Override
    public List<Pokemon> findByIdJogador(int idJogador) {
        List<Pokemon> pokemons = new ArrayList<>();
        for (Pokemon pokemon : pokemonMap.values()) {
            if (pokemon.getIdJogadorDono() == idJogador) {
                pokemons.add(pokemon);
            }
        }
        return pokemons;
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        if (pokemonMap.containsKey(pokemon.getIdPokemon())) {
            pokemonMap.put(pokemon.getIdPokemon(), pokemon);
            System.out.println("Pokémon atualizado: " + pokemon.getNome());
            return pokemon;
        }
        System.out.println("Pokémon não encontrado para atualização: " + pokemon.getIdPokemon());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (pokemonMap.containsKey(id)) {
            pokemonMap.remove(id);
            System.out.println("Pokémon deletado: ID " + id);
            return true;
        }
        System.out.println("Pokémon não encontrado para deleção: " + id);
        return false;
    }
}
