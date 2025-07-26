package com.br.plataforma_processamento_pedidos.dtos;

import com.br.plataforma_processamento_pedidos.model.StatusPedido;

import java.time.LocalDateTime;

public class PedidoAtualizadoEvent {
    private String codigoPedido;
    private StatusPedido status;
    private LocalDateTime dataAtualizacao;

    public PedidoAtualizadoEvent(String codigoPedido, StatusPedido status, LocalDateTime dataAtualizacao) {
        this.codigoPedido = codigoPedido;
        this.status = status;
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
