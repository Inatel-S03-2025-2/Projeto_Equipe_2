package br.inatel.dexmarket.model;

import java.util.Date;

/**
 * Classe Notificacao - Entidade do Domínio
 * Representa uma notificação enviada a um jogador sobre eventos de troca.
 */
public class Notificacao {
    private int idNotificacao;
    private int idDestinatario; // ID do jogador que receberá a notificação
    private String tipo; // "PropostaRecebida", "PropostaAceita", "TrocaConcluída", etc.
    private String mensagem;
    private Date dataEnvio;
    private boolean lida;

    // Construtores
    public Notificacao() {
    }

    public Notificacao(int idDestinatario, String tipo, String mensagem) {
        this.idDestinatario = idDestinatario;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.dataEnvio = new Date();
        this.lida = false;
    }

    // Getters e Setters
    public int getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(int idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    // Métodos de negócio
    public void marcarComoLida() {
        this.lida = true;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "idNotificacao=" + idNotificacao +
                ", idDestinatario=" + idDestinatario +
                ", tipo='" + tipo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", lida=" + lida +
                '}';
    }
}
