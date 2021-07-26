package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise;

import java.math.BigDecimal;

public class DadosPropostaDto {

    private String documento;
    private String nome;
    private String email;
    private BigDecimal salario;
    private ResultadoAnalise resultadoAnalise;

    public DadosPropostaDto(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.salario = proposta.getSalario();
        this.resultadoAnalise = proposta.getResultadoAnalise();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public ResultadoAnalise getResultadoAnalise() {
        return resultadoAnalise;
    }
}
