package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "financeiro", url = "#{'${api.solicitations.url}'}")
public interface ConsultaRestricao {

    @Transactional
    @PostMapping
    AnalisaPropostaResponse avaliaProposta(@RequestBody AnalisaPropostaRequest analisaPropostaRequest);
}
