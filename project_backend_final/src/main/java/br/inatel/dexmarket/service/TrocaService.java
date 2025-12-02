package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.repository.PropostaRepository;
import br.inatel.dexmarket.repository.TrocaRepository;
import br.inatel.dexmarket.strategy.ValidacaoTrocaNormal;
import br.inatel.dexmarket.strategy.ValidacaoTrocaStrategy;

import java.util.List;

/**
 * Classe TrocaService - Camada Service (Lógica de Negócio)
 * Responsável por implementar as regras de negócio para Trocas.
 * 
 * Padrão Strategy: Utiliza uma estratégia de validação de troca.
 */
public class TrocaService {

    private final TrocaRepository trocaRepository;
    private final PropostaRepository propostaRepository;
    private ValidacaoTrocaStrategy estrategiaValidacao;

    public TrocaService(TrocaRepository trocaRepository, PropostaRepository propostaRepository) {
        this.trocaRepository = trocaRepository;
        this.propostaRepository = propostaRepository;
        // **Padrão Strategy**: Inicializa com a estratégia padrão (ValidacaoTrocaNormal)
        this.estrategiaValidacao = new ValidacaoTrocaNormal();
    }

    // --- Métodos de Negócio ---

    /**
     * Busca uma troca pelo ID.
     * @param idTroca ID da troca.
     * @return A troca encontrada.
     */
    public Troca buscarTroca(int idTroca) {
        return trocaRepository.findById(idTroca);
    }

    /**
     * Cria uma nova troca, aplicando a validação da estratégia configurada.
     * @param troca A troca a ser criada.
     * @return A troca criada.
     */
    public Troca criarTroca(Troca troca) {
        // **Padrão Strategy em ação**: A validação é delegada à estratégia configurada.
        if (!estrategiaValidacao.validar(troca)) {
            throw new IllegalArgumentException("Troca inválida de acordo com a estratégia de validação.");
        }
        // O Observer Pattern será ativado ao salvar a troca, se o status mudar.
        return trocaRepository.save(troca);
    }

    /**
     * Lista todas as trocas ativas no marketplace.
     * (Endpoint 1: Pagina MarketPlace)
     * @return Lista de trocas com status "Ativa".
     */
    public List<Troca> listarTrocasAtivas() {
        return trocaRepository.findByStatus("Ativa");
    }

    /**
     * Lista todas as propostas recebidas por uma troca ativa.
     * (Endpoint 2)
     * @param idTroca ID da troca.
     * @return Lista de propostas para a troca.
     */
    public List<Proposta> listarPropostasPorTroca(int idTroca) {
        return propostaRepository.findByIdTroca(idTroca);
    }

    /**
     * Lista todas as trocas concluídas por um jogador.
     * (Endpoint 3)
     * @param idJogador ID do jogador.
     * @return Lista de trocas concluídas.
     */
    public List<Troca> listarTrocasConcluidasDoJogador(int idJogador) {
        // Assume que o TrocaRepository tem um método para buscar por jogador e status
        return trocaRepository.findByJogadorAndStatus(idJogador, "Concluída");
    }

    /**
     * Processa uma proposta (aceitar ou rejeitar).
     * (Endpoint: Aceitar Proposta)
     * @param idProposta ID da proposta.
     * @param aceitar Se a proposta deve ser aceita (true) ou rejeitada (false).
     * @return A proposta processada.
     */
    public Proposta processarProposta(int idProposta, boolean aceitar) {
        Proposta proposta = propostaRepository.findById(idProposta);
        if (proposta == null) {
            throw new IllegalArgumentException("Proposta não encontrada.");
        }

        Troca troca = trocaRepository.findById(proposta.getIdTroca());
        if (troca == null) {
            throw new IllegalArgumentException("Troca associada à proposta não encontrada.");
        }

        if (aceitar) {
            // Lógica de aceitar proposta:
            // 1. Mudar status da troca para "Concluída"
            // 2. Mudar status da proposta para "Aceita"
            // 3. **Padrão Observer**: A classe Troca (Subject) notifica seus Observers (NotificacaoService)
            //    quando o status muda para "Concluída". (A implementação do Observer está na classe Troca).
            troca.setStatus("Concluída");
            trocaRepository.save(troca);
            proposta.setStatus("Aceita");
            propostaRepository.save(proposta);
        } else {
            proposta.setStatus("Rejeitada");
            propostaRepository.save(proposta);
        }
        return proposta;
    }

    // --- Strategy Pattern Setter ---

    /**
     * Define a estratégia de validação de troca.
     * @param estrategiaValidacao A nova estratégia.
     */
    public void setEstrategiaValidacao(ValidacaoTrocaStrategy estrategiaValidacao) {
        this.estrategiaValidacao = estrategiaValidacao;
    }
}
