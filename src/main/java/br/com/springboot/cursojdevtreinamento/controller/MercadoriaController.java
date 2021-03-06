package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.MercadoriaDTO;
import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import br.com.springboot.cursojdevtreinamento.service.MercadoriaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Page<MercadoriaModel>> getAll(@PageableDefault(page = 0, size = 10, sort = "descricao", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(mercadoriaService.findAll(pageable));
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object> salvar(@RequestBody @Valid @NotNull MercadoriaDTO mercadoriaDTO) {
        if (mercadoriaService.existsByDescricao(mercadoriaDTO.getDescricao())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito descrição já existente: " + mercadoriaDTO.getDescricao());
        }

        var mercadoriaModel = new MercadoriaModel();
        BeanUtils.copyProperties(mercadoriaDTO, mercadoriaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoriaService.salvar(mercadoriaModel));
    }

    @GetMapping(value = "/buscarId")
    @ResponseBody
    public ResponseEntity<Object> buscarId(@RequestBody @NotNull MercadoriaModel mercadoriaModel) {
        Optional<MercadoriaModel> mercadoriaLocal = this.mercadoriaService.findById(mercadoriaModel.getIdMercadoria());

        if (!mercadoriaLocal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizado! " + mercadoriaModel.getIdMercadoria());
        }
        return ResponseEntity.status(HttpStatus.OK).body(mercadoriaLocal.get());
    }

    @DeleteMapping(value = "/apagar")
    public ResponseEntity<Object> apagar(@RequestParam(name = "id") Long id) {
        HttpStatus httpStatusLocal = this.mercadoriaService.apagar(id);

        if (httpStatusLocal == HttpStatus.NO_CONTENT) { // NO_CONTENT -> Cancela o Body
            return ResponseEntity.status(httpStatusLocal).body("Não localizado para apagar! " + id);
        }

        return ResponseEntity.status(httpStatusLocal).body("Apagado com Sucesso");
    }
}
