package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.UsuarioDTO;
import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/usuarios")
public class UsuarioController {

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/listaTodos")
    @ResponseBody
    public ResponseEntity<List<UsuarioModel>> listar() {
        List<UsuarioModel> usuariosLocal = this.usuarioService.listaTodos();

        if (usuariosLocal == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuariosLocal);
    }

    @PostMapping(value = "/salvar")
    @ResponseBody
    public ResponseEntity<Object> salvar(@RequestBody @Valid @NotNull UsuarioDTO usuarioDTO) {

        if (this.usuarioService.existsByNome(usuarioDTO.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito nome já existente: " + usuarioDTO.getNome());
        }

        if (!this.usuarioService.validarInputJson(usuarioDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gravar: " + usuarioDTO);
        }

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO, usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuarioModel));
    }

    @DeleteMapping(value = "/apagar")
    @ResponseBody
    public ResponseEntity<Object> apagar(@RequestParam(name = "id", required = true) Long id) {
        HttpStatus httpStatusLocal = this.usuarioService.apagar(id);

        if (httpStatusLocal == HttpStatus.NO_CONTENT) { // anula o body
            return ResponseEntity.status(httpStatusLocal).body("Não localizado para apagar! " + id);
        }
        return ResponseEntity.status(httpStatusLocal).body("Apagado com Sucesso");
    }

    @GetMapping(value = "/buscarId")
    @ResponseBody
    public ResponseEntity<Object> buscarId(@RequestBody @Valid UsuarioModel usuarioModel) {
        Optional<UsuarioModel> usuarioLocal = this.usuarioService.buscarId(usuarioModel.getIdUsuario());

        if (usuarioLocal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizado! " + usuarioModel.getIdUsuario());
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioLocal.get());
    }

    @PutMapping(value = "/atualizar")
    @ResponseBody
    public ResponseEntity<Object> atualizar(@RequestBody UsuarioModel usuarioModel) {

        UsuarioModel usuarioModelLocal = this.usuarioService.atualizar(usuarioModel);
        if (usuarioModelLocal == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Erro ao atualizar! " + usuarioModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioModelLocal);
    }

    @GetMapping(value = "/buscarUserName")
    @ResponseBody
    public ResponseEntity<Object> buscarUserName(@RequestBody @NotNull UsuarioModel usuarioModel) {

        List<UsuarioModel> listaUsuarioLocalModel = usuarioService.buscarNomeUsuario(usuarioModel.getNome().trim().toUpperCase());
        if (listaUsuarioLocalModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não Localizado! " + usuarioModel.getNome());
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaUsuarioLocalModel);
    }

}
