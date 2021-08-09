package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.bloqueio;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.Cartao;

public class CartaoRequest {

    public Bloqueio toModel(String ip, String userAgent, Cartao cartao) {
        return new Bloqueio(ip, userAgent, cartao);
    }
}
