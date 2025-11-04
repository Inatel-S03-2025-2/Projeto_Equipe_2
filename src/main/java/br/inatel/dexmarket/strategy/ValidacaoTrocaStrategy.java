package br.inatel.dexmarket.strategy;

import br.inatel.dexmarket.model.Troca;

/**
 * Interface Strategy para validação de trocas
 * 
 * Padrão: Strategy (Behavioral Pattern)
 * 
 * PROBLEMA RESOLVIDO:
 * - Múltiplas regras de validação precisam ser aplicadas a trocas
 * - Novas regras podem surgir no futuro (raridade, nível, região, etc)
 * - Validações devem ser intercambiáveis e configuráveis
 * - Código deve estar aberto para extensão, mas fechado para modificação (OCP)
 * 
 * SOLUÇÃO:
 * - Define uma família de algoritmos de validação
 * - Encapsula cada algoritmo em uma classe separada
 * - Torna os algoritmos intercambiáveis
 * - Cliente (TrocaService) não precisa conhecer detalhes das validações
 * 
 * BENEFÍCIOS:
 * ✅ Open/Closed Principle: novas estratégias sem modificar código existente
 * ✅ Single Responsibility: cada estratégia tem uma única responsabilidade
 * ✅ Testabilidade: estratégias podem ser testadas isoladamente
 * ✅ Flexibilidade: adicionar/remover estratégias em runtime
 * ✅ Manutenibilidade: lógica de validação isolada e organizada
 * 
 * QUANDO USAR:
 * - Quando você tem múltiplos algoritmos para a mesma tarefa
 * - Quando comportamentos devem ser selecionados em runtime
 * - Quando você quer evitar condicionais complexos (if/else, switch)
 * - Quando você precisa seguir o Open/Closed Principle
 * 
 * EXEMPLO DE USO:
 * <pre>
 * ValidacaoTrocaStrategy validacao = new ValidacaoPosseStrategy();
 * if (validacao.validar(troca)) {
 *     System.out.println("Troca válida!");
 * } else {
 *     System.out.println("Erro: " + validacao.getMensagemErro());
 * }
 * </pre>
 * 
 * PARTICIPANTES DO PATTERN:
 * - Strategy (esta interface): define interface comum
 * - ConcreteStrategy (ValidacaoPosseStrategy, etc): implementações específicas
 * - Context (TrocaService): usa as strategies
 * 
 * @author DexMarket Team
 * @version 1.0
 * @see br.inatel.dexmarket.service.TrocaService
 */
public interface ValidacaoTrocaStrategy {
    
    /**
     * Valida uma troca de acordo com a regra específica desta estratégia
     * 
     * Este é o MÉTODO PRINCIPAL do padrão Strategy.
     * Cada implementação define seu próprio algoritmo de validação.
     * 
     * Exemplos de validações:
     * - Treinador possui o Pokémon oferecido?
     * - Pokémon está disponível para troca?
     * - Troca é justa (mesmo tipo, raridade similar)?
     * - Treinador não está trocando consigo mesmo?
     * - Nível dos Pokémons é compatível?
     * 
     * @param troca objeto contendo dados da troca a ser validada
     * @return true se a troca passa na validação, false caso contrário
     * @throws IllegalArgumentException se troca for null
     */
    boolean validar(Troca troca);
    
    /**
     * Retorna mensagem de erro descritiva quando validação falha
     * 
     * Deve ser chamado APÓS validar() retornar false.
     * Fornece feedback claro ao usuário sobre o motivo da falha.
     * 
     * Boas práticas para mensagens:
     * - Clara e específica (não genérica)
     * - Orientada ao usuário (não técnica demais)
     * - Sugere solução quando possível
     * - Sem jargões desnecessários
     * 
     * Exemplos:
     * ✅ BOM: "Ash não possui Pikachu para trocar"
     * ❌ RUIM: "Validation failed: ownership check negative"
     * 
     * @return mensagem de erro legível pelo usuário
     */
    String getMensagemErro();
    
    /**
     * Retorna nome identificador da estratégia
     * 
     * Útil para:
     * - Logging e debug
     * - Exibir quais validações foram aplicadas
     * - Configuração dinâmica de estratégias
     * - Testes automatizados
     * 
     * Convenção de nomenclatura:
     * - Use "Validação de X" onde X é o aspecto validado
     * - Seja descritivo mas conciso
     * - Evite abreviações confusas
     * 
     * Exemplos:
     * - "Validação de Posse"
     * - "Validação de Disponibilidade"
     * - "Validação de Justiça"
     * - "Validação Anti-AutoTroca"
     * 
     * @return nome da estratégia de validação
     */
    String getNomeEstrategia();
    
    /**
     * (Opcional) Hook method para inicialização
     * 
     * Pode ser sobrescrito por implementações que precisam
     * carregar recursos ou fazer setup inicial.
     * 
     * Chamado uma vez antes da primeira validação.
     * 
     * @implSpec Implementação padrão não faz nada
     */
    default void inicializar() {
        // Implementação padrão vazia
        // Subclasses podem sobrescrever se necessário
    }
    
    /**
     * (Opcional) Hook method para limpeza
     * 
     * Pode ser sobrescrito por implementações que precisam
     * liberar recursos após uso.
     * 
     * @implSpec Implementação padrão não faz nada
     */
    default void finalizar() {
        // Implementação padrão vazia
        // Subclasses podem sobrescrever se necessário
    }
}