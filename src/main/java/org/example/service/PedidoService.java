package org.example.service;

import org.example.dto.detallePedido.DetallePedidoCreate;
import org.example.dto.detallePedido.DetallePedidoDto;
import org.example.dto.pedido.PedidoDto;
import org.example.dto.pedido.PedidoEdit;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.model.DetallePedido;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;
import org.example.repository.PedidoRepository;
import org.example.repository.ProductoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Pedido crear(
            Long usuarioId,
            Estado estado,
            FormaPago formaPago,
            List<DetallePedidoCreate> detalles
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = Pedido.builder()
                .fecha(LocalDate.now())
                .estado(estado)
                .formaPago(formaPago)
                .total(0.0)
                .build();

        for (DetallePedidoCreate detalleDto : detalles) {
            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            pedido.addDetallePedido(detalleDto.getCantidad(), producto);
        }

        usuario.agregarPedido(pedido);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido editar(Long id, PedidoEdit dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(dto.getEstado());
        pedido.setFormaPago(dto.getFormaPago());

        return pedidoRepository.save(pedido);
    }

    public PedidoDto buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return convertirADto(pedido);
    }

    public List<PedidoDto> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public PedidoDto convertirADto(Pedido pedido) {
        Long usuarioId = null;
        String usuarioEmail = null;

        if (pedido.getUsuario() != null) {
            usuarioId = pedido.getUsuario().getId();
            usuarioEmail = pedido.getUsuario().getEmail();
        }

        List<DetallePedidoDto> detalles = pedido.getDetalles()
                .stream()
                .map(this::convertirDetalleADto)
                .toList();

        return new PedidoDto(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getFormaPago(),
                usuarioId,
                usuarioEmail,
                detalles
        );
    }

    private DetallePedidoDto convertirDetalleADto(DetallePedido detalle) {
        Long productoId = null;
        String productoNombre = null;

        if (detalle.getProducto() != null) {
            productoId = detalle.getProducto().getId();
            productoNombre = detalle.getProducto().getNombre();
        }

        return new DetallePedidoDto(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getSubTotal(),
                productoId,
                productoNombre
        );
    }
}