package br.inatel.dexmarket.model;

/**
 * Classe PropostaSimples - Implementação Concreta de Proposta
 * Representa uma proposta simples de troca (1:1 ou N:N de Pokémons).
 * 
 * Padrão Factory: Esta é uma das implementações concretas que será criada pela PropostaFactory.
 */
public class PropostaSimples extends Proposta {

    public PropostaSimples() {
        super();
    }

    public PropostaSimples(int idTroca, int idJogadorProponente) {
        super(idTroca, idJogadorProponente);
    }

    @Override
    public boolean validar() {
        // Validação simples: proposta deve ter pelo menos um Pokémon ofertado
        if (this.pokemonsOfertados == null || this.pokemonsOfertados.isEmpty()) {
            System.out.println("Erro: Proposta simples deve ter pelo menos um Pokémon ofertado.");
            return false;
        }
        System.out.println("Proposta simples validada com sucesso.");
        return true;
    }

    @Override
    public String getDescricao() {
        return "Proposta Simples - " + this.pokemonsOfertados.size() + " Pokémon(s) ofertado(s)";
    }

    @Override
    public String toString() {
        return "PropostaSimples{" +
                "idProposta=" + idProposta +
                ", idTroca=" + idTroca +
                ", idJogadorProponente=" + idJogadorProponente +
                ", status='" + status + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                '}';
    }
}
