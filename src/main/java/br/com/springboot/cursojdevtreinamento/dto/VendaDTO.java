package br.com.springboot.cursojdevtreinamento.dto;

public class VendaDTO {

    private Long idUsuario;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "VendaDTO{ idUsuario=" + getIdUsuario() + '}';
    }
}
