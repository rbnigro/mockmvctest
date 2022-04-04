package br.com.springboot.cursojdevtreinamento.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "table_item_venda")
@SequenceGenerator(name = "seqItemVenda", sequenceName = "seqItemVenda", allocationSize = 1)
public class ItemVendaModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemVenda")
    private Long idItemVenda;

    @Column(nullable = false)
    private Long idVenda;

    @Column(nullable = false)
    private Long idMercadoria;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private BigDecimal valorMercadoria;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    public Long getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(Long idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Long getIdMercadoria() {
        return idMercadoria;
    }

    public void setIdMercadoria(Long idMercadoria) {
        this.idMercadoria = idMercadoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorMercadoria() {
        return valorMercadoria;
    }

    public void setValorMercadoria(BigDecimal valorMercadoria) {
        this.valorMercadoria = valorMercadoria;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    @Override
    public String toString() {
        return "ItemVendaModel{" +
                "idItemVenda=" + getIdItemVenda() +
                ", idVenda=" + getIdVenda() +
                ", idMercadoria=" + getIdMercadoria() +
                ", quantidade=" + getQuantidade() +
                ", valorMercadoria=" + getValorMercadoria() +
                ", valorTotal=" + getValorTotal() +
                '}';
    }
}
