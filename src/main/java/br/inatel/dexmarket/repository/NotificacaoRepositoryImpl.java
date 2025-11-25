package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Notificacao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe NotificacaoRepositoryImpl - Implementação de NotificacaoRepository
 * Implementação em memória do repositório de Notificações.
 */
public class NotificacaoRepositoryImpl implements NotificacaoRepository {
    private Map<Integer, Notificacao> notificacaoMap = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Notificacao save(Notificacao notificacao) {
        if (notificacao.getIdNotificacao() == 0) {
            notificacao.setIdNotificacao(proximoId++);
        }
        notificacaoMap.put(notificacao.getIdNotificacao(), notificacao);
        System.out.println("Notificação salva: ID " + notificacao.getIdNotificacao() + 
                         ", Tipo: " + notificacao.getTipo() + 
                         ", Destinatário: " + notificacao.getIdDestinatario());
        return notificacao;
    }

    @Override
    public Notificacao findById(int id) {
        return notificacaoMap.get(id);
    }

    @Override
    public List<Notificacao> findByIdDestinatario(int idDestinatario) {
        List<Notificacao> notificacoes = new ArrayList<>();
        for (Notificacao notificacao : notificacaoMap.values()) {
            if (notificacao.getIdDestinatario() == idDestinatario) {
                notificacoes.add(notificacao);
            }
        }
        return notificacoes;
    }

    @Override
    public List<Notificacao> findByIdDestinatarioAndNaoLidas(int idDestinatario) {
        List<Notificacao> notificacoes = new ArrayList<>();
        for (Notificacao notificacao : notificacaoMap.values()) {
            if (notificacao.getIdDestinatario() == idDestinatario && !notificacao.isLida()) {
                notificacoes.add(notificacao);
            }
        }
        return notificacoes;
    }

    @Override
    public Notificacao update(Notificacao notificacao) {
        if (notificacaoMap.containsKey(notificacao.getIdNotificacao())) {
            notificacaoMap.put(notificacao.getIdNotificacao(), notificacao);
            System.out.println("Notificação atualizada: ID " + notificacao.getIdNotificacao());
            return notificacao;
        }
        System.out.println("Notificação não encontrada para atualização: " + notificacao.getIdNotificacao());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (notificacaoMap.containsKey(id)) {
            notificacaoMap.remove(id);
            System.out.println("Notificação deletada: ID " + id);
            return true;
        }
        System.out.println("Notificação não encontrada para deleção: " + id);
        return false;
    }
}
