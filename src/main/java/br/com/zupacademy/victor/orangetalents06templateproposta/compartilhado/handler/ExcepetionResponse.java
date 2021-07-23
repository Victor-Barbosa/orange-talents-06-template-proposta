package br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado.handler;

import java.time.LocalDateTime;

public class ExcepetionResponse {

    private final String campo;
    private final String mensagem;
    private final String status;
    private final LocalDateTime ocorridoEm = LocalDateTime.now();

    public ExcepetionResponse(String campo, String mensagem, String status) {
        this.campo = campo;
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOcorridoEm() {
        return ocorridoEm;
    }
}
