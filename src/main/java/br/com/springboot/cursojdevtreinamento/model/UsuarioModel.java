package br.com.springboot.cursojdevtreinamento.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "table_usuario")
@SequenceGenerator(name = "seqUsuario", sequenceName = "seqUsuario", allocationSize = 1)
public class UsuarioModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsuario")
    private Long idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int idade;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

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
        return "UsuarioModel {id=" + getIdUsuario() + ", nome=" + getNome() + ", idade=" + getIdade() + '}';
    }

    public String toXML() {
        return "{\"idUsuario\": " + getIdUsuario() + ", \"nome\": " + "\"" + getNome() + "\"" + ", \"idade" + "\": " + getIdade() + '}';
    }
}
