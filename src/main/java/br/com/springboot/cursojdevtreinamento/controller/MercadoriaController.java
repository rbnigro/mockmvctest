package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.MercadoriaDTO;
import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import br.com.springboot.cursojdevtreinamento.service.MercadoriaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/mercadorias")
public class MercadoriaController {

    final MercadoriaService mercadoriaService;

    public MercadoriaController(MercadoriaService mercadoriaService) {
        this.mercadoriaService = mercadoriaService;
    }

    @GetMapping(value = "/listaTodos")
    // TODO testar com retorno vazio
    public ResponseEntity<List<MercadoriaModel>> getAllMercadoria() {
        return ResponseEntity.status(HttpStatus.OK).body(mercadoriaService.findAll());
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object>  salvarMercadoria(@RequestBody @Valid @NotNull MercadoriaDTO mercadoriaDTO) throws Exception {
        if (mercadoriaService.existsByDescricao(mercadoriaDTO.getDescricao())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito descrição já existente: " + mercadoriaDTO.getDescricao());
        }

        var mercadoriaModel = new MercadoriaModel();
        BeanUtils.copyProperties(mercadoriaDTO, mercadoriaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoriaService.salvarMercadoria(mercadoriaModel));
    }

    @GetMapping(value = "/buscarId")
    @ResponseBody
    public ResponseEntity<Object> buscarMercadoriaId(@RequestBody @NotNull MercadoriaModel mercadoriaModel) {
        Optional<MercadoriaModel> mercadoriaLocal = this.mercadoriaService.findById(mercadoriaModel.getIdMercadoria());

        if (!mercadoriaLocal.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizado! " + mercadoriaModel.getIdMercadoria());
        }
        return ResponseEntity.status(HttpStatus.OK).body(mercadoriaLocal.get());
    }

    @DeleteMapping(value = "/apagar")
    public ResponseEntity<String> apagar(@RequestParam(name = "id", required = true) Long id) {
        HttpStatus httpStatusLocal = this.mercadoriaService.apagar(id);

        if (httpStatusLocal == HttpStatus.NO_CONTENT) { // NO_CONTENT -> Cancela o Body
            return new ResponseEntity<String>("Não localizado para apagar! " + id, httpStatusLocal);
        }

        return new ResponseEntity<String>("Apagado com Sucesso", httpStatusLocal);
    }
}
