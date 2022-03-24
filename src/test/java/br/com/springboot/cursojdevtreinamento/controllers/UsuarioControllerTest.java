package br.com.springboot.cursojdevtreinamento.controllers;

import br.com.springboot.cursojdevtreinamento.model.Usuario;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

// import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
// import static org.mockito.Mockito.when;

@WebMvcTest
class UsuarioControllerTest {

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

    @Test
    void deveRetornarSucesso_QuandoBuscarListaUsuario() {

        ArrayList<Usuario> listUsuario = new ArrayList<>();
        listUsuario.add(this.usuarioLocal);

        Mockito.when(this.usuarioService.listarUsuarios())
                .thenReturn(listUsuario);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/usuarios/listaTodos")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarNaoEncontrado_QuandoBuscarListaUsuario() {
        Mockito.when(this.usuarioService.listarUsuarios())
                .thenReturn(null);

        RestAssuredMockMvc.given()
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
                .log().all()
                .body("{\"nome\": \"Ronney\",\"idade\": 14}")
                .when()
                .post("/api/usuarios/salvar")
                .then()
                .assertThat()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetonarScuesso_QuandoApagarUsuario() {
        RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("idUser", 13)
                .request()
                .delete("/cursojdevtreinamento/api/usuarios/delete/");
                // .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarIdUsuario() {
       Mockito.when(this.usuarioService.buscarIdUsuario(1L))
                .thenReturn(Optional.ofNullable(this.usuarioLocal));

        RestAssuredMockMvc.given()
                .contentType("multipart/form-data")
                .multiPart("idUser", 1L)
                .when()
                .get("/api/usuarios/buscarUserId");
    }

}
