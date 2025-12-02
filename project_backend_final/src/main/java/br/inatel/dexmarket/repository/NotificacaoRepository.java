package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Notificacao;
import java.util.List;

/**
 * Interface NotificacaoRepository - Padrão Repository
 * Define o contrato para operações de acesso a dados de Notificações.
 */
public interface NotificacaoRepository {
    /**
     * Salva uma notificação no repositório.
     * 
     * @param notificacao A notificação a ser salva
     * @return A notificação salva (com ID atribuído, se for inserção)
     */
    Notificacao save(Notificacao notificacao);

    /**
     * Busca uma notificação pelo ID.
     * 
     * @param id O ID da notificação
     * @return A notificação encontrada, ou null se não existir
     */
    Notificacao findById(int id);

    /**
     * Lista todas as notificações de um destinatário.
     * 
     * @param idDestinatario O ID do jogador destinatário
     * @return Lista de notificações do jogador
     */
    List<Notificacao> findByIdDestinatario(int idDestinatario);

    /**
     * Lista todas as notificações não lidas de um destinatário.
     * 
     * @param idDestinatario O ID do jogador destinatário
     * @return Lista de notificações não lidas
     */
    List<Notificacao> findByIdDestinatarioAndNaoLidas(int idDestinatario);

    /**
     * Atualiza uma notificação existente.
     * 
     * @param notificacao A notificação com dados atualizados
     * @return A notificação atualizada
     */
    Notificacao update(Notificacao notificacao);

    /**
     * Deleta uma notificação pelo ID.
     * 
     * @param id O ID da notificação a ser deletada
     * @return true se foi deletada, false caso contrário
     */
    boolean delete(int id);
}
