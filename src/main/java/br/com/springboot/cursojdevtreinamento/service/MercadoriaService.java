package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import br.com.springboot.cursojdevtreinamento.repository.MercadoriaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MercadoriaService {

    final MercadoriaRepository mercadoriaRepository;

    public MercadoriaService(MercadoriaRepository mercadoriaRepository) {
        this.mercadoriaRepository = mercadoriaRepository;
    }

    @Transactional
    public MercadoriaModel save(MercadoriaModel mercadoriaModel) {
        return mercadoriaRepository.save(mercadoriaModel);
    }
}
