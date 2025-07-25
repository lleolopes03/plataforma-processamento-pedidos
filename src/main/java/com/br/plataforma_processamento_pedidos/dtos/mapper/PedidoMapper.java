package com.br.plataforma_processamento_pedidos.dtos.mapper;
import com.br.plataforma_processamento_pedidos.dtos.CreatePedidoDTO;
import com.br.plataforma_processamento_pedidos.dtos.ResponsePedidoDTO;
import com.br.plataforma_processamento_pedidos.model.Pedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    private static final ModelMapper mapper= new ModelMapper();
    static {
        PropertyMap<Pedido, ResponsePedidoDTO>props=new PropertyMap<Pedido, ResponsePedidoDTO>() {
            @Override
            protected void configure() {

            }
        };
        mapper.addMappings(props);
    }
    public static Pedido toPedido(CreatePedidoDTO createPedidoDTO){
        return mapper.map(createPedidoDTO, Pedido.class);
    }
    public static ResponsePedidoDTO toDto(Pedido pedido){
        return mapper.map(pedido, ResponsePedidoDTO.class);
    }
    public static List<ResponsePedidoDTO>toListDto(List<Pedido>pedidos){
        return pedidos.stream().map(PedidoMapper::toDto).collect(Collectors.toList());
    }
}
