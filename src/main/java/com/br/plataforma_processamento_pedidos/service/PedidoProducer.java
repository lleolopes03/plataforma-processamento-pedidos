package com.br.plataforma_processamento_pedidos.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PedidoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarPedidoCriado(String mensagem) {
        kafkaTemplate.send("pedido-criado", mensagem);
    }

}
