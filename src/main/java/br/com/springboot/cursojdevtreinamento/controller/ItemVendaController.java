package br.com.springboot.cursojdevtreinamento.controller;

import br.com.springboot.cursojdevtreinamento.model.ItemVendaModel;
import br.com.springboot.cursojdevtreinamento.service.ItemVendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/itemvenda")
public class ItemVendaController {

    final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @GetMapping(value = "/listaTodos")
    public ResponseEntity<List<ItemVendaModel>> listar() {
        List<ItemVendaModel> itemLocal = this.itemVendaService.listaTodos();

        if (itemLocal == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemLocal);

    }
}
