package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Notificacao;
import br.inatel.dexmarket.model.Notificacao.TipoNotificacao;
import br.inatel.dexmarket.repository.NotificacaoRepository;

/**
 * Serviço de notificações implementando padrão SINGLETON
 * 
 * Responsabilidades:
 * - Gerenciar envio de notificações
 * - Garantir única instância do serviço no sistema
 * - Integrar com diferentes canais de notificação (futuro: email, WebSocket, push)
 * - Coordenar com o repositório de notificações
 * 
 * Padrão: Singleton (Creational)
 * Thread-Safety: Sim (eager initialization)
 * 
 * Este serviço segue o princípio da Responsabilidade Única (SRP):
 * - Foca apenas em ENVIAR notificações
 * - Delega persistência ao NotificacaoRepository
 * - Não contém lógica de negócio de trocas
 * 
 * @author DexMarket Team
 * @version 1.0
 */
public class NotificacaoService {
    
    // ================== SINGLETON PATTERN ==================
    
    /**
     * Instância única do serviço (Eager Initialization)
     * Thread-safe: ClassLoader garante inicialização única
     */
    private static final NotificacaoService INSTANCE = new NotificacaoService();
    
    /**
     * Repository para persistência de notificações
     * Também é Singleton - sempre a mesma instância
     */
    private final NotificacaoRepository notificacaoRepository;
    
    // ================== CONSTRUTOR PRIVADO ==================
    
    /**
     * Construtor privado - impede instanciação externa
     * Inicializa dependências necessárias
     */
    private NotificacaoService() {
        this.notificacaoRepository = NotificacaoRepository.getInstance();
        System.out.println("📬 NotificacaoService inicializado (Singleton)");
    }
    
    // ================== ACESSO À INSTÂNCIA ==================
    
    /**
     * Retorna a instância única do serviço
     * 
     * @return instância singleton do NotificacaoService
     */
    public static NotificacaoService getInstance() {
        return INSTANCE;
    }
    
    // ================== MÉTODOS DE NEGÓCIO ==================
    
    /**
     * Envia uma notificação completa
     * 
     * Este é o método principal que coordena:
     * 1. Validação da notificação
     * 2. Persistência no repositório
     * 3. Envio para canais externos (futuro)
     * 
     * @param notificacao notificação a ser enviada
     * @throws IllegalArgumentException se notificação for inválida
     */
    public void enviarNotificacao(Notificacao notificacao) {
        if (notificacao == null) {
            throw new IllegalArgumentException("Notificação não pode ser nula");
        }
        
        // 1. Salva no repositório (histórico)
        notificacaoRepository.salvar(notificacao);
        
        // 2. Envia para canais externos (simulado)
        simularEnvioExterno(notificacao);
        
        // 3. Log de confirmação
        System.out.println("📬 Notificação enviada com sucesso: " + notificacao);
    }
    
    /**
     * Notifica um jogador específico sobre um evento
     * 
     * Método de conveniência que cria e envia notificação em uma única operação
     * 
     * @param nomeJogador nome do jogador a ser notificado
     * @param mensagem conteúdo da mensagem
     */
    public void notificarJogador(String nomeJogador, String mensagem) {
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            System.err.println("⚠️  Nome do jogador inválido");
            return;
        }
        
        Notificacao notificacao = new Notificacao(
            nomeJogador, 
            mensagem, 
            TipoNotificacao.NOVA_TROCA
        );
        
        enviarNotificacao(notificacao);
    }
    
    /**
     * Notifica sobre troca aceita
     * 
     * @param nomeJogador nome do jogador
     * @param mensagem detalhes da troca aceita
     */
    public void notificarTrocaAceita(String nomeJogador, String mensagem) {
        Notificacao notificacao = new Notificacao(
            nomeJogador,
            mensagem,
            TipoNotificacao.TROCA_ACEITA
        );
        enviarNotificacao(notificacao);
    }
    
    /**
     * Notifica sobre troca recusada
     * 
     * @param nomeJogador nome do jogador
     * @param mensagem motivo da recusa
     */
    public void notificarTrocaRecusada(String nomeJogador, String mensagem) {
        Notificacao notificacao = new Notificacao(
            nomeJogador,
            mensagem,
            TipoNotificacao.TROCA_RECUSADA
        );
        enviarNotificacao(notificacao);
    }
    
    /**
     * Notifica serviço externo (analytics, logs, etc)
     * 
     * Usado para integração com sistemas externos:
     * - Sistema de analytics
     * - Auditoria
     * - Monitoramento
     * - Webhooks
     * 
     * @param mensagem mensagem para o serviço externo
     */
    public void notificarServicoExterno(String mensagem) {
        Notificacao notificacao = new Notificacao(
            "SISTEMA_EXTERNO", 
            mensagem, 
            TipoNotificacao.SISTEMA
        );
        
        enviarNotificacao(notificacao);
        
        // Simula integração externa
        System.out.println("🔔 Serviço externo notificado: " + mensagem);
    }
    
    // ================== MÉTODOS DE CONSULTA ==================
    
    /**
     * Retorna total de notificações enviadas
     * 
     * @return quantidade de notificações no sistema
     */
    public int getTotalNotificacoes() {
        return notificacaoRepository.contarNotificacoes();
    }
    
    /**
     * Busca notificações de um jogador específico
     * 
     * @param nomeJogador nome do jogador
     * @return lista de notificações do jogador
     */
    public java.util.List<Notificacao> buscarNotificacoesJogador(String nomeJogador) {
        return notificacaoRepository.buscarPorDestinatario(nomeJogador);
    }
    
    /**
     * Exibe estatísticas de notificações
     */
    public void exibirEstatisticas() {
        notificacaoRepository.exibirEstatisticas();
    }
    
    // ================== MÉTODOS PRIVADOS ==================
    
    /**
     * Simula envio para canais externos
     * 
     * Em produção, aqui seria:
     * - Envio de email (JavaMail)
     * - Push notification (Firebase)
     * - WebSocket (STOMP)
     * - SMS (Twilio)
     * - Slack/Discord webhook
     * 
     * @param notificacao notificação a ser enviada
     */
    private void simularEnvioExterno(Notificacao notificacao) {
        // Simula delay de rede (comentar em produção)
        try {
            Thread.sleep(10); // 10ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Log simulando diferentes canais
        switch (notificacao.getTipo()) {
            case NOVA_TROCA:
            case TROCA_ACEITA:
            case TROCA_RECUSADA:
                // Simula envio WebSocket para jogador online
                System.out.println("   📱 [WebSocket] → " + notificacao.getDestinatario());
                break;
            case SISTEMA:
                // Simula envio para sistema de analytics
                System.out.println("   📊 [Analytics] → Evento registrado");
                break;
            default:
                System.out.println("   📧 [Email] → " + notificacao.getDestinatario());
        }
    }
    
    /**
     * Formata mensagem de notificação com template
     * 
     * @param template template da mensagem
     * @param params parâmetros para substituir no template
     * @return mensagem formatada
     */
    private String formatarMensagem(String template, Object... params) {
        return String.format(template, params);
    }
}