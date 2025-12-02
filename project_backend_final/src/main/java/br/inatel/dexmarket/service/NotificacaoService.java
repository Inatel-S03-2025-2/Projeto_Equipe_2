package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Notificacao;
import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.observer.Observer;
import br.inatel.dexmarket.repository.NotificacaoRepository;

import java.util.List;

/**
 * Classe NotificacaoService - Observer e Service
 * Implementa o padrão Observer para reagir a mudanças de estado em Trocas.
 * Também gerencia a lógica de negócio relacionada a notificações.
 * 
 * **Padrão Observer (Observer)**: Esta classe é um Observer que reage quando uma Troca muda de status.
 */
public class NotificacaoService implements Observer {
    private NotificacaoRepository notificacaoRepository;

    // Construtor com injeção de dependência
    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    /**
     * **Padrão Observer em ação**: Método chamado quando uma Troca sofre alteração.
     * 
     * @param troca A troca que sofreu alteração
     */
    @Override
    public void update(Troca troca) {
        String mensagem = "Troca #" + troca.getIdTroca() + " teve seu status alterado para: " + troca.getStatus();
        System.out.println("[OBSERVER] " + mensagem);
        
        // Cria uma notificação para o jogador ofertante
        Notificacao notificacao = new Notificacao(
            troca.getIdJogadorOfertante(),
            "AtualizacaoTroca",
            mensagem
        );
        
        // Salva a notificação no repositório
        notificacaoRepository.save(notificacao);
    }

    /**
     * Envia uma notificação genérica.
     * 
     * @param idDestinatario ID do jogador destinatário
     * @param tipo Tipo de notificação
     * @param mensagem Mensagem da notificação
     * @return A notificação criada
     */
    public Notificacao enviarNotificacao(int idDestinatario, String tipo, String mensagem) {
        Notificacao notificacao = new Notificacao(idDestinatario, tipo, mensagem);
        return notificacaoRepository.save(notificacao);
    }

    /**
     * Busca uma notificação pelo ID.
     * 
     * @param idNotificacao ID da notificação
     * @return A notificação encontrada
     */
    public Notificacao buscarNotificacao(int idNotificacao) {
        return notificacaoRepository.findById(idNotificacao);
    }

    /**
     * Lista todas as notificações de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de notificações do jogador
     */
    public List<Notificacao> listarNotificacoesDoJogador(int idJogador) {
        return notificacaoRepository.findByIdDestinatario(idJogador);
    }

    /**
     * Lista todas as notificações não lidas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de notificações não lidas
     */
    public List<Notificacao> listarNotificacoesNaoLidas(int idJogador) {
        return notificacaoRepository.findByIdDestinatarioAndNaoLidas(idJogador);
    }

    /**
     * Marca uma notificação como lida.
     * 
     * @param idNotificacao ID da notificação
     */
    public void marcarComoLida(int idNotificacao) {
        Notificacao notificacao = notificacaoRepository.findById(idNotificacao);
        if (notificacao != null) {
            notificacao.marcarComoLida();
            notificacaoRepository.update(notificacao);
            System.out.println("Notificação marcada como lida: " + idNotificacao);
        }
    }
}
