package com.tp.foodstore.mapper;

import com.tp.foodstore.dto.detallePedido.DetallePedidoCreate;
import com.tp.foodstore.dto.detallePedido.DetallePedidoDto;
import com.tp.foodstore.dto.pedido.PedidoDto;
import com.tp.foodstore.dto.pedido.PedidoEdit;
import com.tp.foodstore.entity.DetallePedido;
import com.tp.foodstore.entity.Pedido;
import com.tp.foodstore.entity.Producto;
import com.tp.foodstore.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convierte DTOs de pedidos en la entidad Pedido y viceversa.
 */
@Component
public class PedidoMapper {

    /**
     * Construye un Pedido con sus detalles. Cada detalle del DTO se asocia
     * con el producto de la lista que ocupa su misma posición.
     */
    public Pedido toEntity(PedidoEdit dto, Usuario usuario, List<Producto> productos) {
        Pedido pedido = new Pedido();
        pedido.setFecha(dto.getFecha());
        pedido.setEstado(dto.getEstado());
        pedido.setFormaPago(dto.getFormaPago());
        pedido.setUsuario(usuario);

        List<DetallePedidoCreate> detallesDto = dto.getDetalles();
        for (int i = 0; i < detallesDto.size(); i++) {
            DetallePedidoCreate detalleDto = detallesDto.get(i);
            pedido.addDetallePedido(detalleDto.getCantidad(), productos.get(i));
        }

        usuario.getPedidos().add(pedido);
        return pedido;
    }

    public PedidoDto toDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado());
        dto.setFormaPago(pedido.getFormaPago());
        dto.setTotal(pedido.getTotal());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setDetalles(pedido.getDetalles().stream()
                .map(this::toDetalleDto)
                .toList());
        return dto;
    }

    private DetallePedidoDto toDetalleDto(DetallePedido detalle) {
        DetallePedidoDto dto = new DetallePedidoDto();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setProductoId(detalle.getProducto().getId());
        dto.setNombreProducto(detalle.getProducto().getNombre());
        return dto;
    }
}
