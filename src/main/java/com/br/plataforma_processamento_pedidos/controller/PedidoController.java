package com.br.plataforma_processamento_pedidos.controller;

import com.br.plataforma_processamento_pedidos.dtos.CreatePedidoDTO;
import com.br.plataforma_processamento_pedidos.dtos.ResponsePedidoDTO;
import com.br.plataforma_processamento_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ResponsePedidoDTO>criarPedido(@RequestBody @Valid CreatePedidoDTO createDto){
        ResponsePedidoDTO responsePedidoDTO=pedidoService.salvar(createDto);
        URI location=URI.create("/api/v1/pedidos/"+responsePedidoDTO.getId());
        return ResponseEntity.created(location).body(responsePedidoDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePedidoDTO>buscarPorId(@PathVariable Long id){
        ResponsePedidoDTO responsePedidoDTO=pedidoService.buscaPorId(id);
        return ResponseEntity.ok(responsePedidoDTO);
    }
    @GetMapping
    public ResponseEntity<List<ResponsePedidoDTO>>buscarTodos(){
        List<ResponsePedidoDTO>responsePedidoDTOS=pedidoService.buscarTodos();
        return ResponseEntity.ok(responsePedidoDTOS);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePedidoDTO>editarPedido(@PathVariable Long id, @RequestBody @Valid CreatePedidoDTO createPedidoDTO){
        ResponsePedidoDTO responsePedidoDTO=pedidoService.editarPedido(id,createPedidoDTO);
        return ResponseEntity.ok(responsePedidoDTO);
    }
}
