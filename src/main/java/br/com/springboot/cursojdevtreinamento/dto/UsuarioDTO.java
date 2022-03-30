package br.com.springboot.cursojdevtreinamento.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max = 2)
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
