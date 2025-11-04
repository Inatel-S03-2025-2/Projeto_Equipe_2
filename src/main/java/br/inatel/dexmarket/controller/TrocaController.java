package br.inatel.dexmarket.controller;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.service.TrocaService;

public class TrocaController {
    // Singleton pra garantir uma única instância do serviço
    private TrocaService trocaService = TrocaService.getInstance();
    
    // Strategy pra permitir trocar o tipo de notificação
    private NotificacaoStrategy notificacaoStrategy;
    
    public void setNotificacaoStrategy(NotificacaoStrategy strategy) {
        this.notificacaoStrategy = strategy;
    }
    
    public void registra(Troca troca) {
        trocaService.realizarTroca(troca);
        
        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Troca registrada: " + troca.getId());
        }
    }
    
    public void envia(Proposia proposta) {

        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Nova proposta enviada");
        }
    }
    
    public void aceita(Proposia proposta) {

        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Proposta aceita");
        }
    }
}