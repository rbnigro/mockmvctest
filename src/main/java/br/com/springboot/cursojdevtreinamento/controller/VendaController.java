package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.VendaDTO;
import br.com.springboot.cursojdevtreinamento.model.VendaModel;
import br.com.springboot.cursojdevtreinamento.service.UsuarioService;
import br.com.springboot.cursojdevtreinamento.service.VendaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/vendas")
public class VendaController {

    final VendaService vendaService;
    final UsuarioService usuarioService;

    public VendaController(VendaService vendaService, UsuarioService usuarioService) {
        this.vendaService = vendaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/salvar")
    @ResponseBody
    public ResponseEntity<Object> salvar(@RequestBody @Valid @NotNull VendaDTO vendaDTO) {

        if ((vendaDTO.getIdUsuario() == null) || (!usuarioService.buscarId(vendaDTO.getIdUsuario()).isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não localizado: " + vendaDTO.getIdUsuario());
        }

        var vendaModel = new VendaModel();
        BeanUtils.copyProperties(vendaDTO, vendaModel);

        vendaModel.setDataVenda(LocalDateTime.now(ZoneId.of("UTC")));
        vendaModel.setCancelado('N');

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.salvar(vendaModel));
    }
}
