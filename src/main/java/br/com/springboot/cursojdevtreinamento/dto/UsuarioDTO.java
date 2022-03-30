package br.com.springboot.cursojdevtreinamento.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioDTO {

    @NotBlank
    private String nome;

    @NotNull
    private int idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{ nome='" + getNome() + '\''  + ", idade=" + getIdade() + '}';
    }
}
