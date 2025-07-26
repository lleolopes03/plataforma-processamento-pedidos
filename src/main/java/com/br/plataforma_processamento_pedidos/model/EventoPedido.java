package com.br.plataforma_processamento_pedidos.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_eventos")

public class EventoPedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoPedido;
    private String tipoEvento; // ATUALIZACAO, DELECAO, CRIACAO etc.
    private String payloadJson;
    private LocalDateTime dataEvento;

    public EventoPedido() {
    }

    public EventoPedido(Long id, String codigoPedido, String tipoEvento, String payloadJson, LocalDateTime dataEvento) {
        this.id = id;
        this.codigoPedido = codigoPedido;
        this.tipoEvento = tipoEvento;
        this.payloadJson = payloadJson;
        this.dataEvento = dataEvento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }
}
