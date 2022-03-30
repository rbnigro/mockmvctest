package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.MercadoriaDTO;
import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import br.com.springboot.cursojdevtreinamento.service.MercadoriaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/mercadorias")
public class MercadoriaController {

    final MercadoriaService mercadoriaService;

    public MercadoriaController(MercadoriaService mercadoriaService) {
        this.mercadoriaService = mercadoriaService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object>  salvarMercadoria(@RequestBody @Valid MercadoriaDTO mercadoriaDTO) throws Exception {
        System.out.println(mercadoriaDTO);

        var mercadoriaModel = new MercadoriaModel();
        BeanUtils.copyProperties(mercadoriaDTO, mercadoriaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoriaService.save(mercadoriaModel));
    }
}
