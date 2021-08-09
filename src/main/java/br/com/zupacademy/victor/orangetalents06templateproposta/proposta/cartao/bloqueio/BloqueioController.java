package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.bloqueio;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.PropostaController;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import static br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.bloqueio.StatusCartao.BLOQUEADO;

@RestController
@RequestMapping("/api/v1/bloqueio")
public class BloqueioController {

    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private CartaoRepository cartaoRepository;
    private BloqueioRepository bloqueioRepository;

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    @PostMapping("/{idCartao}")
    public void bloqueiaCartao (@PathVariable Long idCartao, CartaoRequest cartaoRequest, HttpServletRequest httpServletRequest ){
        logger.info("BloqueioController.bloqueiaCartao - Iniciando bloqueio de cartão " + cartaoRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Não existe cartão com esse id!"));

        if (BLOQUEADO.equals(cartao.getStatusCartao())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
        }

        String ip = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        Bloqueio novoBloqueio = cartaoRequest.toModel(ip, userAgent, cartao.setStatusCartao(BLOQUEADO));

        bloqueioRepository.save(novoBloqueio);

        logger.info("Finalizando bloqueio de cartão" + novoBloqueio);

    }

}
