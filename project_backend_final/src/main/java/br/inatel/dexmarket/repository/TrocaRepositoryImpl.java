package br.inatel.dexmarket.repository;

import br.inatel.dexmarket.model.Troca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe TrocaRepositoryImpl - Implementação de TrocaRepository
 * Implementação em memória do repositório de Trocas.
 * 
 * **Padrão Singleton (Implícito)**: Embora a classe não implemente o Singleton de forma estrita (com getInstance()),
 * no contexto da simulação (Main.java e Testes), uma única instância deste repositório é criada e injetada
 * nos Services, garantindo que todos os Services compartilhem o mesmo estado de dados em memória.
 * Em um ambiente Spring, o Spring faria isso automaticamente com a anotação @Singleton ou @Service.
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
    public List<Troca> findByJogadorAndStatus(int idJogador, String status) {
        List<Troca> trocas = new ArrayList<>();
        for (Troca troca : trocaMap.values()) {
            // Verifica se o jogador é o ofertante OU se o jogador é o proponente da proposta aceita (simulação)
            // Como não temos a informação do proponente aceito na Troca, vamos considerar apenas o ofertante.
            // Em um sistema real, a Troca teria o ID do proponente vencedor.
            if (troca.getStatus().equals(status) && troca.getIdJogadorOfertante() == idJogador) {
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
