package br.inatel.dexmarket.controller;

import br.inatel.dexmarket.model.Troca;
import br.inatel.dexmarket.service.TrocaService;

public class TrocaController {
    private TrocaService trocaService = new TrocaService();

    public void iniciarTroca(Troca troca) {
        trocaService.realizarTroca(troca);
    }
}