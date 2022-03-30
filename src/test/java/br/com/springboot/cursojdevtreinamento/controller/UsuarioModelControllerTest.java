package br.com.springboot.cursojdevtreinamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

// import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
// import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UsuarioController.class)
class UsuarioModelControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private static ObjectMapper mapper = new ObjectMapper();

    // nao tinha mada
    @Mock
    private UsuarioModel usuarioModelLocal;

    @BeforeEach
    public void setup() { // antes de cada teste
        this.usuarioModelLocal = new UsuarioModel();
        this.usuarioModelLocal.setIdUsuario(1L);
        this.usuarioModelLocal.setNome("Sussu");
        this.usuarioModelLocal.setIdade(50);

        RestAssuredMockMvc.standaloneSetup(this.usuarioController);
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarListaUsuario() {

        ArrayList<UsuarioModel> listUsuarioModel = new ArrayList<>();
        listUsuarioModel.add(this.usuarioModelLocal);

        Mockito.when(this.usuarioService.listarUsuarios())
                .thenReturn(listUsuarioModel);

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
    void deveRetornarSucesso_QuandoCriarUsuario() throws Exception {

        Mockito.when(usuarioService.salvarUsuario(ArgumentMatchers.any())).thenReturn(this.usuarioModelLocal);
      //  Mockito.when(usuarioService.validarInputJson(ArgumentMatchers.any())).thenReturn(true);

        String json = mapper.writeValueAsString(this.usuarioModelLocal);
        mockMvc.perform(post("/api/usuarios/salvar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                //  .andExpect(jsonPath("$.id", Matchers.equalTo(this.usuarioModelLocal.getId())))
                .andExpect(jsonPath("$.nome", Matchers.equalTo(this.usuarioModelLocal.getNome())))
                .andExpect(jsonPath("$.idade", Matchers.equalTo(this.usuarioModelLocal.getIdade())));
    }

    @Test
    void deveRetornarSucesso_QuandoCriarUsuarioTWO() throws Exception {
        Mockito.when(this.usuarioService.salvarUsuario(this.usuarioModelLocal)).thenReturn(this.usuarioModelLocal);
        Mockito.when(this.usuarioService.validarInputJson(this.usuarioModelLocal)).thenReturn(true);

        JSONObject json1 = new JSONObject();
        json1.put("nome", this.usuarioModelLocal.getNome());
        json1.put("idade", this.usuarioModelLocal.getIdade());

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                //.accept(ContentType.JSON)
                .contentType("application/json")
                .body(json1.toString())
                .when()
                .post("/api/usuarios/salvar");
               // .then()
               // .log().all()
               // .assertThat().body("message", equalTo("Message accepted"));
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
        Mockito.when(this.usuarioService.buscarIdUsuario(1L)).thenReturn(Optional.ofNullable(this.usuarioModelLocal));

        RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id", 18) //this.usuarioModelLocal.getId())
                .when()
                .delete("/cursojdevtreinamento/api/usuarios/apagar");
              //  .then()
              //  .log().all()
              //  .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetonarErro_QuandoApagarUsuario() {
        Mockito.when(this.usuarioService.buscarIdUsuario(1L))
                .thenReturn(Optional.ofNullable(this.usuarioModelLocal));

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id", this.usuarioModelLocal.getIdUsuario())
                .log().all()
                .delete("/cursojdevtreinamento/api/usuarios/apagar/")
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarIdUsuario() {
        Mockito.when(this.usuarioService.buscarIdUsuario(14L))
                .thenReturn(Optional.ofNullable(this.usuarioModelLocal));

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
                .thenReturn(Optional.ofNullable(this.usuarioModelLocal));

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
