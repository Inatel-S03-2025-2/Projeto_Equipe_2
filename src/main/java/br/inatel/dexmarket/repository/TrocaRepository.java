package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Troca;
import java.util.List;

/**
 * Interface TrocaRepository - Padrão Repository
 * Define o contrato para operações de acesso a dados de Trocas.
 */
public interface TrocaRepository {
    /**
     * Salva uma troca no repositório.
     * 
     * @param troca A troca a ser salva
     * @return A troca salva (com ID atribuído, se for inserção)
     */
    Troca save(Troca troca);

    /**
     * Busca uma troca pelo ID.
     * 
     * @param id O ID da troca
     * @return A troca encontrada, ou null se não existir
     */
    Troca findById(int id);

    /**
     * Lista todas as trocas.
     * 
     * @return Lista de todas as trocas
     */
    List<Troca> findAll();

    /**
     * Lista todas as trocas ativas.
     * 
     * @return Lista de trocas com status "Ativa"
     */
    List<Troca> findByStatus(String status);

    /**
     * Lista todas as trocas de um jogador ofertante.
     * 
     * @param idJogadorOfertante O ID do jogador ofertante
     * @return Lista de trocas do jogador
     */
    List<Troca> findByIdJogadorOfertante(int idJogadorOfertante);

    /**
     * Atualiza uma troca existente.
     * 
     * @param troca A troca com dados atualizados
     * @return A troca atualizada
     */
    Troca update(Troca troca);

    /**
     * Deleta uma troca pelo ID.
     * 
     * @param id O ID da troca a ser deletada
     * @return true se foi deletada, false caso contrário
     */
    boolean delete(int id);
}
