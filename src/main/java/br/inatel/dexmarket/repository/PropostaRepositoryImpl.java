package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Proposta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe PropostaRepositoryImpl - Implementação de PropostaRepository
 * Implementação em memória do repositório de Propostas.
 */
public class PropostaRepositoryImpl implements PropostaRepository {
    private Map<Integer, Proposta> propostaMap = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Proposta save(Proposta proposta) {
        if (proposta.getIdProposta() == 0) {
            proposta.setIdProposta(proximoId++);
        }
        propostaMap.put(proposta.getIdProposta(), proposta);
        System.out.println("Proposta salva: ID " + proposta.getIdProposta() + 
                         ", Troca: " + proposta.getIdTroca() + 
                         ", Status: " + proposta.getStatus());
        return proposta;
    }

    @Override
    public Proposta findById(int id) {
        return propostaMap.get(id);
    }

    @Override
    public List<Proposta> findByIdTroca(int idTroca) {
        List<Proposta> propostas = new ArrayList<>();
        for (Proposta proposta : propostaMap.values()) {
            if (proposta.getIdTroca() == idTroca) {
                propostas.add(proposta);
            }
        }
        return propostas;
    }

    @Override
    public List<Proposta> findByIdJogadorProponente(int idJogadorProponente) {
        List<Proposta> propostas = new ArrayList<>();
        for (Proposta proposta : propostaMap.values()) {
            if (proposta.getIdJogadorProponente() == idJogadorProponente) {
                propostas.add(proposta);
            }
        }
        return propostas;
    }

    @Override
    public List<Proposta> findByStatus(String status) {
        List<Proposta> propostas = new ArrayList<>();
        for (Proposta proposta : propostaMap.values()) {
            if (proposta.getStatus().equals(status)) {
                propostas.add(proposta);
            }
        }
        return propostas;
    }

    @Override
    public Proposta update(Proposta proposta) {
        if (propostaMap.containsKey(proposta.getIdProposta())) {
            propostaMap.put(proposta.getIdProposta(), proposta);
            System.out.println("Proposta atualizada: ID " + proposta.getIdProposta());
            return proposta;
        }
        System.out.println("Proposta não encontrada para atualização: " + proposta.getIdProposta());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (propostaMap.containsKey(id)) {
            propostaMap.remove(id);
            System.out.println("Proposta deletada: ID " + id);
            return true;
        }
        System.out.println("Proposta não encontrada para deleção: " + id);
        return false;
    }
}
