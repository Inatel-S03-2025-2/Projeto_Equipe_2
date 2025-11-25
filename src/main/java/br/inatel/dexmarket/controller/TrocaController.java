package br.inatel.dexmarket.controller;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.Pokemon;
import br.inatel.dexmarket.service.TrocaService;
import br.inatel.dexmarket.service.PropostaService;
import br.inatel.dexmarket.service.NotificacaoService;
import java.util.List;

/**
 * Classe TrocaController - View/Controller (MVC)
 * Responsável por orquestrar as requisições relacionadas a trocas.
 * 
 * Padrão MVC Desacoplado:
 * - Não contém lógica de negócio (essa fica nos Services)
 * - Apenas orquestra chamadas aos Services
 * - Recebe dependências pelo construtor (Injeção de Dependência)
 */
public class TrocaController {
    private TrocaService trocaService;
    private PropostaService propostaService;
    private NotificacaoService notificacaoService;

    // Construtor com injeção de dependências
    public TrocaController(TrocaService trocaService, PropostaService propostaService, 
                          NotificacaoService notificacaoService) {
        this.trocaService = trocaService;
        this.propostaService = propostaService;
        this.notificacaoService = notificacaoService;
    }

    // ========== ENDPOINTS DE TROCAS ==========

    /**
     * Endpoint: POST /trocas
     * Cria uma nova troca.
     * 
     * @param troca A troca a ser criada
     * @return A troca criada
     */
    public Troca criarTroca(Troca troca) {
        System.out.println("[CONTROLLER] Criando nova troca...");
        return trocaService.criarTroca(troca);
    }

    /**
     * Endpoint: GET /trocas/ativas
     * Lista todas as trocas ativas (para o Marketplace).
     * 
     * @return Lista de trocas ativas
     */
    public List<Troca> listarTrocasAtivas() {
        System.out.println("[CONTROLLER] Listando trocas ativas...");
        return trocaService.listarTrocasAtivas();
    }

    /**
     * Endpoint: GET /trocas/{id}
     * Busca uma troca específica pelo ID.
     * 
     * @param idTroca ID da troca
     * @return A troca encontrada
     */
    public Troca buscarTroca(int idTroca) {
        System.out.println("[CONTROLLER] Buscando troca: " + idTroca);
        return trocaService.buscarTroca(idTroca);
    }

    /**
     * Endpoint: GET /trocas/concluidas/{idJogador}
     * Lista todas as trocas concluídas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de trocas concluídas
     */
    public List<Troca> listarTrocasConcluidasDoJogador(int idJogador) {
        System.out.println("[CONTROLLER] Listando trocas concluídas do jogador: " + idJogador);
        return trocaService.listarTrocasConcluidasDoJogador(idJogador);
    }

    /**
     * Endpoint: GET /trocas/jogador/{idJogador}
     * Lista todas as trocas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de trocas do jogador
     */
    public List<Troca> listarTrocasDoJogador(int idJogador) {
        System.out.println("[CONTROLLER] Listando trocas do jogador: " + idJogador);
        return trocaService.listarTrocasDoJogador(idJogador);
    }

    /**
     * Endpoint: PUT /trocas/{id}/status
     * Atualiza o status de uma troca.
     * 
     * @param idTroca ID da troca
     * @param novoStatus Novo status
     * @return A troca atualizada
     */
    public Troca atualizarStatusTroca(int idTroca, String novoStatus) {
        System.out.println("[CONTROLLER] Atualizando status da troca: " + idTroca + " para " + novoStatus);
        return trocaService.atualizarStatusTroca(idTroca, novoStatus);
    }

    // ========== ENDPOINTS DE PROPOSTAS ==========

    /**
     * Endpoint: POST /propostas
     * Cria uma nova proposta simples.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @return A proposta criada
     */
    public Proposta enviarPropostaSimples(int idTroca, int idJogadorProponente) {
        System.out.println("[CONTROLLER] Enviando proposta simples para troca: " + idTroca);
        Proposta proposta = propostaService.criarPropostaSimples(idTroca, idJogadorProponente);
        
        // Notifica o ofertante sobre a nova proposta
        Troca troca = trocaService.buscarTroca(idTroca);
        if (troca != null) {
            notificacaoService.enviarNotificacao(
                troca.getIdJogadorOfertante(),
                "NovaPropostaRecebida",
                "Você recebeu uma nova proposta para a troca #" + idTroca
            );
        }
        
        return proposta;
    }

