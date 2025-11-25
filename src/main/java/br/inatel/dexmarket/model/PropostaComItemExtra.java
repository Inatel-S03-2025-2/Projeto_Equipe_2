package br.inatel.dexmarket.model;

/**
 * Classe PropostaComItemExtra - Implementação Concreta de Proposta
 * Representa uma proposta com um item extra (ex: moeda, item especial).
 * 
 * Padrão Factory: Esta é outra implementação concreta que será criada pela PropostaFactory.
 */
public class PropostaComItemExtra extends Proposta {
    private String itemExtra; // Descrição do item extra (ex: "100 moedas", "Poção Rara")
    private int quantidadeItemExtra;

    public PropostaComItemExtra() {
        super();
    }

    public PropostaComItemExtra(int idTroca, int idJogadorProponente, String itemExtra, int quantidadeItemExtra) {
        super(idTroca, idJogadorProponente);
        this.itemExtra = itemExtra;
        this.quantidadeItemExtra = quantidadeItemExtra;
    }

    // Getters e Setters
    public String getItemExtra() {
        return itemExtra;
    }

    public void setItemExtra(String itemExtra) {
        this.itemExtra = itemExtra;
    }

    public int getQuantidadeItemExtra() {
        return quantidadeItemExtra;
    }

    public void setQuantidadeItemExtra(int quantidadeItemExtra) {
        this.quantidadeItemExtra = quantidadeItemExtra;
    }

    @Override
    public boolean validar() {
        // Validação: proposta deve ter pelo menos um Pokémon e um item extra válido
        if (this.pokemonsOfertados == null || this.pokemonsOfertados.isEmpty()) {
            System.out.println("Erro: Proposta com item extra deve ter pelo menos um Pokémon ofertado.");
            return false;
        }
        if (this.itemExtra == null || this.itemExtra.isEmpty() || this.quantidadeItemExtra <= 0) {
            System.out.println("Erro: Item extra inválido.");
            return false;
        }
        System.out.println("Proposta com item extra validada com sucesso.");
        return true;
    }

    @Override
    public String getDescricao() {
        return "Proposta com Item Extra - " + this.pokemonsOfertados.size() + " Pokémon(s) + " + 
               this.quantidadeItemExtra + "x " + this.itemExtra;
    }

    @Override
    public String toString() {
        return "PropostaComItemExtra{" +
                "idProposta=" + idProposta +
                ", idTroca=" + idTroca +
                ", idJogadorProponente=" + idJogadorProponente +
                ", status='" + status + '\'' +
                ", itemExtra='" + itemExtra + '\'' +
                ", quantidadeItemExtra=" + quantidadeItemExtra +
                ", descricao='" + getDescricao() + '\'' +
                '}';
    }
}
