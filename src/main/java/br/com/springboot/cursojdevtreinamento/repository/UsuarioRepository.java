package br.com.springboot.cursojdevtreinamento.repository;

import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    @Query(value = "select u from UsuarioModel u where upper(trim(u.nome)) like %?1%")
    List<UsuarioModel> buscarNomeUsario(String name);

}