    /**
     * Endpoint: POST /propostas (com item extra)
     * Cria uma nova proposta com item extra.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @param itemExtra Descrição do item extra
     * @param quantidadeItemExtra Quantidade do item extra
     * @return A proposta criada
     */
    public Proposta enviarPropostaComItemExtra(int idTroca, int idJogadorProponente, 
                                               String itemExtra, int quantidadeItemExtra) {
        System.out.println("[CONTROLLER] Enviando proposta com item extra para troca: " + idTroca);
        Proposta proposta = propostaService.criarPropostaComItemExtra(
            idTroca, idJogadorProponente, itemExtra, quantidadeItemExtra
        );
        
        // Notifica o ofertante sobre a nova proposta
        Troca troca = trocaService.buscarTroca(idTroca);
        if (troca != null) {
            notificacaoService.enviarNotificacao(
                troca.getIdJogadorOfertante(),
                "NovaPropostaRecebida",
                "Você recebeu uma nova proposta com item extra para a troca #" + idTroca
            );
        }
        
        return proposta;
    }

    /**
     * Endpoint: GET /trocas/{id}/propostas
     * Lista todas as propostas de uma troca.
     * 
     * @param idTroca ID da troca
     * @return Lista de propostas da troca
     */
    public List<Proposta> listarPropostasDeUmaTroca(int idTroca) {
        System.out.println("[CONTROLLER] Listando propostas da troca: " + idTroca);
        return propostaService.listarPropostasDeUmaTroca(idTroca);
    }

    /**
     * Endpoint: PUT /propostas/{id}/aceitar
     * Aceita uma proposta de troca.
     * 
     * @param idProposta ID da proposta a ser aceita
     * @return A proposta atualizada
     */
    public Proposta aceitarProposta(int idProposta) {
        System.out.println("[CONTROLLER] Aceitando proposta: " + idProposta);
        Proposta proposta = trocaService.processarProposta(idProposta, true);
        
        // Notifica o proponente sobre a aceitação
        if (proposta != null) {
            notificacaoService.enviarNotificacao(
                proposta.getIdJogadorProponente(),
                "PropostaAceita",
                "Sua proposta #" + idProposta + " foi aceita!"
            );
        }
        
        return proposta;
    }

    /**
     * Endpoint: PUT /propostas/{id}/recusar
     * Recusa uma proposta de troca.
     * 
     * @param idProposta ID da proposta a ser recusada
     * @return A proposta atualizada
     */
    public Proposta recusarProposta(int idProposta) {
        System.out.println("[CONTROLLER] Recusando proposta: " + idProposta);
        Proposta proposta = trocaService.processarProposta(idProposta, false);
        
        // Notifica o proponente sobre a recusa
        if (proposta != null) {
            notificacaoService.enviarNotificacao(
                proposta.getIdJogadorProponente(),
                "PropostaRecusada",
                "Sua proposta #" + idProposta + " foi recusada."
            );
        }
        
        return proposta;
    }

    // ========== ENDPOINTS DE NOTIFICAÇÕES ==========

    /**
     * Endpoint: GET /notificacoes/{idJogador}
     * Lista todas as notificações de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de notificações do jogador
     */
    public List<?> listarNotificacoesDoJogador(int idJogador) {
        System.out.println("[CONTROLLER] Listando notificações do jogador: " + idJogador);
        return (List<?>) (List<?>) notificacaoService.listarNotificacoesDoJogador(idJogador);
    }

    /**
     * Endpoint: GET /notificacoes/{idJogador}/nao-lidas
     * Lista todas as notificações não lidas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de notificações não lidas
     */
    public List<?> listarNotificacoesNaoLidas(int idJogador) {
        System.out.println("[CONTROLLER] Listando notificações não lidas do jogador: " + idJogador);
        return (List<?>) (List<?>) notificacaoService.listarNotificacoesNaoLidas(idJogador);
    }

    /**
     * Endpoint: PUT /notificacoes/{id}/marcar-como-lida
     * Marca uma notificação como lida.
     * 
     * @param idNotificacao ID da notificação
     */
    public void marcarNotificacaoComoLida(int idNotificacao) {
        System.out.println("[CONTROLLER] Marcando notificação como lida: " + idNotificacao);
        notificacaoService.marcarComoLida(idNotificacao);
    }
}
