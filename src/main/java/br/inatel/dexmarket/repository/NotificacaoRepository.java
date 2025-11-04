package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Notificacao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositório de notificações implementando padrão SINGLETON
 * 
 * Responsabilidades:
 * - Manter única instância do repositório
 * - Simular persistência em memória (futuramente será banco de dados)
 * - Fornecer operações CRUD básicas
 * 
 * Padrão: Singleton (Creational)
 * Thread-Safety: Sim (eager initialization)
 * 
 * @author DexMarket Team
 * @version 1.0
 */
public class NotificacaoRepository {
    
    // ================== SINGLETON PATTERN ==================
    
    /**
     * Instância única do repositório (Eager Initialization)
     * Criada no momento do carregamento da classe
     * Thread-safe por padrão (ClassLoader é thread-safe)
     */
    private static final NotificacaoRepository INSTANCE = new NotificacaoRepository();
    
    /**
     * "Banco de dados" em memória
     * Simula tabela de notificações
     * Em produção, seria substituído por JPA/Hibernate
     */
    private final List<Notificacao> notificacoes;
    
    // ================== CONSTRUTOR PRIVADO ==================
    
    /**
     * Construtor privado - impede instanciação externa
     * Apenas a própria classe pode criar instâncias
     * Garante o padrão Singleton
     */
    private NotificacaoRepository() {
        this.notificacoes = new ArrayList<>();
        System.out.println("🗄️  NotificacaoRepository inicializado (Singleton)");
    }
    
    // ================== ACESSO À INSTÂNCIA ==================
    
    /**
     * Retorna a instância única do repositório
     * 
     * @return instância singleton do repositório
     */
    public static NotificacaoRepository getInstance() {
        return INSTANCE;
    }
    
    // ================== OPERAÇÕES CRUD ==================
    
    /**
     * Salva uma notificação no repositório
     * 
     * @param notificacao notificação a ser salva
     * @throws IllegalArgumentException se notificação for null
     */
    public void salvar(Notificacao notificacao) {
        if (notificacao == null) {
            throw new IllegalArgumentException("Notificação não pode ser nula");
        }
        notificacoes.add(notificacao);
        System.out.println("💾 Notificação salva no repositório");
    }
    
    /**
     * Lista todas as notificações do sistema
     * 
     * @return cópia da lista de notificações (imutabilidade)
     */
    public List<Notificacao> listarTodas() {
        return new ArrayList<>(notificacoes);
    }
    
    /**
     * Conta o total de notificações no repositório
     * 
     * @return quantidade de notificações
     */
    public int contarNotificacoes() {
        return notificacoes.size();
    }
    
    /**
     * Busca notificações por destinatário
     * 
     * @param destinatario nome do destinatário
     * @return lista de notificações do destinatário
     */
    public List<Notificacao> buscarPorDestinatario(String destinatario) {
        if (destinatario == null || destinatario.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return notificacoes.stream()
                .filter(n -> n.getDestinatario().equals(destinatario))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca notificações por tipo
     * 
     * @param tipo tipo de notificação
     * @return lista de notificações do tipo especificado
     */
    public List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo) {
        if (tipo == null) {
            return new ArrayList<>();
        }
        
        return notificacoes.stream()
                .filter(n -> n.getTipo() == tipo)
                .collect(Collectors.toList());
    }
    
    /**
     * Remove todas as notificações (útil para testes)
     */
    public void limparTodas() {
        notificacoes.clear();
        System.out.println("🗑️  Todas as notificações foram removidas");
    }
    
    /**
     * Retorna as últimas N notificações
     * 
     * @param quantidade número de notificações a retornar
     * @return lista com as últimas notificações
     */
    public List<Notificacao> buscarUltimas(int quantidade) {
        if (quantidade <= 0 || notificacoes.isEmpty()) {
            return new ArrayList<>();
        }
        
        int inicio = Math.max(0, notificacoes.size() - quantidade);
        return new ArrayList<>(notificacoes.subList(inicio, notificacoes.size()));
    }
    
    /**
     * Exibe estatísticas do repositório
     */
    public void exibirEstatisticas() {
        System.out.println("\n📊 Estatísticas do Repositório:");
        System.out.println("   Total de notificações: " + contarNotificacoes());
        System.out.println("   Tipos:");
        for (Notificacao.TipoNotificacao tipo : Notificacao.TipoNotificacao.values()) {
            long count = notificacoes.stream()
                    .filter(n -> n.getTipo() == tipo)
                    .count();
            if (count > 0) {
                System.out.println("     - " + tipo + ": " + count);
            }
        }
    }
}