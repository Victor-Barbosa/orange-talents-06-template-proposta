package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.CartaoRepository;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.PropostaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/viagem")
public class AvisoViagemController {
    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private CartaoRepository cartaoRepository;
    private AvisoViagemRepository avisoViagemRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
    }

    @PostMapping("/{idCartao}")
    public void cria (@PathVariable Long idCartao, @Valid @RequestBody AvisoViagemRequest avisoViagemRequest, HttpServletRequest httpServletRequest){
        logger.info("AvisoViagemController.AvisoViagemController - Iniciando aviso viagem " + avisoViagemRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cartão com esse id!"));

        var ip = httpServletRequest.getRemoteAddr();
        var userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        var novoAvisoViagem = avisoViagemRequest.toModel(cartao, ip, userAgent);
        avisoViagemRepository.save(novoAvisoViagem);

        logger.info("Finalizando aviso viagem" + novoAvisoViagem);
    }
}
