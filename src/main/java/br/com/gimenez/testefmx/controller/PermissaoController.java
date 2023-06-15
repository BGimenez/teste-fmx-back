package br.com.gimenez.testefmx.controller;

import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.service.PermissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
    private final PermissaoService permissaoService;

    @GetMapping
    public ResponseEntity<List<Permissao>> findAll() {
        return ResponseEntity.ok(permissaoService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Permissao> findById(@PathVariable long id) {
        return ResponseEntity.ok(permissaoService.findByIdWithException(id));
    }

    @PostMapping
    public ResponseEntity<Permissao> save(@RequestBody @Valid Permissao permissao) {
        return new ResponseEntity<>(permissaoService.save(permissao), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        permissaoService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Permissao permissao) {
        permissaoService.save(permissao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
