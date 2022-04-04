package br.com.springboot.cursojdevtreinamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.springboot.cursojdevtreinamento.dto.UsuarioDTO;
import br.com.springboot.cursojdevtreinamento.model.UsuarioModel;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ArrayList<UsuarioModel> listUsuarioModel = new ArrayList<>();

    // nao tinha mada
    @Mock
    private UsuarioModel usuarioModelLocal;

    @Mock
    private UsuarioDTO usuarioDTOLocal;

    @BeforeEach
    public void setup() { // antes de cada teste
        this.usuarioModelLocal = new UsuarioModel();
        this.usuarioModelLocal.setIdUsuario(1L);
        this.usuarioModelLocal.setNome("Sussu");
        this.usuarioModelLocal.setIdade(50);

        this.listUsuarioModel.add(this.usuarioModelLocal);

        RestAssuredMockMvc.standaloneSetup(this.usuarioController);

        this.usuarioDTOLocal = new UsuarioDTO();
        this.usuarioDTOLocal.setNome(this.usuarioModelLocal.getNome());
        this.usuarioDTOLocal.setIdade(this.usuarioModelLocal.getIdade());
    }

    @AfterEach
    public void cleanSetup() {
        this.usuarioModelLocal = null;
        this.usuarioDTOLocal = null;

        this.listUsuarioModel.clear();
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarListaUsuarioMockito() throws Exception {

        Mockito
                .when(this.usuarioService.listaTodos())
                .thenReturn(listUsuarioModel);

        mockMvc
                .perform(get("/api/usuarios/listaTodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].nome", Matchers.equalTo(this.usuarioModelLocal.getNome())));
    }

        @Test
        void deveRetornarSucesso_QuandoBuscarListaUsuario() {
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
        Mockito.when(this.usuarioService.listaTodos())
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
        Mockito.when(usuarioService.existsByNome(usuarioDTOLocal.getNome())).thenReturn(false);
        Mockito.when(usuarioService.validarInputJson(usuarioDTOLocal)).thenReturn(true);
        Mockito.when(usuarioService.salvar(ArgumentMatchers.any())).thenReturn(usuarioModelLocal);
        String json = mapper.writeValueAsString(usuarioModelLocal);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios/salvar")
                 .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
              //  .andExpect(jsonPath("$.idUsuario", Matchers.equalTo(1L)))
                .andExpect(jsonPath("$.nome", Matchers.equalTo(usuarioModelLocal.getNome())))
                .andExpect(jsonPath("$.idade", Matchers.equalTo(usuarioModelLocal.getIdade())));
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
        Mockito.when(this.usuarioService.buscarId(1L)).thenReturn(Optional.ofNullable(this.usuarioModelLocal));

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
        Mockito.when(this.usuarioService.buscarId(1L))
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
        Mockito.when(this.usuarioService.buscarId(14L))
                .thenReturn(Optional.ofNullable(this.usuarioModelLocal));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .log().all()
                .body("{\"idUsuario\": 14}")
                .when()
                .get("/api/usuarios/buscarId")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarErro_QuandoBuscarIdUsuario() {
        Mockito.when(this.usuarioService.buscarId(14L))
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

    @Test
    public void deveRetornarSucesso_QuandoAtualizarUsuario() throws Exception {
        Mockito.when(this.usuarioService.atualizar(ArgumentMatchers.any())).thenReturn(usuarioModelLocal);

        String json = mapper.writeValueAsString(usuarioModelLocal);
        mockMvc.perform(put("/api/usuarios/atualizar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$.idUsuario", Matchers.equalTo(usuarioModelLocal.getIdUsuario())))
                .andExpect(jsonPath("$.nome", Matchers.equalTo(usuarioModelLocal.getNome())))
                .andExpect(jsonPath("$.idade", Matchers.equalTo(usuarioModelLocal.getIdade())));
    }

}
