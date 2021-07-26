package br.com.zupacademy.victor.orangetalents06templateproposta.utils;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.NovaPropostaRequest;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.AnalisaPropostaRequest;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.AnalisaPropostaResponse;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise;

import java.math.BigDecimal;

public class Factory {

    public static final String DOCUMENTO_ELEGIVEL = "08690153039";
    public static final String DOCUMENTO_NAO_ELEGIVEL = "32384973754";
    public static final String EMAIL= "zup@zup.com.br";
    public static final String NOME = "zup";
    public static final String ENDERECO = "rua 1";
    public static final BigDecimal SALARIO = BigDecimal.valueOf(5000);
    public static final String ID_PROPOSTA = "1";
    public static final String URI = "/api/v1/proposta";


    public static NovaPropostaRequest buildNovaPropostaRequest(String documento) {
        return new NovaPropostaRequest
                (documento,EMAIL , NOME, ENDERECO, SALARIO);
    }

    public static AnalisaPropostaRequest buildAnalisaPropostaRequest(String documento) {
        return new AnalisaPropostaRequest(ID_PROPOSTA, documento, NOME);
    }

    public static AnalisaPropostaResponse buildAnalisaPropostaResponse(String documento, ResultadoAnalise resultadoAnalise) {
        return new AnalisaPropostaResponse(ID_PROPOSTA, documento, NOME, resultadoAnalise);
    }
}
