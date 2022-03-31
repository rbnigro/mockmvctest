package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.VendaModel;
import br.com.springboot.cursojdevtreinamento.repository.VendaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class VendaService {

    final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @Transactional
    public VendaModel salvar(VendaModel vendaModel) {
        return vendaRepository.save(vendaModel);
    }
}
