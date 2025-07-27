package com.br.plataforma_processamento_pedidos.kafka;

import com.br.plataforma_processamento_pedidos.dtos.PedidoAtualizadoEvent;
import com.br.plataforma_processamento_pedidos.service.HistoricoEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoAtualizadoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoAtualizadoListener.class);
    private final ObjectMapper mapper;
    private final HistoricoEventoService historicoService;



    public PedidoAtualizadoListener(ObjectMapper mapper, HistoricoEventoService historicoService) {
        this.mapper = mapper;
        this.historicoService = historicoService;
    }

    @KafkaListener(topics = "pedido-atualizado", groupId = "plataforma-processamento")
    public void consumirPedidoAtualizado(String mensagemJson) {
        log.info("🔔 Pedido atualizado recebido: {}", mensagemJson);

        try {
            PedidoAtualizadoEvent evento = mapper.readValue(mensagemJson, PedidoAtualizadoEvent.class);
            log.info("📦 Pedido: {}, Novo status: {}", evento.getCodigoPedido(), evento.getStatus());

            historicoService.registrarEvento(evento.getCodigoPedido(), "ATUALIZACAO", mensagemJson);

        } catch (Exception e) {
            log.error("❌ Erro ao deserializar evento de atualização: {}", e.getMessage());
        }
    }



}
