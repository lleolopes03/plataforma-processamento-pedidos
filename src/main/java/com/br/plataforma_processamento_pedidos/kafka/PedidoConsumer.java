package com.br.plataforma_processamento_pedidos.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {
    @KafkaListener(topics = "pedido-criado", groupId = "pedido-consumer-group")
    public void consumir(String mensagem) {
        System.out.println("📩 Pedido recebido via Kafka: " + mensagem);
        // Aqui pode transformar em objeto, processar, chamar outro service...
    }

}
