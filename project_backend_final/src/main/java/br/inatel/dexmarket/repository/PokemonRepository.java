package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Pokemon;
import java.util.List;

/**
 * Interface PokemonRepository - Padrão Repository
 * Define o contrato para operações de acesso a dados de Pokémons.
 * 
 * Benefício: Abstrai a lógica de persistência (banco de dados, arquivos, etc.)
 * permitindo trocar a implementação sem afetar o código de negócio.
 */
public interface PokemonRepository {
    /**
     * Salva um Pokémon no repositório.
     * 
     * @param pokemon O Pokémon a ser salvo
     * @return O Pokémon salvo (com ID atribuído, se for inserção)
     */
    Pokemon save(Pokemon pokemon);

    /**
     * Busca um Pokémon pelo ID.
     * 
     * @param id O ID do Pokémon
     * @return O Pokémon encontrado, ou null se não existir
     */
    Pokemon findById(int id);

    /**
     * Lista todos os Pokémons.
     * 
     * @return Lista de todos os Pokémons
     */
    List<Pokemon> findAll();

    /**
     * Lista todos os Pokémons de um jogador.
     * 
     * @param idJogador O ID do jogador
     * @return Lista de Pokémons do jogador
     */
    List<Pokemon> findByIdJogador(int idJogador);

    /**
     * Atualiza um Pokémon existente.
     * 
     * @param pokemon O Pokémon com dados atualizados
     * @return O Pokémon atualizado
     */
    Pokemon update(Pokemon pokemon);

    /**
     * Deleta um Pokémon pelo ID.
     * 
     * @param id O ID do Pokémon a ser deletado
     * @return true se foi deletado, false caso contrário
     */
    boolean delete(int id);
}
