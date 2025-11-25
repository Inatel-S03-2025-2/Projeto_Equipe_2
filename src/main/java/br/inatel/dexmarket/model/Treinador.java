package br.inatel.dexmarket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Treinador - Entidade do Domínio
 * Representa um jogador no marketplace de trocas.
 */
public class Treinador {
    private int idJogador;
    private String nome;
    private String email;
    private List<Pokemon> pokemons; // Pokémons que o treinador possui
    private List<Pokemon> wishlist; // Pokémons que o treinador deseja

    // Construtores
    public Treinador() {
        this.pokemons = new ArrayList<>();
        this.wishlist = new ArrayList<>();
    }

    public Treinador(int idJogador, String nome, String email) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.email = email;
        this.pokemons = new ArrayList<>();
        this.wishlist = new ArrayList<>();
    }

    // Getters e Setters
    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Pokemon> wishlist) {
        this.wishlist = wishlist;
    }

    // Métodos de negócio
    public void adicionarPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    public void removerPokemon(Pokemon pokemon) {
        this.pokemons.remove(pokemon);
    }

    public void adicionarWishlist(Pokemon pokemon) {
        this.wishlist.add(pokemon);
    }

    public void removerWishlist(Pokemon pokemon) {
        this.wishlist.remove(pokemon);
    }

    @Override
    public String toString() {
        return "Treinador{" +
                "idJogador=" + idJogador +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", pokemons=" + pokemons.size() +
                ", wishlist=" + wishlist.size() +
                '}';
    }
}
