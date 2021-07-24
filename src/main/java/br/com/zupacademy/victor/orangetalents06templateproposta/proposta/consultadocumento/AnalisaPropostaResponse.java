package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento;

public class AnalisaPropostaResponse {

    private String idProposta;
    private String documento;
    private String nome;
    private ResultadoAnalise resultadoSolicitacao;

    public AnalisaPropostaResponse(String idProposta, String documento, String nome, ResultadoAnalise resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public ResultadoAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
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
}
