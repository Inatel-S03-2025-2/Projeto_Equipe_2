package br.inatel.dexmarket.model;

public class Pokemon {
    private String nome;
    private String tipo;

    public Pokemon(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }
}