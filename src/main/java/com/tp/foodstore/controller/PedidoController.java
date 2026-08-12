package com.tp.foodstore.controller;

import com.tp.foodstore.dto.pedido.PedidoDto;
import com.tp.foodstore.dto.pedido.PedidoEdit;
import com.tp.foodstore.service.interfaces.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints de pedidos.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto crear(@Valid @RequestBody PedidoEdit dto) {
        return pedidoService.crear(dto);
    }

    @GetMapping
    public List<PedidoDto> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public PedidoDto obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }
}
