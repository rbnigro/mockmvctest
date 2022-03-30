package br.com.springboot.cursojdevtreinamento.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MercadoriaDTO {

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal valor;

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
        return "MercadoriaDTO {descricao='" + getDescricao() + '\'' + ", valor=" + getValor() + '}';
    }
}
