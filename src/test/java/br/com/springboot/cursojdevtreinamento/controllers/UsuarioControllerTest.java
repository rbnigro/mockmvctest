package br.com.springboot.cursojdevtreinamento.controllers;

import br.com.springboot.cursojdevtreinamento.model.Usuario;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuarioLocal;

    @BeforeEach
    public void setup() { // antes de cada teste
        this.usuarioLocal = new Usuario();
        this.usuarioLocal.setId(1L);
        this.usuarioLocal.setNome("Sussu");
        this.usuarioLocal.setIdade(50);

        RestAssuredMockMvc.standaloneSetup(this.usuarioController);

    }

    // @Test
    void deveRetornarSucesso_QuandoBuscarUsuario() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/usuarios/buscarUserId")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarListaUsuario() {

        ArrayList<Usuario> listUsuario = new ArrayList<>();
        listUsuario.add(this.usuarioLocal);

        when(this.usuarioService.listarUsuarios())
                .thenReturn(listUsuario);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/usuarios/listaTodos")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarNaoEncontrado_QuandoBuscarListaUsuario() {
        when(this.usuarioService.listarUsuarios())
                .thenReturn(null);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/usuarios/listaTodos")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarScuesso_QuandoCriarUsuario() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body("{\"nome\": \"Ronney\",\"idade\": 14}")
                .when()
                .post("/api/usuarios/salvar")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
