package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.model.EventoPedido;
import com.br.plataforma_processamento_pedidos.repositories.EventoPedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoricoEventoServiceTest {
    @Mock
    private EventoPedidoRepository eventoRepository;

    @InjectMocks
    private HistoricoEventoService historicoEventoService;

    @Test
    void deveRegistrarEventoCorretamente() {
        String codigoPedido = "1234";
        String tipoEvento = "ATUALIZACAO";
        String payloadJson = "{\"status\":\"APROVADO\"}";

        historicoEventoService.registrarEvento(codigoPedido, tipoEvento, payloadJson);

        ArgumentCaptor<EventoPedido> captor = ArgumentCaptor.forClass(EventoPedido.class);
        verify(eventoRepository).save(captor.capture());

        EventoPedido eventoSalvo = captor.getValue();
        assertEquals(codigoPedido, eventoSalvo.getCodigoPedido());
        assertEquals(tipoEvento, eventoSalvo.getTipoEvento());
        assertEquals(payloadJson, eventoSalvo.getPayloadJson());
        assertNotNull(eventoSalvo.getDataEvento()); // verifica que foi gerado o timestamp
    }

    @Test
    void deveBuscarEventosPorCodigoPedido() {
        String codigoPedido = "1234";
        List<EventoPedido> mockEventos = List.of(new EventoPedido(), new EventoPedido());

        when(eventoRepository.findByCodigoPedidoOrderByDataEventoAsc(codigoPedido)).thenReturn(mockEventos);

        List<EventoPedido> resultado = historicoEventoService.buscarEventosPorCodigo(codigoPedido);
        assertEquals(2, resultado.size());
        verify(eventoRepository).findByCodigoPedidoOrderByDataEventoAsc(codigoPedido);
    }

    @Test
    void deveBuscarTodosEventosOrdenados() {
        List<EventoPedido> mockEventos = List.of(new EventoPedido());

        when(eventoRepository.findAll(any(Sort.class))).thenReturn(mockEventos);

        List<EventoPedido> resultado = historicoEventoService.buscarTodosEventos();
        assertEquals(1, resultado.size());
        verify(eventoRepository).findAll(Sort.by(Sort.Direction.DESC, "dataEvento"));
    }


}
