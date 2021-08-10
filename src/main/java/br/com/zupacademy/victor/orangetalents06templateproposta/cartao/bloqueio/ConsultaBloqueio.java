package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "aviso", url = "#{'${api.cards.url}'}")
public interface ConsultaBloqueio {

    @Transactional
    @PostMapping("/{id}/bloqueios")
    NotificaBloqueioResponse notifica(@PathVariable String id, Map<String, String> sistemaResponsavel);
}
