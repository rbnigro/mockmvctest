package br.com.springboot.cursojdevtreinamento.dto;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ItemVendaDTO {

    @NotNull
    private Long idVenda;

    @NotNull
    private Long idMercadoria;

    @NotNull
    private int quantidade;

    @NotNull
    private BigDecimal valorMercadoria;

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

    @Override
    public String toString() {
        return "ItemVendaDTO{" +
                "idVenda=" + getIdVenda() +
                ", idMercadoria=" + getIdMercadoria() +
                ", quantidade=" + getQuantidade() +
                ", valorMercadoria=" + getValorMercadoria() +
                '}';
    }
}
