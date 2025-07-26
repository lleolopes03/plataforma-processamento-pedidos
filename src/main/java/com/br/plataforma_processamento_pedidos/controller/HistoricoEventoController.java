package com.br.plataforma_processamento_pedidos.controller;

import com.br.plataforma_processamento_pedidos.model.EventoPedido;
import com.br.plataforma_processamento_pedidos.service.HistoricoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historico")

public class HistoricoEventoController {
    @Autowired
    private HistoricoEventoService historicoService;
    @GetMapping("/{codigoPedido}")
    public ResponseEntity<List<EventoPedido>> listarPorPedido(@PathVariable String codigoPedido) {
        List<EventoPedido> eventos = historicoService.buscarEventosPorCodigo(codigoPedido);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping
    public ResponseEntity<List<EventoPedido>> listarTodos() {
        return ResponseEntity.ok(historicoService.buscarTodosEventos());
    }


}
