package br.com.springboot.cursojdevtreinamento.service;

import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import br.com.springboot.cursojdevtreinamento.repository.MercadoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoriaService {

    final MercadoriaRepository mercadoriaRepository;

    public MercadoriaService(MercadoriaRepository mercadoriaRepository) {
        this.mercadoriaRepository = mercadoriaRepository;
    }

    @Transactional
    public MercadoriaModel salvarMercadoria(MercadoriaModel mercadoriaModel) {
        return mercadoriaRepository.save(mercadoriaModel);
    }

    public boolean existsByDescricao(String descricao) {
        return mercadoriaRepository.existsByDescricao(descricao);
    }

    public List<MercadoriaModel> findAll() {
        return mercadoriaRepository.findAll();
    }

    public Optional<MercadoriaModel> findById(Long idMercadoria) {
        return mercadoriaRepository.findById(idMercadoria);
    }

    @Transactional
    public HttpStatus apagar(Long id) {
        Optional<MercadoriaModel> mercadoriaLocal = mercadoriaRepository.findById(id);

        if (!mercadoriaLocal.isPresent()) {
            return HttpStatus.NO_CONTENT;
        }
        mercadoriaRepository.deleteById(id);
        return HttpStatus.OK;
    }
}
