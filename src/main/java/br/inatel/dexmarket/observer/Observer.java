package br.inatel.dexmarket.observer;

import br.inatel.dexmarket.model.Troca;

/**
 * Interface Observer - Padrão Observer
 * Define o contrato para objetos que desejam ser notificados sobre mudanças em uma Troca.
 * 
 * Exemplo de uso: NotificacaoService implementa esta interface para reagir a mudanças de status
 * de uma Troca.
 */
public interface Observer {
    /**
     * Método chamado quando o Subject (Troca) sofre uma alteração.
     * 
     * @param troca A troca que sofreu alteração
     */
    void update(Troca troca);
}
