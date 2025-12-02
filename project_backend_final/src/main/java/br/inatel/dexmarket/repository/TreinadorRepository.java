package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Treinador;
import java.util.List;

/**
 * Interface TreinadorRepository - Padrão Repository
 * Define o contrato para operações de persistência da entidade Treinador.
 */
public interface TreinadorRepository {
    Treinador findById(int id);
    Treinador save(Treinador entity);
    List<Treinador> findAll();
    boolean delete(int id);
}
