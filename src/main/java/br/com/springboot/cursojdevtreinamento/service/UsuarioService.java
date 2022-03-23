package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.Usuario;
import br.com.springboot.cursojdevtreinamento.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuariosLocal = usuarioRepository.findAll();
        return usuariosLocal;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        Usuario usuarioLocal = usuarioRepository.save(usuario);
        return usuarioLocal;
    }

    public HttpStatus deletarUsuario(Long idUser) {
        Optional<Usuario> usuarioLocal = usuarioRepository.findById(idUser);
        if (!usuarioLocal.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        usuarioRepository.deleteById(idUser);
        return HttpStatus.OK;
    }

    public Optional<Usuario> buscarIdUsuario(Long idUser) {
        Optional<Usuario> usuarioLocal = usuarioRepository.findById(idUser);

        if (!usuarioLocal.isPresent()) {
            return Optional.empty();
        }
        return usuarioLocal;
    }

    public Usuario atualizarUsuario(@NotNull Usuario usuario) {
        Optional<Usuario> usuarioLocal = usuarioRepository.findById(usuario.getId());

        if ((usuario.getId() == null) || (!usuarioLocal.isPresent())) {
            return null;
        }
        Usuario usuarioRetorno = usuarioRepository.saveAndFlush(usuario);
        return usuarioRetorno;
    }

    public List<Usuario> buscarNomeUsuario(String nomeUsuario) {
        List<Usuario> listaUsuarioLocal = usuarioRepository.buscarPorNome(nomeUsuario.trim().toUpperCase());

        if (listaUsuarioLocal.size() == 0) {
            return Collections.emptyList();
        }
            return listaUsuarioLocal;
    }
}
