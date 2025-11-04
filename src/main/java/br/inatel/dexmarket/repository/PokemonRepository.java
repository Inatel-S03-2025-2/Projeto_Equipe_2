package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonRepository {

    // --- SINGLETON ---
    private static volatile PokemonRepository instance;

    // "banco" em memória
    private final Map<Integer, Pokemon> pokemons = new HashMap<>();

    // construtor privado
    private PokemonRepository() {
        // dados de exemplo pra não ficar vazio
        save(new Pokemon(1, "Bulbasaur", "Grama/Veneno",
                "https://img.pokemondb.net/artwork/bulbasaur.jpg", 1));
        save(new Pokemon(2, "Charizard", "Fogo/Voador",
                "https://img.pokemondb.net/artwork/charizard.jpg", 2));
        save(new Pokemon(3, "Mewtwo", "Psíquico",
                "https://img.pokemondb.net/artwork/mewtwo.jpg", 3));
    }

    public static PokemonRepository getInstance() {
        if (instance == null) {
            synchronized (PokemonRepository.class) {
                if (instance == null) {
                    instance = new PokemonRepository();
                }
            }
        }
        return instance;
    }

    // CRUD básico
    public void save(Pokemon pokemon) {
        pokemons.put(pokemon.getIdPokemon(), pokemon);
    }

    public Pokemon findById(int id) {
        return pokemons.get(id);
    }

    public List<Pokemon> findAll() {
        return new ArrayList<>(pokemons.values());
    }

    public void delete(int id) {
        pokemons.remove(id);
    }
}
