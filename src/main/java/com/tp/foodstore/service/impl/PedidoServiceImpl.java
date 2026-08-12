package com.tp.foodstore.service.impl;

import com.tp.foodstore.dto.detallePedido.DetallePedidoCreate;
import com.tp.foodstore.dto.pedido.PedidoDto;
import com.tp.foodstore.dto.pedido.PedidoEdit;
import com.tp.foodstore.entity.Pedido;
import com.tp.foodstore.entity.Producto;
import com.tp.foodstore.entity.Usuario;
import com.tp.foodstore.exception.NegocioException;
import com.tp.foodstore.mapper.PedidoMapper;
import com.tp.foodstore.repository.PedidoRepository;
import com.tp.foodstore.repository.ProductoRepository;
import com.tp.foodstore.repository.UsuarioRepository;
import com.tp.foodstore.service.interfaces.PedidoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio de pedidos.
 */
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             UsuarioRepository usuarioRepository,
                             ProductoRepository productoRepository,
                             PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoDto crear(PedidoEdit dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .filter(u -> !u.isEliminado())
                .orElseThrow(() -> new NegocioException("Usuario no encontrado con id " + dto.getUsuarioId()));

        List<Producto> productos = new ArrayList<>();
        for (DetallePedidoCreate detalle : dto.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId())
                    .filter(p -> !p.isEliminado())
                    .orElseThrow(() -> new NegocioException("Producto no encontrado con id " + detalle.getProductoId()));
            if (!producto.tieneStock(detalle.getCantidad())) {
                throw new NegocioException("Stock insuficiente del producto " + producto.getNombre());
            }
            producto.descontarStock(detalle.getCantidad());
            productoRepository.save(producto);
            productos.add(producto);
        }

        Pedido pedido = pedidoMapper.toEntity(dto, usuario, productos);
        pedido.calcularTotal();
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    @Override
    public PedidoDto obtenerPorId(Long id) {
        return pedidoMapper.toDto(buscar(id));
    }

    @Override
    public List<PedidoDto> listar() {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> !pedido.isEliminado())
                .map(pedidoMapper::toDto)
                .toList();
    }

    private Pedido buscar(Long id) {
        return pedidoRepository.findById(id)
                .filter(pedido -> !pedido.isEliminado())
                .orElseThrow(() -> new NegocioException("Pedido no encontrado con id " + id));
    }
}
