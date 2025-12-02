package br.inatel.dexmarket.model;

import br.inatel.dexmarket.observer.Observer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe Troca - Entidade do Domínio e Subject do Padrão Observer
 * Representa uma troca de Pokémons no marketplace.
 * 
 * **Padrão Observer (Subject)**: Esta classe é o Subject que notifica os Observers (ex: NotificacaoService)
 * quando o seu estado muda (ex: quando uma proposta é aceita).
 */
public class Troca {
    private int idTroca;
    private int idJogadorOfertante; // Quem criou a troca
    private List<Pokemon> pokemonsOfertados; // Pokémons que o ofertante está oferecendo
    private List<Pokemon> pokemonsDesejados; // Pokémons que o ofertante deseja receber
    private String status; // "Ativa", "Concluída", "Cancelada"
    private Date dataCriacao;
    private Date dataAtualizacao;
    
    // Observer Pattern: Lista de observadores
    private List<Observer> observers = new ArrayList<>();

    // Construtores
    public Troca() {
        this.pokemonsOfertados = new ArrayList<>();
        this.pokemonsDesejados = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public Troca(int idJogadorOfertante, String status) {
        this.idJogadorOfertante = idJogadorOfertante;
        this.status = status;
        this.dataCriacao = new Date();
        this.dataAtualizacao = new Date();
        this.pokemonsOfertados = new ArrayList<>();
        this.pokemonsDesejados = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Getters e Setters
    public int getIdTroca() {
        return idTroca;
    }

    public void setIdTroca(int idTroca) {
        this.idTroca = idTroca;
    }

    public int getIdJogadorOfertante() {
        return idJogadorOfertante;
    }

    public void setIdJogadorOfertante(int idJogadorOfertante) {
        this.idJogadorOfertante = idJogadorOfertante;
    }

    public List<Pokemon> getPokemonsOfertados() {
        return pokemonsOfertados;
    }

    public void setPokemonsOfertados(List<Pokemon> pokemonsOfertados) {
        this.pokemonsOfertados = pokemonsOfertados;
    }

    public List<Pokemon> getPokemonsDesejados() {
        return pokemonsDesejados;
    }

    public void setPokemonsDesejados(List<Pokemon> pokemonsDesejados) {
        this.pokemonsDesejados = pokemonsDesejados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.dataAtualizacao = new Date();
        // **Padrão Observer em ação**: Notifica todos os observadores quando o status muda
        notifyObservers();
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

    // Observer Pattern: Métodos para gerenciar observadores
    /**
     * Adiciona um observador à lista de observadores.
     * 
     * @param observer O observador a ser adicionado
     */
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Remove um observador da lista de observadores.
     * 
     * @param observer O observador a ser removido
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica todos os observadores sobre uma mudança de estado.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // Métodos de negócio
    public void adicionarPokemonOfertado(Pokemon pokemon) {
        this.pokemonsOfertados.add(pokemon);
    }

    public void adicionarPokemonDesejado(Pokemon pokemon) {
        this.pokemonsDesejados.add(pokemon);
    }

    @Override
    public String toString() {
        return "Troca{" +
                "idTroca=" + idTroca +
                ", idJogadorOfertante=" + idJogadorOfertante +
                ", status='" + status + '\'' +
                ", pokemonsOfertados=" + pokemonsOfertados.size() +
                ", pokemonsDesejados=" + pokemonsDesejados.size() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
