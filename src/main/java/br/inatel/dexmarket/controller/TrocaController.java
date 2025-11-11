package br.inatel.dexmarket.controller;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.service.TrocaService;
import br.inatel.dexmarket.strategy.NotificacaoStrategy;

public class TrocaController {

    // O controller não cria mais o serviço diretamente (sem Singleton)
    // Ele recebe o TrocaService por injeção de dependência (DI)
    private final TrocaService trocaService;

    // Mantém o uso de Strategy para enviar notificações (desacoplamento)
    private NotificacaoStrategy notificacaoStrategy;

    // Construtor com injeção do serviço
    public TrocaController(TrocaService trocaService) {
        this.trocaService = trocaService;
    }

    // Permite trocar o tipo de notificação em tempo de execução
    public void setNotificacaoStrategy(NotificacaoStrategy strategy) {
        this.notificacaoStrategy = strategy;
    }

    // Método principal: registra uma troca
    public void registrar(Troca troca) {
        // Controller apenas orquestra: delega regras ao serviço
        trocaService.validarTroca(troca);
        trocaService.executarTroca(troca);

        // Envia notificação, se houver uma Strategy configurada
        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Troca registrada com sucesso!");
        }
    }

    // Método para enviar uma proposta de troca
    public void enviarProposta(Troca troca) {
        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Nova proposta de troca enviada!");
        }
    }

    // Método para aceitar uma proposta de troca
    public void aceitarProposta(Troca troca) {
        if (notificacaoStrategy != null) {
            notificacaoStrategy.enviarNotificacao("Proposta de troca aceita!");
        }
    }
}
