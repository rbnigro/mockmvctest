package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UsuarioService usuarioService;

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
    public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioModel usuarioModel) throws IOException {
        //TODO AQUI

        if (this.usuarioService.validarInputJson(usuarioModel)) {
            UsuarioModel usuarioModelSalvar = usuarioModelSalvar = this.usuarioService.salvarUsuario(usuarioModel);
            return new ResponseEntity<UsuarioModel>(usuarioModelSalvar, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Erro ao gravar: " + usuarioModel, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/apagar")
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam(name = "id", required = true) Long id) {
        HttpStatus httpStatusLocal = this.usuarioService.deletarUsuario(id);

        if (httpStatusLocal == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<String>("Não localizado para apagar! " + id, httpStatusLocal);
        }
        return new ResponseEntity<String>("User Deleted Successful", httpStatusLocal);
    }

    @GetMapping(value = "/buscarUserId")
    @ResponseBody
    public ResponseEntity<?> buscarId(@RequestBody @NotNull UsuarioModel usuarioModel) {
        Optional<UsuarioModel> usuarioLocal = this.usuarioService.buscarIdUsuario(usuarioModel.getIdUsuario());

        if (!usuarioLocal.isPresent()) {
            return new ResponseEntity<String>("Não localizado! " + usuarioModel.getIdUsuario(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<UsuarioModel>>(usuarioLocal, HttpStatus.OK);
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
