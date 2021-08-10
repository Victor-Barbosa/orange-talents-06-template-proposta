package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

public class AvisoViagemResponse {

    private AvisoResultado resultado;

    public  AvisoViagemResponse(){
    }

    public AvisoViagemResponse(AvisoResultado resultado) {
        this.resultado = resultado;
    }

    public AvisoResultado getResultado() {
        return resultado;
    }

    public void setResultado(AvisoResultado resultado) {
        this.resultado = resultado;
    }
}
