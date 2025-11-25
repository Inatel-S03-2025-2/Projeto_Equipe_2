package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.repository.TrocaRepository;
import br.inatel.dexmarket.repository.PropostaRepository;
import br.inatel.dexmarket.strategy.ValidacaoTrocaStrategy;
import br.inatel.dexmarket.strategy.ValidacaoTrocaNormal;
import java.util.List;

/**
 * Classe TrocaService - Service de Negócio
 * Gerencia a lógica de negócio relacionada a trocas.
 * 
 * Padrão Strategy: Utiliza diferentes estratégias de validação de trocas.
 * Padrão Repository: Utiliza repositórios para acesso a dados.
 * Padrão Injeção de Dependência: Recebe dependências pelo construtor.
 */
public class TrocaService {
    private TrocaRepository trocaRepository;
    private PropostaRepository propostaRepository;
    private ValidacaoTrocaStrategy estrategiaValidacao;

    // Construtor com injeção de dependências
    public TrocaService(TrocaRepository trocaRepository, PropostaRepository propostaRepository) {
        this.trocaRepository = trocaRepository;
        this.propostaRepository = propostaRepository;
        // Define a estratégia padrão
        this.estrategiaValidacao = new ValidacaoTrocaNormal();
    }

    /**
     * Define a estratégia de validação a ser utilizada.
     * 
     * @param estrategia A estratégia de validação
     */
    public void setEstrategiaValidacao(ValidacaoTrocaStrategy estrategia) {
        this.estrategiaValidacao = estrategia;
        System.out.println("Estratégia de validação alterada para: " + estrategia.getDescricao());
    }

    /**
     * Cria uma nova troca.
     * 
     * @param troca A troca a ser criada
     * @return A troca criada (com ID atribuído)
     */
    public Troca criarTroca(Troca troca) {
        // Valida a troca usando a estratégia definida
        if (!estrategiaValidacao.validar(troca)) {
            throw new IllegalArgumentException("Troca inválida: " + estrategiaValidacao.getDescricao());
        }

        // Salva a troca no repositório
        return trocaRepository.save(troca);
    }

    /**
     * Busca uma troca pelo ID.
     * 
     * @param idTroca ID da troca
     * @return A troca encontrada
     */
    public Troca buscarTroca(int idTroca) {
        return trocaRepository.findById(idTroca);
    }

    /**
     * Lista todas as trocas ativas.
     * 
     * @return Lista de trocas com status "Ativa"
     */
    public List<Troca> listarTrocasAtivas() {
        return trocaRepository.findByStatus("Ativa");
    }

    /**
     * Lista todas as trocas concluídas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de trocas concluídas
     */
    public List<Troca> listarTrocasConcluidasDoJogador(int idJogador) {
        List<Troca> trocas = trocaRepository.findByIdJogadorOfertante(idJogador);
        trocas.removeIf(t -> !t.getStatus().equals("Concluída"));
        return trocas;
    }

    /**
     * Lista todas as trocas de um jogador.
     * 
     * @param idJogador ID do jogador
     * @return Lista de trocas do jogador
     */
    public List<Troca> listarTrocasDoJogador(int idJogador) {
        return trocaRepository.findByIdJogadorOfertante(idJogador);
    }

    /**
     * Atualiza o status de uma troca.
     * 
     * @param idTroca ID da troca
     * @param novoStatus Novo status
     * @return A troca atualizada
     */
    public Troca atualizarStatusTroca(int idTroca, String novoStatus) {
        Troca troca = trocaRepository.findById(idTroca);
        if (troca != null) {
            troca.setStatus(novoStatus); // Isso dispara o notifyObservers()
            return trocaRepository.update(troca);
        }
        System.out.println("Troca não encontrada: " + idTroca);
        return null;
    }

    /**
     * Processa uma proposta (aceita ou recusa).
     * 
     * @param idProposta ID da proposta
     * @param aceitar true para aceitar, false para recusar
     * @return A proposta processada
     */
    public Proposta processarProposta(int idProposta, boolean aceitar) {
        Proposta proposta = propostaRepository.findById(idProposta);
        if (proposta == null) {
            System.out.println("Proposta não encontrada: " + idProposta);
            return null;
        }

        if (aceitar) {
            proposta.setStatus("Aceita");
            System.out.println("Proposta aceita: " + idProposta);
            
            // Atualiza a troca para "Concluída"
            Troca troca = trocaRepository.findById(proposta.getIdTroca());
            if (troca != null) {
                atualizarStatusTroca(troca.getIdTroca(), "Concluída");
            }
        } else {
            proposta.setStatus("Recusada");
            System.out.println("Proposta recusada: " + idProposta);
        }

        return propostaRepository.update(proposta);
    }

    /**
     * Lista todas as propostas de uma troca.
     * 
     * @param idTroca ID da troca
     * @return Lista de propostas da troca
     */
    public List<Proposta> listarPropostasDeUmaTroca(int idTroca) {
        return propostaRepository.findByIdTroca(idTroca);
    }

    /**
     * Deleta uma troca.
     * 
     * @param idTroca ID da troca a ser deletada
     * @return true se foi deletada, false caso contrário
     */
    public boolean deletarTroca(int idTroca) {
        return trocaRepository.delete(idTroca);
    }
}
