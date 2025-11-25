package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Proposta;
import java.util.List;

/**
 * Interface PropostaRepository - Padrão Repository
 * Define o contrato para operações de acesso a dados de Propostas.
 */
public interface PropostaRepository {
    /**
     * Salva uma proposta no repositório.
     * 
     * @param proposta A proposta a ser salva
     * @return A proposta salva (com ID atribuído, se for inserção)
     */
    Proposta save(Proposta proposta);

    /**
     * Busca uma proposta pelo ID.
     * 
     * @param id O ID da proposta
     * @return A proposta encontrada, ou null se não existir
     */
    Proposta findById(int id);

    /**
     * Lista todas as propostas de uma troca.
     * 
     * @param idTroca O ID da troca
     * @return Lista de propostas da troca
     */
    List<Proposta> findByIdTroca(int idTroca);

    /**
     * Lista todas as propostas de um proponente.
     * 
     * @param idJogadorProponente O ID do jogador proponente
     * @return Lista de propostas do jogador
     */
    List<Proposta> findByIdJogadorProponente(int idJogadorProponente);

    /**
     * Lista todas as propostas com um status específico.
     * 
     * @param status O status das propostas ("Pendente", "Aceita", "Recusada")
     * @return Lista de propostas com o status especificado
     */
    List<Proposta> findByStatus(String status);

    /**
     * Atualiza uma proposta existente.
     * 
     * @param proposta A proposta com dados atualizados
     * @return A proposta atualizada
     */
    Proposta update(Proposta proposta);

    /**
     * Deleta uma proposta pelo ID.
     * 
     * @param id O ID da proposta a ser deletada
     * @return true se foi deletada, false caso contrário
     */
    boolean delete(int id);
}
