package br.com.zupacademy.victor.orangetalents06templateproposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cartao", url = "#{'${api.cards.url}'}")
public interface CartaoClient {

    @Transactional
    @PostMapping
    Cartao criaCartaoPropostaElegivel(@RequestBody CriaCartaoRequest criaCartaoRequest);

}
