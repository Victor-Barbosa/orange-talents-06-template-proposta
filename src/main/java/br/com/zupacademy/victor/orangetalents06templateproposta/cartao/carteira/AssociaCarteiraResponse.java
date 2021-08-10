package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

public class AssociaCarteiraResponse {

    private CarteiraResultado resultado;
    private String id;

    public AssociaCarteiraResponse() {
    }

    public AssociaCarteiraResponse(CarteiraResultado resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public CarteiraResultado getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
