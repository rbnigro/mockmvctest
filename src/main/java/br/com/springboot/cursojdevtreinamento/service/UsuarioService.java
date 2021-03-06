package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.dto.UsuarioDTO;
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

    public List<UsuarioModel> listaTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public UsuarioModel salvar(UsuarioModel usuarioModel) {
        return usuarioRepository.save(usuarioModel);
    }

    @Transactional
    public HttpStatus apagar(Long id) {
        Optional<UsuarioModel> usuarioLocal = usuarioRepository.findById(id);

        if (!usuarioLocal.isEmpty()) {
            return HttpStatus.NO_CONTENT;
        }
        usuarioRepository.deleteById(id);
        return HttpStatus.OK;
    }

    public Optional<UsuarioModel> buscarId(@NotNull Long idUsuario) {
       return usuarioRepository.findById(idUsuario);
    }

    @Transactional
    public UsuarioModel atualizar(@NotNull UsuarioModel usuarioModel) {
        Optional<UsuarioModel> usuarioLocal = usuarioRepository.findById(usuarioModel.getIdUsuario());

        if ((usuarioModel.getIdUsuario() == null) || (!usuarioLocal.isEmpty())) {
            return null;
        }
        return usuarioRepository.saveAndFlush(usuarioModel);
    }

    public List<UsuarioModel> buscarNomeUsuario(@NotNull String nomeUsuario) {
        List<UsuarioModel> listaUsuarioLocalModel = usuarioRepository.buscarNomeUsuario(nomeUsuario.trim().toUpperCase());

        if (listaUsuarioLocalModel.isEmpty()) {
            return Collections.emptyList();
        }
        return listaUsuarioLocalModel;
    }

    public boolean validarInputJson(@NotNull UsuarioModel usuarioModel) {
        return  (usuarioModel.getNome() != null && usuarioModel.getIdade() != 0);
    }

    public boolean validarInputJson(@NotNull UsuarioDTO usuarioDTO) {
        return  (usuarioDTO.getNome() != null && usuarioDTO.getIdade() != 0);
    }

    public boolean existsByNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }

}
