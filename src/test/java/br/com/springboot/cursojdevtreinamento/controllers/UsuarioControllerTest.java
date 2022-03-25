package br.com.springboot.cursojdevtreinamento.controllers;

import br.com.springboot.cursojdevtreinamento.model.Usuario;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;

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
                .log().all()
                .when()
                .get("/api/usuarios/listaTodos")
                .then()
                .log().all()
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
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarSucesso_QuandoCriarUsuario() {
        // TODO aqui
        JSONObject json1 = new JSONObject();
        json1.put("nome", usuarioLocal.getNome());
        json1.put("idade", usuarioLocal.getIdade());

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                //.accept(ContentType.JSON)
                .contentType("application/json")
                .body(json1.toString())
                .when()
                .post("/api/usuarios/salvar")
                .then()
                .assertThat().body("message", equalTo("Message accepted"));
                // .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    void deveRetornarErro_QuandoCriarUsuario() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .log().all()
                .body("{\"cpf\": \"159.100.978-22\",  \"nome\": \"Ronney\",\"idade\": 14}")
                .when()
                .post("/api/usuarios/salvar")
                .then()
                .assertThat()
                .log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetonarSucesso_QuandoApagarUsuario() {
           Mockito.when(this.usuarioService.buscarIdUsuario(1L))
                 .thenReturn(Optional.ofNullable(this.usuarioLocal));

        RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id", 18) //this.usuarioLocal.getId())
                .when()
                .delete("/cursojdevtreinamento/api/usuarios/apagar")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetonarErro_QuandoApagarUsuario() {
        Mockito.when(this.usuarioService.buscarIdUsuario(1L))
                .thenReturn(Optional.ofNullable(this.usuarioLocal));

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id", this.usuarioLocal.getId())
                .log().all()
                .delete("/cursojdevtreinamento/api/usuarios/apagar/")
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarIdUsuario() {
       Mockito.when(this.usuarioService.buscarIdUsuario(14L))
                .thenReturn(Optional.ofNullable(this.usuarioLocal));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .log().all()
                .body("{\"id\": 14}")
                .when()
                .get("/api/usuarios/buscarUserId")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarErro_QuandoBuscarIdUsuario() {
        Mockito.when(this.usuarioService.buscarIdUsuario(14L))
                .thenReturn(Optional.ofNullable(this.usuarioLocal));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .log().all()
                .body("{\"id\": 1}")
                .when()
                .get("/api/usuarios/buscarUserId")
                .then()
                .log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
