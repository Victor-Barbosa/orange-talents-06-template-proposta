package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String documento;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private ResultadoAnalise resultadoAnalise = ResultadoAnalise.NAO_ELEGIVEL;


    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    @Deprecated
    public Proposta() {

    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void setResultadoAnalise(ResultadoAnalise resultadoAnalise) {
        this.resultadoAnalise = resultadoAnalise;
    }

    public ResultadoAnalise getResultadoAnalise() {
        return resultadoAnalise;
    }
}