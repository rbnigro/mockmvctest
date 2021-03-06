package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.ItemVendaModel;
import br.com.springboot.cursojdevtreinamento.repository.ItemVendaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ItemVendaService {

    final ItemVendaRepository itemVendaRepository;

    public ItemVendaService(ItemVendaRepository itemVendaRepository) {
        this.itemVendaRepository = itemVendaRepository;
    }

    public List<ItemVendaModel> listaTodos() {
        return itemVendaRepository.findAll();
    }

    @Transactional
    public ItemVendaModel salvar(ItemVendaModel itemVendaModel) {
        return itemVendaRepository.save(itemVendaModel);
    }
}
