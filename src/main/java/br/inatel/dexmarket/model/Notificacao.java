package br.inatel.dexmarket.model;

import java.time.LocalDateTime;

/**
 * Representa uma notificação enviada a um treinador
 */
public class Notificacao {
    private String destinatario;
    private String mensagem;
    private TipoNotificacao tipo;
    private LocalDateTime dataHora;
    
    public enum TipoNotificacao {
        NOVA_TROCA,
        TROCA_ACEITA,
        TROCA_RECUSADA,
        SISTEMA
    }
    
    public Notificacao(String destinatario, String mensagem, TipoNotificacao tipo) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now();
    }
    
    public String getDestinatario() {
        return destinatario;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public TipoNotificacao getTipo() {
        return tipo;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s para %s: %s", 
            tipo, dataHora, destinatario, mensagem);
    }
}