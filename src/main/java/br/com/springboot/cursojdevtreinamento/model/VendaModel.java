package br.com.springboot.cursojdevtreinamento.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "table_venda")
@SequenceGenerator(name = "seqVenda", sequenceName = "seqVenda", allocationSize = 1)
public class VendaModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqVenda")
    private Long idVenda;

    @Column(nullable = false)
    private Long idUsuario;

    private LocalDateTime dataVenda;

    private Double totalVenda;

    public char cancelado;

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

    public char getCancelado() {
        return cancelado;
    }

    public void setCancelado(char cancelado) {
        this.cancelado = cancelado;
    }
}
