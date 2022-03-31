package br.com.springboot.cursojdevtreinamento.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
@SequenceGenerator(name = "seqVenda", sequenceName = "seqVenda", allocationSize = 1, initialValue = 1)
public class VendaModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqVenda")
    private Long idVenda;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @Column(nullable = false)
    private Double totalVenda;

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(Double totalVenda) {
        this.totalVenda = totalVenda;
    }
// TODO configuração de data https://youtu.be/LXRU-Z36GEU?t=6550
    // conversao de DTO para model
    // vendamodel.setDataVena(LocalDateTime.now(ZoneId.of("UTC"))); DTO nao tem data
    // return
}
