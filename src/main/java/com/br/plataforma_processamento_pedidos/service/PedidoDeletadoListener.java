package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.dtos.PedidoDeletadoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoDeletadoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoDeletadoListener.class);

    @KafkaListener(topics = "pedido-deletado", groupId = "plataforma-processamento")
    public void consumirPedidoDeletado(String mensagemJson) {
        log.info("🔔 Pedido deletado recebido: {}", mensagemJson);

        // Se quiser converter em objeto:
        try {
            ObjectMapper mapper = new ObjectMapper();
            PedidoDeletadoEvent evento = mapper.readValue(mensagemJson, PedidoDeletadoEvent.class);
            log.info("👉 Código do pedido deletado: {}", evento.getCodigoPedido());
        } catch (Exception e) {
            log.error("❌ Erro ao deserializar evento de deleção: {}", e.getMessage());
        }
    }

}
