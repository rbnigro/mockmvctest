package br.com.springboot.cursojdevtreinamento.repository;

import br.com.springboot.cursojdevtreinamento.model.ItemVendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVendaModel, Long> {
}
