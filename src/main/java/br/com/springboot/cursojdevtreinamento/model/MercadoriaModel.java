package br.com.springboot.cursojdevtreinamento.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "table_mercadoria")
@SequenceGenerator(name = "seqMercadoria", sequenceName = "seqMercadoria", allocationSize = 1)
public class MercadoriaModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMercadoria")
    private Long idMercadoria;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    public Long getIdMercadoria() {
        return idMercadoria;
    }

    public void setIdMercadoria(Long idMercadoria) {
        this.idMercadoria = idMercadoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "MercadoriaModel{ idMercadoria=" + getIdMercadoria() + ", descricao=" + getDescricao() + ", valor=" + getValor() +'}';
    }

    public String toXML() {
        return "{\"idMercadoria\": " + getIdMercadoria() + ", \"descricao\": " + "\"" + getDescricao() + ", \"valor\": " + "\"" + getValor() + '}';
    }
}
