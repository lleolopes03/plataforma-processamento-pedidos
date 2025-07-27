package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.dtos.PedidoAtualizadoEvent;
import com.br.plataforma_processamento_pedidos.dtos.PedidoDeletadoEvent;
import com.br.plataforma_processamento_pedidos.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoProducerTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PedidoProducer pedidoProducer;

    @Test
    @DisplayName("teste enviar pedido criado")
    void deveEnviarPedidoCriado() {
        String mensagem = "pedido teste";
        pedidoProducer.enviarPedidoCriado(mensagem);
        verify(kafkaTemplate).send("pedido-criado", mensagem);
    }

    @Test
    @DisplayName("teste enviar teste atualizado")
    void deveEnviarPedidoAtualizado() throws JsonProcessingException {
        PedidoAtualizadoEvent evento = new PedidoAtualizadoEvent(/* preencher campos */);
        String mensagem = "mensagem serializada";
        when(objectMapper.writeValueAsString(evento)).thenReturn(mensagem);

        pedidoProducer.enviarPedidoAtualizado(evento);
        verify(kafkaTemplate).send("pedido-atualizado", evento.getCodigoPedido(), mensagem);
    }

    @Test
    @DisplayName("teste de falha na serializacao")
    void deveLancarExceptionAoFalharSerializacaoAtualizacao() throws JsonProcessingException {
        PedidoAtualizadoEvent evento = new PedidoAtualizadoEvent(/* preencher campos */);
        when(objectMapper.writeValueAsString(evento)).thenThrow(new JsonProcessingException("erro") {});

        assertThrows(BusinessException.class, () -> pedidoProducer.enviarPedidoAtualizado(evento));
    }
    @Test
    @DisplayName("teste de pedido deletado")
    void deveEnviarPedidoDeletado() throws JsonProcessingException {
        PedidoDeletadoEvent evento = new PedidoDeletadoEvent(/* preencher campos */);
        String mensagem = "mensagem deletada";

        when(objectMapper.writeValueAsString(evento)).thenReturn(mensagem);
        pedidoProducer.enviarPedidoDeletado(evento);

        verify(kafkaTemplate).send("pedido-deletado", evento.getCodigoPedido(), mensagem);
    }

    @Test
    @DisplayName("teste de falha de serializacao")
    void deveLancarExceptionAoFalharSerializacaoDelecao() throws JsonProcessingException {
        PedidoDeletadoEvent evento = new PedidoDeletadoEvent(/* preencher campos */);

        when(objectMapper.writeValueAsString(evento))
                .thenThrow(new JsonProcessingException("Erro na deleção") {});

        assertThrows(BusinessException.class, () -> pedidoProducer.enviarPedidoDeletado(evento));
    }





}
