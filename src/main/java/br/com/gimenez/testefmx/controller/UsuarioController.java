package br.com.gimenez.testefmx.controller;

import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.findByIdWithException(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid Usuario usuario) {
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        usuarioService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
