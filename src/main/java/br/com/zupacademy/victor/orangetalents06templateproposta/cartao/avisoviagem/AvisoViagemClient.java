package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "bloqueio", url = "#{'${api.cards.url}'}")
public interface AvisoViagemClient {

    @Transactional
    @PostMapping("/{id}/avisos")
    AvisoViagemResponse avisoViagem(@PathVariable String id, @RequestBody AvisoViagemRequest avisoViagemRequest);
}
