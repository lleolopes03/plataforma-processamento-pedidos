package com.br.plataforma_processamento_pedidos.service;

import com.br.plataforma_processamento_pedidos.dtos.CreatePedidoDTO;
import com.br.plataforma_processamento_pedidos.dtos.PedidoDeletadoEvent;
import com.br.plataforma_processamento_pedidos.dtos.ResponsePedidoDTO;
import com.br.plataforma_processamento_pedidos.exception.BusinessException;
import com.br.plataforma_processamento_pedidos.model.Pedido;
import com.br.plataforma_processamento_pedidos.model.StatusPedido;
import com.br.plataforma_processamento_pedidos.repositories.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.br.plataforma_processamento_pedidos.model.StatusPedido.PROCESSANDO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @InjectMocks
    private PedidoService pedidoService;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PedidoProducer pedidoProducer;

    private CreatePedidoDTO criarPedidoValido() {
        CreatePedidoDTO dto = new CreatePedidoDTO();
        dto.setCodigoPedido("1");
        dto.setProduto("caderno");
        dto.setQuantidade(1);
        dto.setValorTotal(new BigDecimal("5.00"));
        dto.setStatus(StatusPedido.PROCESSANDO);
        return dto;
    }
    private Pedido criarPedido(){
        LocalDateTime agora = LocalDateTime.of(2025, 7, 26, 13, 44);
        Pedido pedido=new Pedido();
        pedido.setId(1L);
        pedido.setCodigoPedido("1");
        pedido.setProduto("caderno");
        pedido.setQuantidade(1);
        pedido.setValorTotal(new BigDecimal(5.00));
        pedido.setStatus(PROCESSANDO);
        pedido.setDataCriacao(agora);
        pedido.setDataAtualizacao(agora);
        return  pedido;

    }
    private ResponsePedidoDTO respostaPedidoDTO(){
        LocalDateTime agora = LocalDateTime.of(2025, 7, 26, 13, 44);
        ResponsePedidoDTO responsePedidoDTO=new ResponsePedidoDTO();
        responsePedidoDTO.setId(1L);
        responsePedidoDTO.setCodigoPedido("1");
        responsePedidoDTO.setProduto("caderno");
        responsePedidoDTO.setQuantidade(1);
        responsePedidoDTO.setValorTotal(new BigDecimal(5.00));
        responsePedidoDTO.setStatus(PROCESSANDO);
        responsePedidoDTO.setDataCriacao(agora);
        responsePedidoDTO.setDataAtualizacao(agora);
        return responsePedidoDTO;
    }

    @Test
    @DisplayName("Teste criar pedido")
    public void teste_criar_pedido() throws JsonProcessingException {
        CreatePedidoDTO dto = criarPedidoValido();
        Pedido pedidos=criarPedido();
        ResponsePedidoDTO responsePedidoDTO=respostaPedidoDTO();
        Assertions.assertNotNull(dto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidos);
        when(objectMapper.writeValueAsString(any())).thenReturn("pedido-em-json");
        doNothing().when(pedidoProducer).enviarPedidoCriado(anyString());
        ResponsePedidoDTO resultado=pedidoService.salvar(dto);
        Assertions.assertEquals(responsePedidoDTO.getId(),resultado.getId());
        Assertions.assertEquals(responsePedidoDTO.getCodigoPedido(),resultado.getCodigoPedido());
        Assertions.assertEquals(responsePedidoDTO.getProduto(),resultado.getProduto());
        Assertions.assertEquals(responsePedidoDTO.getQuantidade(),resultado.getQuantidade());
        Assertions.assertEquals(responsePedidoDTO.getValorTotal(),resultado.getValorTotal());
        Assertions.assertEquals(responsePedidoDTO.getStatus(),resultado.getStatus());
        Assertions.assertEquals(responsePedidoDTO.getDataCriacao(),resultado.getDataCriacao());
        Assertions.assertEquals(responsePedidoDTO.getDataAtualizacao(),resultado.getDataAtualizacao());

        verify(pedidoRepository).save(any(Pedido.class));
        verify(pedidoProducer).enviarPedidoCriado("pedido-em-json");


    }
    @Test
    @DisplayName("Teste pedido inválido - quantidade igual a zero")
    void teste_pedido_invalido_quantidade_zero() {
        CreatePedidoDTO dto = criarPedidoValido();
        dto.setQuantidade(0); // quantidade inválida

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.salvar(dto);
        });
    }
    @Test
    @DisplayName("Teste pedido duplicado - código já existe")
    void teste_pedido_duplicado() {
        CreatePedidoDTO dto = criarPedidoValido();

        // Simulando que já existe um pedido com esse código
        when(pedidoRepository.existsByCodigoPedido(dto.getCodigoPedido())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            pedidoService.salvar(dto);
        });

        verify(pedidoRepository).existsByCodigoPedido("1");
        verify(pedidoRepository, never()).save(any());
    }
    @Test
    @DisplayName("teste_buscar_por_id")
    public void teste_buscar_por_id(){
        Pedido pedidos=criarPedido();
        ResponsePedidoDTO responsePedidoDTO=respostaPedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidos));
        ResponsePedidoDTO resultado=pedidoService.buscaPorId(1L);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(responsePedidoDTO.getId(),resultado.getId());
        Assertions.assertEquals(responsePedidoDTO.getCodigoPedido(),resultado.getCodigoPedido());
        Assertions.assertEquals(responsePedidoDTO.getProduto(),resultado.getProduto());
        Assertions.assertEquals(responsePedidoDTO.getQuantidade(),resultado.getQuantidade());
        Assertions.assertEquals(responsePedidoDTO.getValorTotal(),resultado.getValorTotal());
        Assertions.assertEquals(responsePedidoDTO.getStatus(),resultado.getStatus());
        Assertions.assertEquals(responsePedidoDTO.getDataCriacao(),resultado.getDataCriacao());
        Assertions.assertEquals(responsePedidoDTO.getDataAtualizacao(),resultado.getDataAtualizacao());



    }
    @Test
    @DisplayName("teste_buscar_por_id_nao_encontrado")
    public void teste_buscar_por_id_nao_encontrado() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            pedidoService.buscaPorId(99L);
        });
    }
    @Test
    @DisplayName("teste_lista_pedidos")
    public void teste_lista_pedidos(){
        LocalDateTime agora = LocalDateTime.of(2025, 7, 26, 13, 44);
        List<Pedido>pedidos= Arrays.asList(
                new Pedido(1L,"1","caderno",1,new BigDecimal(5.00),PROCESSANDO,agora,agora),
                new Pedido(2L,"2","folha",2,new BigDecimal(3.00),PROCESSANDO,agora,agora)
        );
        List<ResponsePedidoDTO>responsePedidoDTOS=Arrays.asList(
                new ResponsePedidoDTO(1L,"1","caderno",1,new BigDecimal(5.00),PROCESSANDO,agora,agora),
                new ResponsePedidoDTO(2L,"2","folha",2,new BigDecimal(3.00),PROCESSANDO,agora,agora)
        );
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        List<ResponsePedidoDTO>resultados=pedidoService.buscarTodos();
        Assertions.assertNotNull(resultados);
        Assertions.assertEquals(responsePedidoDTOS.size(),resultados.size());
        for (int i=0;i<responsePedidoDTOS.size();i++){
            Assertions.assertEquals(responsePedidoDTOS.get(i).getId(),resultados.get(i).getId());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getCodigoPedido(),resultados.get(i).getCodigoPedido());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getProduto(),resultados.get(i).getProduto());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getQuantidade(),resultados.get(i).getQuantidade());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getValorTotal(),resultados.get(i).getValorTotal());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getStatus(),resultados.get(i).getStatus());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getDataCriacao(),resultados.get(i).getDataCriacao());
            Assertions.assertEquals(responsePedidoDTOS.get(i).getDataAtualizacao(),resultados.get(i).getDataAtualizacao());

        }
        Assertions.assertEquals("1", resultados.get(0).getCodigoPedido());
        Assertions.assertEquals("2", resultados.get(1).getCodigoPedido());
    }
    @Test
    @DisplayName("teste_lista_vazia")
    public void teste_lista_vazia() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        List<ResponsePedidoDTO> resultados = pedidoService.buscarTodos();

        Assertions.assertNotNull(resultados);
        Assertions.assertTrue(resultados.isEmpty());
    }
    @Test
    @DisplayName("Teste_deletar_pedido")
    void teste_deletar_pedido() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setCodigoPedido("123"); // ou o valor que seu evento espera

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).deleteById(id);
        doNothing().when(pedidoProducer).enviarPedidoDeletado(any(PedidoDeletadoEvent.class));

        pedidoService.deletar(id);

        verify(pedidoRepository, times(1)).findById(id);
        verify(pedidoRepository, times(1)).deleteById(id);
        verify(pedidoProducer, times(1)).enviarPedidoDeletado(any(PedidoDeletadoEvent.class));
    }
    @Test
    @DisplayName("Teste editar  pedido nao encontrado")
    void test_editar_pedido_nao_encontrado() {
        Long id = 1L;
        CreatePedidoDTO createDto=new CreatePedidoDTO();

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> pedidoService.editarPedido(id, createDto));
    }
    @Test
    @DisplayName("Teste editar pedido")
    public void editar_pedido(){
        LocalDateTime agora = LocalDateTime.of(2025, 7, 26, 13, 44);
        Pedido pedidoExistente=new Pedido(1L,"1","caderno",1,new BigDecimal(5.00),PROCESSANDO,agora,agora);
        CreatePedidoDTO createPedidoDTO=new CreatePedidoDTO("1","lapis",2,new BigDecimal(6.00),PROCESSANDO);
        Pedido pedidoAtualizado=new Pedido(1L,"1","lapis",2,new BigDecimal(6.00),PROCESSANDO,agora,agora);
        ResponsePedidoDTO responseEsperado=new ResponsePedidoDTO(1L,"1","lapis",2,new BigDecimal(6.00),PROCESSANDO,agora,agora);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoAtualizado);

        ResponsePedidoDTO resultado=pedidoService.editarPedido(1L,createPedidoDTO);
        Assertions.assertNotNull(resultado);

        Assertions.assertEquals(responseEsperado.getId(),resultado.getId());
        Assertions.assertEquals(responseEsperado.getCodigoPedido(),resultado.getCodigoPedido());
        Assertions.assertEquals(responseEsperado.getProduto(),resultado.getProduto());
        Assertions.assertEquals(responseEsperado.getQuantidade(),resultado.getQuantidade());
        Assertions.assertEquals(responseEsperado.getValorTotal(),resultado.getValorTotal());
        Assertions.assertEquals(responseEsperado.getStatus(),resultado.getStatus());
        Assertions.assertEquals(responseEsperado.getDataCriacao(),resultado.getDataCriacao());
        Assertions.assertEquals(responseEsperado.getDataAtualizacao(),resultado.getDataAtualizacao());
    }
}
