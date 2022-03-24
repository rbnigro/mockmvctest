package br.com.springboot.cursojdevtreinamento.controllers;

import br.com.springboot.cursojdevtreinamento.model.Usuario;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listaTodos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuariosLocal = this.usuarioService.listarUsuarios();

        if(usuariosLocal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuariosLocal);
    }

    @PostMapping(value = "/salvar")
    @ResponseBody
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
      Usuario usuarioLocal = this.usuarioService.salvarUsuario(usuario);
      return new ResponseEntity<Usuario>(usuarioLocal, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam(name = "idUser", required = true) Long idUser) {
        HttpStatus httpStatusLocal = this.usuarioService.deletarUsuario(idUser);

       if (httpStatusLocal == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<String>("Não localizado! " + idUser, httpStatusLocal);
        }
       return new ResponseEntity<String>("User Deleted Successful", httpStatusLocal);
    }

    @GetMapping(value = "/buscarUserId")
    @ResponseBody
    public ResponseEntity<?> buscarId(@RequestBody @NotNull Usuario usuario) {
        Optional<Usuario> usuarioLocal = this.usuarioService.buscarIdUsuario(usuario.getId());

        if (!usuarioLocal.isPresent()) {
            return new ResponseEntity<String>("Não localizado! " + usuario.getId(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Usuario>>(usuarioLocal, HttpStatus.OK);
    }

    @PutMapping(value = "/atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

        Usuario usuarioLocal = this.usuarioService.atualizarUsuario(usuario);
        if (usuarioLocal == null) {
            return new ResponseEntity<String>("Erro ao atualizar! " + usuario.getId(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuarioLocal, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarUserName")
    @ResponseBody
    public ResponseEntity<?> buscarUserName(@RequestBody @NotNull Usuario usuario) {

        List<Usuario> listaUsuarioLocal = usuarioService.buscarNomeUsuario(usuario.getNome().trim().toUpperCase());
        if (listaUsuarioLocal.isEmpty()) {
            return new ResponseEntity<String>("Não Localizado! " + usuario.getNome(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Usuario>>(listaUsuarioLocal, HttpStatus.OK);
    }

}
