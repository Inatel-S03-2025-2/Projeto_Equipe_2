package br.inatel.dexmarket.controller;

import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.service.NotificacaoService;
import br.inatel.dexmarket.service.PropostaService;
import br.inatel.dexmarket.service.TrocaService;

import java.util.List;

/**
 * **Padrão MVC (Controller)**: Responsável por receber as requisições e coordenar as ações nos Services.
 * Não utiliza anotações Spring, sendo uma classe Java simples para manter o desacoplamento.
 */
public class TrocaController {

    private final TrocaService trocaService;
    private final PropostaService propostaService;
    private final NotificacaoService notificacaoService;

    // **Injeção de Dependência (Manual)**: Garantindo o desacoplamento do Controller para o Service
    public TrocaController(TrocaService trocaService, PropostaService propostaService, NotificacaoService notificacaoService) {
        this.trocaService = trocaService;
        this.propostaService = propostaService;
        this.notificacaoService = notificacaoService;
    }

    // ========== ENDPOINTS SIMULADOS (Retorno de Objetos de Domínio) ==========

    /**
     * 1. Capacidade de enviar um Json com a informação de todas as "TrocasAtivas" (MarketPlace)
     * @return Lista de Trocas Ativas.
     */
    public List<Troca> listarTrocasAtivas() {
        return trocaService.listarTrocasAtivas();
    }

    /**
     * 2. Um json que consegue enviar todas as propostas que uma troca ativa {id} recebeu
     * @param idTroca ID da troca.
     * @return Lista de Propostas para a troca.
     */
    public List<Proposta> listarPropostasPorTroca(int idTroca) {
        return trocaService.listarPropostasPorTroca(idTroca);
    }

    /**
     * 3. trocas concluídas por id
     * @param idJogador ID do jogador.
     * @return Lista de Trocas Concluídas do jogador.
     */
    public List<Troca> listarTrocasConcluidasDoJogador(int idJogador) {
        return trocaService.listarTrocasConcluidasDoJogador(idJogador);
    }

    /**
     * POST: criar nova Troca
     * @param troca A troca a ser criada.
     * @return A Troca criada.
     */
    public Troca criarTroca(Troca troca) {
        return trocaService.criarTroca(troca);
    }

    /**
     * POST: enviar proposta
     * @param proposta A proposta a ser enviada.
     * @return A Proposta criada.
     */
    public Proposta enviarProposta(Proposta proposta) {
        Proposta novaProposta = propostaService.enviarProposta(proposta);

        // Notificação (Endpoint 4)
        Troca troca = trocaService.buscarTroca(proposta.getIdTroca());
        if (troca != null) {
            notificacaoService.enviarNotificacao(
                    troca.getIdJogadorOfertante(),
                    "NovaPropostaRecebida",
                    "Você recebeu uma nova proposta para a troca #" + troca.getIdTroca()
            );
        }
        return novaProposta;
    }

    /**
     * PUT: aceitar proposta
     * @param idProposta ID da proposta.
     * @return A Proposta aceita.
     */
    public Proposta aceitarProposta(int idProposta) {
        Proposta proposta = trocaService.processarProposta(idProposta, true);

        // Notificação para o proponente
        notificacaoService.enviarNotificacao(
                proposta.getIdJogadorProponente(),
                "PropostaAceita",
                "Sua proposta #" + idProposta + " foi aceita!"
        );
        return proposta;
    }

    /**
     * PUT: rejeitar proposta
     * @param idProposta ID da proposta.
     * @return A Proposta rejeitada.
     */
    public Proposta rejeitarProposta(int idProposta) {
        Proposta proposta = trocaService.processarProposta(idProposta, false);

        // Notificação para o proponente
        notificacaoService.enviarNotificacao(
                proposta.getIdJogadorProponente(),
                "PropostaRecusada",
                "Sua proposta #" + idProposta + " foi recusada."
        );
        return proposta;
    }

    /**
     * PUT: enviar lista de wishlist (Simulação)
     * @param idJogador ID do jogador.
     * @param wishlist Lista de Pokémons desejados.
     * @return Mensagem de sucesso.
     */
    public String enviarListaWishlist(int idJogador, List<String> wishlist) {
        // Lógica de negócio para atualizar a wishlist do jogador
        return "Wishlist do jogador " + idJogador + " atualizada com sucesso.";
    }
}
