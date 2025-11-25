package br.inatel.dexmarket.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe Abstrata Proposta - Entidade do Domínio
 * Define a estrutura base para diferentes tipos de propostas de troca.
 * 
 * Padrão Factory: Esta classe é a classe abstrata que será estendida por diferentes
 * tipos de propostas (PropostaSimples, PropostaComItemExtra, etc.).
 */
public abstract class Proposta {
    protected int idProposta;
    protected int idTroca;
    protected int idJogadorProponente;
    protected List<Pokemon> pokemonsOfertados; // Pokémons que o proponente oferece
    protected String status; // "Pendente", "Aceita", "Recusada"
    protected Date dataCriacao;
    protected Date dataAtualizacao;

    // Construtores
    public Proposta() {
        this.pokemonsOfertados = new ArrayList<>();
    }

    public Proposta(int idTroca, int idJogadorProponente) {
        this.idTroca = idTroca;
        this.idJogadorProponente = idJogadorProponente;
        this.status = "Pendente";
        this.dataCriacao = new Date();
        this.dataAtualizacao = new Date();
        this.pokemonsOfertados = new ArrayList<>();
    }

    // Getters e Setters
    public int getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }

    public int getIdTroca() {
        return idTroca;
    }

    public void setIdTroca(int idTroca) {
        this.idTroca = idTroca;
    }

    public int getIdJogadorProponente() {
        return idJogadorProponente;
    }

    public void setIdJogadorProponente(int idJogadorProponente) {
        this.idJogadorProponente = idJogadorProponente;
    }

    public List<Pokemon> getPokemonsOfertados() {
        return pokemonsOfertados;
    }

    public void setPokemonsOfertados(List<Pokemon> pokemonsOfertados) {
        this.pokemonsOfertados = pokemonsOfertados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.dataAtualizacao = new Date();
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    // Métodos abstratos que devem ser implementados pelas subclasses
    /**
     * Valida a proposta de acordo com as regras específicas do tipo.
     * 
     * @return true se a proposta é válida, false caso contrário
     */
    public abstract boolean validar();

    /**
     * Retorna uma descrição detalhada da proposta.
     * 
     * @return String com a descrição
     */
    public abstract String getDescricao();

    // Método concreto
    public void adicionarPokemonOfertado(Pokemon pokemon) {
        this.pokemonsOfertados.add(pokemon);
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "idProposta=" + idProposta +
                ", idTroca=" + idTroca +
                ", idJogadorProponente=" + idJogadorProponente +
                ", status='" + status + '\'' +
                ", pokemonsOfertados=" + pokemonsOfertados.size() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
