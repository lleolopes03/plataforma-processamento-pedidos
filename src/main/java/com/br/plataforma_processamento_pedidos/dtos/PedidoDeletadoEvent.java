package com.br.plataforma_processamento_pedidos.dtos;

public class PedidoDeletadoEvent {
    private String codigoPedido;

    public PedidoDeletadoEvent(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
}
