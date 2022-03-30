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
import java.io.IOException;
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
        List<UsuarioModel> usuariosLocal = this.usuarioService.listarUsuarios();

        if (usuariosLocal == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuariosLocal);
    }

    @PostMapping(value = "/salvar")
    @ResponseBody
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid @NotNull UsuarioDTO usuarioDTO) {

        if (usuarioService.existsByNome(usuarioDTO.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito nome já existente: " + usuarioDTO.getNome());
        }

        if (!this.usuarioService.validarInputJson(usuarioDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gravar: " + usuarioDTO);
        }
            var usuarioModel = new UsuarioModel();
            BeanUtils.copyProperties(usuarioDTO, usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioModel));
    }

    @DeleteMapping(value = "/apagar")
    @ResponseBody
    public ResponseEntity<String> apagar(@RequestParam(name = "id", required = true) Long id) {
        HttpStatus httpStatusLocal = this.usuarioService.deletarUsuario(id);

        if (httpStatusLocal == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<String>("Não localizado para apagar! " + id, httpStatusLocal);
        }
        return new ResponseEntity<String>("User Deleted Successful", httpStatusLocal);
    }

    @GetMapping(value = "/buscarUsuarioId")
    @ResponseBody
    public ResponseEntity<Object> buscarUsuarioId(@RequestBody @Valid UsuarioModel usuarioModel) {
        Optional<UsuarioModel> usuarioLocal = this.usuarioService.buscarIdUsuario(usuarioModel.getIdUsuario());

        if (!usuarioLocal.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizado! " + usuarioModel.getIdUsuario());
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioLocal.get());
    }

    @PutMapping(value = "/atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody UsuarioModel usuarioModel) {

        UsuarioModel usuarioModelLocal = this.usuarioService.atualizarUsuario(usuarioModel);
        if (usuarioModelLocal == null) {
            return new ResponseEntity<String>("Erro ao atualizar! " + usuarioModel.getIdUsuario(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<UsuarioModel>(usuarioModelLocal, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarUserName")
    @ResponseBody
    public ResponseEntity<?> buscarUserName(@RequestBody @NotNull UsuarioModel usuarioModel) {

        List<UsuarioModel> listaUsuarioLocalModel = usuarioService.buscarNomeUsuario(usuarioModel.getNome().trim().toUpperCase());
        if (listaUsuarioLocalModel.isEmpty()) {
            return new ResponseEntity<String>("Não Localizado! " + usuarioModel.getNome(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UsuarioModel>>(listaUsuarioLocalModel, HttpStatus.OK);
    }

}
