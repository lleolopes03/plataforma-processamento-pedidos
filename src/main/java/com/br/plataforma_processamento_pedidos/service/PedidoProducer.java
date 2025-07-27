package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.dtos.PedidoAtualizadoEvent;
import com.br.plataforma_processamento_pedidos.dtos.PedidoDeletadoEvent;
import com.br.plataforma_processamento_pedidos.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(PedidoProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PedidoProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarPedidoCriado(String mensagem) {
        kafkaTemplate.send("pedido-criado", mensagem);
    }
    public void enviarPedidoAtualizado(PedidoAtualizadoEvent evento) {
        try {
            String mensagem = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send("pedido-atualizado", evento.getCodigoPedido(), mensagem);
            log.info("🚀 Evento de atualização enviado: {}", mensagem);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Erro ao enviar pedido atualizado para Kafka: " + e.getMessage());
        }
    }
    public void enviarPedidoDeletado(PedidoDeletadoEvent evento) {
        try {
            String mensagem = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send("pedido-deletado", evento.getCodigoPedido(), mensagem);
            log.info("🗑️ Evento de deleção enviado: {}", mensagem);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Erro ao enviar evento de deleção: " + e.getMessage());
        }
    }

}
