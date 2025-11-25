package br.inatel.dexmarket.model;

/**
 * Classe Pokemon - Entidade do Domínio
 * Representa um Pokémon que pode ser trocado no marketplace.
 */
public class Pokemon {
    private int idPokemon;
    private String nome;
    private String tipo;
    private String urlImagem;
    private int raridade; // 1 a 5 (1 = comum, 5 = lendário)
    private int idJogadorDono;

    // Construtores
    public Pokemon() {
    }

    public Pokemon(int idPokemon, String nome, String tipo, String urlImagem, int raridade, int idJogadorDono) {
        this.idPokemon = idPokemon;
        this.nome = nome;
        this.tipo = tipo;
        this.urlImagem = urlImagem;
        this.raridade = raridade;
        this.idJogadorDono = idJogadorDono;
    }

    // Getters e Setters
    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public int getRaridade() {
        return raridade;
    }

    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public int getIdJogadorDono() {
        return idJogadorDono;
    }

    public void setIdJogadorDono(int idJogadorDono) {
        this.idJogadorDono = idJogadorDono;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "idPokemon=" + idPokemon +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", raridade=" + raridade +
                ", idJogadorDono=" + idJogadorDono +
                '}';
    }
}
