package br.com.springboot.cursojdevtreinamento.model;

import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
public class UsuarioModelTest {

    @Autowired
    UsuarioService usuarioService;

    @Test public void naoSei() {

    }
}
