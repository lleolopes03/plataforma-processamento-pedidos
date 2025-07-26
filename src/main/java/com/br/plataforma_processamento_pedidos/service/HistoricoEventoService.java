package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.model.EventoPedido;
import com.br.plataforma_processamento_pedidos.repositories.EventoPedidoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoEventoService {
    private final EventoPedidoRepository eventoRepository;

    public HistoricoEventoService(EventoPedidoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public void registrarEvento(String codigoPedido, String tipoEvento, String payloadJson) {
        EventoPedido evento = new EventoPedido();
        evento.setCodigoPedido(codigoPedido);
        evento.setTipoEvento(tipoEvento);
        evento.setPayloadJson(payloadJson);
        evento.setDataEvento(LocalDateTime.now());

        eventoRepository.save(evento);
    }
    public List<EventoPedido> buscarEventosPorCodigo(String codigoPedido) {
        return eventoRepository.findByCodigoPedidoOrderByDataEventoAsc(codigoPedido);
    }

    public List<EventoPedido> buscarTodosEventos() {
        return eventoRepository.findAll(Sort.by(Sort.Direction.DESC, "dataEvento"));
    }

}
