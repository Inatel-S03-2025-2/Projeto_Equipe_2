package br.inatel.dexmarket.model;

public class Troca {
    private Treinador oferente;
    private Treinador recebedor;
    private Pokemon pokemonOferecido;
    private Pokemon pokemonDesejado;

    public Troca(Treinador oferente, Treinador recebedor, Pokemon pokemonOferecido, Pokemon pokemonDesejado) {
        this.oferente = oferente;
        this.recebedor = recebedor;
        this.pokemonOferecido = pokemonOferecido;
        this.pokemonDesejado = pokemonDesejado;
    }

    public Treinador getOferente() {
        return oferente;
    }

    public Treinador getRecebedor() {
        return recebedor;
    }

    @Override
    public String toString() {
        return oferente.getNome() + " oferece " + pokemonOferecido + " por " + pokemonDesejado + " de " + recebedor.getNome();
    }
}