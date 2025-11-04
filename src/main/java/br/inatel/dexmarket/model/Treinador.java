package br.inatel.dexmarket.model;

import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private String nome;
    private List<Pokemon> pokemons = new ArrayList<>();

    public Treinador(String nome) {
        this.nome = nome;
    }

    public void adicionarPokemon(Pokemon p) {
        pokemons.add(p);
    }

    public String getNome() {
        return nome;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}