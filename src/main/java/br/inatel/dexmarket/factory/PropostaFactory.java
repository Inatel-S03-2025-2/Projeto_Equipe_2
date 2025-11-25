package br.inatel.dexmarket.factory;

import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.PropostaSimples;
import br.inatel.dexmarket.model.PropostaComItemExtra;

/**
 * Classe PropostaFactory - Padrão Factory Method
 * Responsável por criar diferentes tipos de propostas de forma desacoplada.
 * 
 * Benefício: O código cliente não precisa conhecer as classes concretas (PropostaSimples, PropostaComItemExtra).
 * Apenas a Factory conhece como criar cada tipo.
 */
public class PropostaFactory {

    /**
     * Cria uma proposta simples.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @return Uma nova instância de PropostaSimples
     */
    public static Proposta criarPropostaSimples(int idTroca, int idJogadorProponente) {
        return new PropostaSimples(idTroca, idJogadorProponente);
    }

    /**
     * Cria uma proposta com item extra.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @param itemExtra Descrição do item extra
     * @param quantidadeItemExtra Quantidade do item extra
     * @return Uma nova instância de PropostaComItemExtra
     */
    public static Proposta criarPropostaComItemExtra(int idTroca, int idJogadorProponente, 
                                                      String itemExtra, int quantidadeItemExtra) {
        return new PropostaComItemExtra(idTroca, idJogadorProponente, itemExtra, quantidadeItemExtra);
    }

    /**
     * Cria uma proposta genérica baseada no tipo fornecido.
     * Este método é mais flexível e pode ser estendido para novos tipos.
     * 
     * @param tipo Tipo de proposta ("simples", "comItemExtra")
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @param parametrosAdicionais Parâmetros adicionais específicos do tipo
     * @return Uma nova instância de Proposta do tipo especificado
     * @throws IllegalArgumentException Se o tipo não for reconhecido
     */
    public static Proposta criarProposta(String tipo, int idTroca, int idJogadorProponente, 
                                         Object... parametrosAdicionais) {
        switch (tipo.toLowerCase()) {
            case "simples":
                return criarPropostaSimples(idTroca, idJogadorProponente);
            case "comitemextra":
                if (parametrosAdicionais.length >= 2) {
                    String itemExtra = (String) parametrosAdicionais[0];
                    int quantidade = (int) parametrosAdicionais[1];
                    return criarPropostaComItemExtra(idTroca, idJogadorProponente, itemExtra, quantidade);
                } else {
                    throw new IllegalArgumentException("Parâmetros insuficientes para criar PropostaComItemExtra");
                }
            default:
                throw new IllegalArgumentException("Tipo de proposta desconhecido: " + tipo);
        }
    }
}
