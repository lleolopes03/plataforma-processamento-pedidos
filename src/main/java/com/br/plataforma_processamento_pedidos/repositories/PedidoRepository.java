package com.br.plataforma_processamento_pedidos.repositories;

import com.br.plataforma_processamento_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
