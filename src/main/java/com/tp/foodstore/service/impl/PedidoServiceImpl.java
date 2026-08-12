package com.tp.foodstore.service.impl;

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

        List<Producto> productos = dto.getDetalles().stream()
                .map(detalle -> productoRepository.findById(detalle.getProductoId())
                        .filter(p -> !p.isEliminado())
                        .orElseThrow(() -> new NegocioException("Producto no encontrado con id " + detalle.getProductoId())))
                .toList();

        Pedido pedido = pedidoMapper.toEntity(dto, usuario, productos);
        pedido.calcularTotal();
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido, usuario.getId());
    }

    @Override
    public PedidoDto obtenerPorId(Long id) {
        Pedido pedido = buscar(id);
        return pedidoMapper.toDto(pedido, buscarUsuarioId(pedido));
    }

    @Override
    public List<PedidoDto> listar() {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> !pedido.isEliminado())
                .map(pedido -> pedidoMapper.toDto(pedido, buscarUsuarioId(pedido)))
                .toList();
    }

    private Pedido buscar(Long id) {
        return pedidoRepository.findById(id)
                .filter(pedido -> !pedido.isEliminado())
                .orElseThrow(() -> new NegocioException("Pedido no encontrado con id " + id));
    }

    private Long buscarUsuarioId(Pedido pedido) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.isEliminado())
                .filter(usuario -> usuario.getPedidos().contains(pedido))
                .map(Usuario::getId)
                .findFirst()
                .orElse(null);
    }
}
