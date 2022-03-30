package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import br.com.springboot.cursojdevtreinamento.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioModel> listarUsuarios() {
        List<UsuarioModel> usuariosLocal = usuarioRepository.findAll();
        return usuariosLocal;
    }

    @Transactional
    public UsuarioModel salvarUsuario(UsuarioModel usuarioModel) {
        UsuarioModel usuarioModelRetorno = null;

        try {
            usuarioModelRetorno = usuarioRepository.save(usuarioModel);
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        return usuarioModelRetorno;
    }

    @Transactional
    public HttpStatus deletarUsuario(Long idUsuario) {
        Optional<UsuarioModel> usuarioLocal = usuarioRepository.findById(idUsuario);

        if (!usuarioLocal.isPresent()) {
            return HttpStatus.NO_CONTENT;
        }
        usuarioRepository.deleteById(idUsuario);
        return HttpStatus.OK;
    }

    public Optional<UsuarioModel> buscarIdUsuario(@NotNull Long idUsuario) {
        Optional<UsuarioModel> usuarioLocal = usuarioRepository.findById(idUsuario);

        if (!usuarioLocal.isPresent()) {
            return Optional.empty();
        }
        return usuarioLocal;
    }

    @Transactional
    public UsuarioModel atualizarUsuario(@NotNull UsuarioModel usuarioModel) {
        Optional<UsuarioModel> usuarioLocal = usuarioRepository.findById(usuarioModel.getIdUsuario());

        if ((usuarioModel.getIdUsuario() == null) || (!usuarioLocal.isPresent())) {
            return null;
        }
        UsuarioModel usuarioModelRetorno = usuarioRepository.saveAndFlush(usuarioModel);
        return usuarioModelRetorno;
    }

    public List<UsuarioModel> buscarNomeUsuario(@NotNull String nomeUsuario) {
        List<UsuarioModel> listaUsuarioLocalModel = usuarioRepository.buscarNomeUsario(nomeUsuario.trim().toUpperCase());

        if (listaUsuarioLocalModel.size() == 0) {
            return Collections.emptyList();
        }
        return listaUsuarioLocalModel;
    }

    @Transactional
    public boolean validarInputJson(@NotNull UsuarioModel usuarioModel) {
        // TODO preprar para remover com valid
        return  (usuarioModel.getNome() != null && usuarioModel.getIdade() != 0);
    }
}
