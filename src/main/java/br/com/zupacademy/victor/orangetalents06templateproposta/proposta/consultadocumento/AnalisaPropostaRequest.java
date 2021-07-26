package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento;

import java.util.Objects;

public class AnalisaPropostaRequest {

    private String idProposta;
    private String documento;
    private String nome;

    public AnalisaPropostaRequest(String idProposta, String documento, String nome) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalisaPropostaRequest that = (AnalisaPropostaRequest) o;
        return Objects.equals(idProposta, that.idProposta) && Objects.equals(documento, that.documento) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProposta, documento, nome);
    }
}
