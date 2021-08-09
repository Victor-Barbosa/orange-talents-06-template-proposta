package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NotificaBloqueioResponse {

    private StatusCartao resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NotificaBloqueioResponse(StatusCartao resultado) {
        this.resultado = resultado;
    }

    public StatusCartao getResultado() {
        return resultado;
    }
}
