package br.inatel.dexmarket.strategy;

import br.inatel.dexmarket.model.Troca;

/**
 * Interface ValidacaoTrocaStrategy - Padrão Strategy
 * Define o contrato para diferentes estratégias de validação de trocas.
 * 
 * Benefício: Permite trocar o algoritmo de validação em tempo de execução sem alterar o código cliente.
 */
public interface ValidacaoTrocaStrategy {
    /**
     * Valida uma troca de acordo com a estratégia específica.
     * 
     * @param troca A troca a ser validada
     * @return true se a troca é válida, false caso contrário
     */
    boolean validar(Troca troca);

    /**
     * Retorna uma descrição da estratégia de validação.
     * 
     * @return String com a descrição
     */
    String getDescricao();
}
