package br.com.springboot.cursojdevtreinamento.repository;

import br.com.springboot.cursojdevtreinamento.model.MercadoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoriaRepository extends JpaRepository<MercadoriaModel, Long> {
}
