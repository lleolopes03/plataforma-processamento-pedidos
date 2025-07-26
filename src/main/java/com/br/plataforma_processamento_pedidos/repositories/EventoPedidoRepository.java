package com.br.plataforma_processamento_pedidos.repositories;

import com.br.plataforma_processamento_pedidos.model.EventoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoPedidoRepository extends JpaRepository<EventoPedido,Long> {
    List<EventoPedido> findByCodigoPedidoOrderByDataEventoAsc(String codigoPedido);

}
