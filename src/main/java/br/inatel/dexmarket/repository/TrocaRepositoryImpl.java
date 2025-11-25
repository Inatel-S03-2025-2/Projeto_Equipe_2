package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Troca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe TrocaRepositoryImpl - Implementação de TrocaRepository
 * Implementação em memória do repositório de Trocas.
 */
public class TrocaRepositoryImpl implements TrocaRepository {
    private Map<Integer, Troca> trocaMap = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Troca save(Troca troca) {
        if (troca.getIdTroca() == 0) {
            troca.setIdTroca(proximoId++);
        }
        trocaMap.put(troca.getIdTroca(), troca);
        System.out.println("Troca salva: ID " + troca.getIdTroca() + ", Status: " + troca.getStatus());
        return troca;
    }

    @Override
    public Troca findById(int id) {
        return trocaMap.get(id);
    }

    @Override
    public List<Troca> findAll() {
        return new ArrayList<>(trocaMap.values());
    }

    @Override
    public List<Troca> findByStatus(String status) {
        List<Troca> trocas = new ArrayList<>();
        for (Troca troca : trocaMap.values()) {
            if (troca.getStatus().equals(status)) {
                trocas.add(troca);
            }
        }
        return trocas;
    }

    @Override
    public List<Troca> findByIdJogadorOfertante(int idJogadorOfertante) {
        List<Troca> trocas = new ArrayList<>();
        for (Troca troca : trocaMap.values()) {
            if (troca.getIdJogadorOfertante() == idJogadorOfertante) {
                trocas.add(troca);
            }
        }
        return trocas;
    }

    @Override
    public Troca update(Troca troca) {
        if (trocaMap.containsKey(troca.getIdTroca())) {
            trocaMap.put(troca.getIdTroca(), troca);
            System.out.println("Troca atualizada: ID " + troca.getIdTroca());
            return troca;
        }
        System.out.println("Troca não encontrada para atualização: " + troca.getIdTroca());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (trocaMap.containsKey(id)) {
            trocaMap.remove(id);
            System.out.println("Troca deletada: ID " + id);
            return true;
        }
        System.out.println("Troca não encontrada para deleção: " + id);
        return false;
    }
}
