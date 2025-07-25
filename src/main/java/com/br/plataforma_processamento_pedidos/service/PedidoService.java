package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.dtos.CreatePedidoDTO;
import com.br.plataforma_processamento_pedidos.dtos.ResponsePedidoDTO;
import com.br.plataforma_processamento_pedidos.dtos.mapper.PedidoMapper;
import com.br.plataforma_processamento_pedidos.exception.BusinessException;
import com.br.plataforma_processamento_pedidos.model.Pedido;
import com.br.plataforma_processamento_pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public ResponsePedidoDTO salvar(CreatePedidoDTO createDto){
        Pedido pedido= PedidoMapper.toPedido(createDto);
        pedido.setDataCriacao(LocalDateTime.now());
        Pedido pedidoSalvo=pedidoRepository.save(pedido);
        return PedidoMapper.toDto(pedidoSalvo);

    }
    public ResponsePedidoDTO buscaPorId(Long id){
        Pedido pedido=pedidoRepository.findById(id).orElseThrow(()->new BusinessException(String.format("Pedido com id: %s não encontrado",id)));
        return PedidoMapper.toDto(pedido);
    }
    public List<ResponsePedidoDTO>buscarTodos(){
        List<Pedido>pedidos=pedidoRepository.findAll();
        return PedidoMapper.toListDto(pedidos);
    }
    public void deletar(Long id){
        if (!pedidoRepository.existsById(id)){
            throw new BusinessException(String.format("Pedido com id: %s não encontrado",id));
        }
        pedidoRepository.deleteById(id);

    }
    public ResponsePedidoDTO editarPedido(Long id, CreatePedidoDTO creteDto){
        Pedido pedido=pedidoRepository.findById(id).orElseThrow(()->new BusinessException(String.format("Pedido com id: %s não encontrado",id)));;
        pedido.setCodigoPedido(creteDto.getCodigoPedido());
        pedido.setProduto(creteDto.getProduto());
        pedido.setQuantidade(creteDto.getQuantidade());
        pedido.setValorTotal(creteDto.getValorTotal());
        pedido.setStatus(creteDto.getStatus());
        pedido.setDataAtualizacao(LocalDateTime.now());
        Pedido Atualizado=pedidoRepository.save(pedido);
        return PedidoMapper.toDto(Atualizado);

    }

}
