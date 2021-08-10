package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "carteira", url = "#{'${api.cards.url}'}")
public interface AssociaCarteiraClient {

    @Transactional
    @PostMapping("/{id}/carteiras")
    AssociaCarteiraResponse associaCartão(@PathVariable String id, @RequestBody AssociaCarteiraRequest associaCarteiraRequest);
}
