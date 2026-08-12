package com.tp.foodstore.controller;

import com.tp.foodstore.dto.usuario.UsuarioCreate;
import com.tp.foodstore.dto.usuario.UsuarioDto;
import com.tp.foodstore.service.interfaces.UsuarioService;
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
 * Endpoints de usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto crear(@Valid @RequestBody UsuarioCreate dto) {
        return usuarioService.crear(dto);
    }

    @GetMapping("/{id}")
    public UsuarioDto obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @GetMapping("/mail/{mail}")
    public UsuarioDto buscarPorMail(@PathVariable String mail) {
        return usuarioService.buscarPorMail(mail);
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        return usuarioService.listar();
    }
}
