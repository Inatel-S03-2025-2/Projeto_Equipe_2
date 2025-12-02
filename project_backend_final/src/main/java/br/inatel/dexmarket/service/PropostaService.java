package br.inatel.dexmarket.service;

import br.inatel.dexmarket.model.Proposta;
import br.inatel.dexmarket.repository.PropostaRepository;
import br.inatel.dexmarket.factory.PropostaFactory;

import java.util.List;

/**
 * Classe PropostaService - Service de Negócio
 * Gerencia a lógica de negócio relacionada a propostas.
 * 
 * **Padrão Factory (Cliente)**: Utiliza a PropostaFactory para criar diferentes tipos de propostas.
 * Padrão Repository: Utiliza repositório para acesso a dados.
 */
public class PropostaService {
    private PropostaRepository propostaRepository;

    // Construtor com injeção de dependência
    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    /**
     * Cria uma nova proposta simples.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @return A proposta criada
     */
    public Proposta criarPropostaSimples(int idTroca, int idJogadorProponente) {
        // **Padrão Factory em ação**: Delega a criação à Factory
        Proposta proposta = PropostaFactory.criarPropostaSimples(idTroca, idJogadorProponente);
        
        // Valida a proposta
        if (!proposta.validar()) {
            throw new IllegalArgumentException("Proposta simples inválida");
        }

        // Salva a proposta no repositório
        return propostaRepository.save(proposta);
    }

    /**
     * Cria uma nova proposta com item extra.
     * 
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @param itemExtra Descrição do item extra
     * @param quantidadeItemExtra Quantidade do item extra
     * @return A proposta criada
     */
    public Proposta criarPropostaComItemExtra(int idTroca, int idJogadorProponente, 
                                              String itemExtra, int quantidadeItemExtra) {
        // **Padrão Factory em ação**: Delega a criação à Factory
        Proposta proposta = PropostaFactory.criarPropostaComItemExtra(
            idTroca, idJogadorProponente, itemExtra, quantidadeItemExtra
        );
        
        // Valida a proposta
        if (!proposta.validar()) {
            throw new IllegalArgumentException("Proposta com item extra inválida");
        }

        // Salva a proposta no repositório
        return propostaRepository.save(proposta);
    }

    /**
     * Cria uma proposta genérica usando a factory.
     * 
     * @param tipo Tipo de proposta
     * @param idTroca ID da troca
     * @param idJogadorProponente ID do jogador que faz a proposta
     * @param parametrosAdicionais Parâmetros adicionais específicos do tipo
     * @return A proposta criada
     */
    public Proposta criarProposta(String tipo, int idTroca, int idJogadorProponente, 
                                  Object... parametrosAdicionais) {
        // **Padrão Factory em ação**: Delega a criação à Factory
        Proposta proposta = PropostaFactory.criarProposta(tipo, idTroca, idJogadorProponente, parametrosAdicionais);
        
        // Valida a proposta
        if (!proposta.validar()) {
            throw new IllegalArgumentException("Proposta inválida: " + tipo);
        }

        // Salva a proposta no repositório
        return propostaRepository.save(proposta);
    }

    /**
     * Busca uma proposta pelo ID.
     * 
     * @param idProposta ID da proposta
     * @return A proposta encontrada
     */
    public Proposta buscarProposta(int idProposta) {
        return propostaRepository.findById(idProposta);
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
     * Envia uma nova proposta (genérica).
     * 
     * @param proposta A proposta a ser enviada.
     * @return A proposta criada.
     */
    public Proposta enviarProposta(Proposta proposta) {
        // Validação básica (ex: se a proposta é válida)
        if (!proposta.validar()) {
            throw new IllegalArgumentException("Proposta inválida.");
        }
        proposta.setStatus("Pendente");
        return propostaRepository.save(proposta);
    }

    /**
     * Lista todas as propostas de um proponente.
     * 
     * @param idJogadorProponente ID do jogador proponente
     * @return Lista de propostas do jogador
     */
    public List<Proposta> listarPropostasDoJogador(int idJogadorProponente) {
        return propostaRepository.findByIdJogadorProponente(idJogadorProponente);
    }

    /**
     * Lista todas as propostas com um status específico.
     * 
     * @param status Status das propostas
     * @return Lista de propostas com o status especificado
     */
    public List<Proposta> listarPropostasPorStatus(String status) {
        return propostaRepository.findByStatus(status);
    }

    /**
     * Atualiza o status de uma proposta.
     * 
     * @param idProposta ID da proposta
     * @param novoStatus Novo status
     * @return A proposta atualizada
     */
    public Proposta atualizarStatusProposta(int idProposta, String novoStatus) {
        Proposta proposta = propostaRepository.findById(idProposta);
        if (proposta != null) {
            proposta.setStatus(novoStatus);
            return propostaRepository.update(proposta);
        }
        System.out.println("Proposta não encontrada: " + idProposta);
        return null;
    }

    /**
     * Deleta uma proposta.
     * 
     * @param idProposta ID da proposta a ser deletada
     * @return true se foi deletada, false caso contrário
     */
    public boolean deletarProposta(int idProposta) {
        return propostaRepository.delete(idProposta);
    }
}
