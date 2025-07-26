package com.br.plataforma_processamento_pedidos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic pedidoCriadoTopic() {
        return TopicBuilder.name("pedido-criado").partitions(1).replicas(1).build();
    }


}
