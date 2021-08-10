package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.CartaoRepository;
import feign.FeignException.FeignClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio.StatusCartao.BLOQUEADO;
import static br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio.StatusCartao.FALHA;

@RestController
@RequestMapping("/api/v1/bloqueio")
public class BloqueioController {

    Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    private CartaoRepository cartaoRepository;
    private BloqueioRepository bloqueioRepository;
    private ConsultaBloqueio consultaBloqueio;

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository, ConsultaBloqueio consultaBloqueio) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.consultaBloqueio = consultaBloqueio;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<String> bloqueiaCartao(@PathVariable Long idCartao, CartaoRequest cartaoRequest, HttpServletRequest httpServletRequest) {
        logger.info("BloqueioController.bloqueiaCartao - Iniciando bloqueio de cartão " + cartaoRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cartão com esse id!"));

        if (BLOQUEADO.equals(cartao.getStatusCartao())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
        }

        var ip = httpServletRequest.getRemoteAddr();
        var userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        try {
            var notificaBloqueioResponse = consultaBloqueio.notifica(cartao.getId(), Map.of("sistemaResponsavel", "proposta-api-victor"));

            if (FALHA.equals(notificaBloqueioResponse.getResultado())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            var novoBloqueio = cartaoRequest.toModel(ip, userAgent, cartao.setStatusCartao(notificaBloqueioResponse.getResultado()));

            bloqueioRepository.save(novoBloqueio);

            logger.info("Finalizando bloqueio de cartão" + novoBloqueio);

            return ResponseEntity.ok().body("Cartão bloqueado!");
        } catch (FeignClientException ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.unprocessableEntity().body("Falha ao bloquear cartão!");
        } catch (Exception ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.internalServerError().body("Erro inesperado, tente novamente!");
        }
    }
}
