package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento;

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
}
