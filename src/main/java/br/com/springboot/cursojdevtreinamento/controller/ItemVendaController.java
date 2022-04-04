package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.dto.ItemVendaDTO;
import br.com.springboot.cursojdevtreinamento.model.ItemVendaModel;
import br.com.springboot.cursojdevtreinamento.service.ItemVendaService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/itemvenda")
public class ItemVendaController {

    private static final Logger logger = LogManager.getLogger(ItemVendaService.class);

    final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @GetMapping(value = "/listaTodos")
    public ResponseEntity<List<ItemVendaModel>> listar() {
        List<ItemVendaModel> itemLocal = this.itemVendaService.listaTodos();

        logger.info("Hello world");
        logger.debug("we are in logger info mode");

        if (itemLocal == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemLocal);
    }

    @PostMapping(value="/salvar")
    public ResponseEntity<Object> salvar(@RequestBody @Valid @NotNull ItemVendaDTO itemVendaDTO) {


        var itemVendaModel = new ItemVendaModel();
        BeanUtils.copyProperties(itemVendaDTO, itemVendaModel);
        itemVendaModel.setValorTotal(new BigDecimal(itemVendaDTO.getQuantidade()).multiply(itemVendaDTO.getValorMercadoria()));
        return ResponseEntity.status(HttpStatus.CREATED).body(itemVendaService.salvar(itemVendaModel));
    }
}
