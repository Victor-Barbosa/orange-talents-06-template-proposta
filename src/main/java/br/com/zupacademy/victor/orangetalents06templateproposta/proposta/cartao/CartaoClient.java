package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cartao", url = "http://localhost:8888/api")
public interface CartaoClient {

    @Transactional
    @PostMapping(value = "/cartoes")
    Cartao criaCartaoPropostaElegivel(@RequestBody CriaCartaoRequest criaCartaoRequest);

}
