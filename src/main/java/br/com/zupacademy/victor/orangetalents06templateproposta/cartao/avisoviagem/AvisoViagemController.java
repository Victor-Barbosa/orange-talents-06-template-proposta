package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.CartaoRepository;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.PropostaController;
import feign.FeignException.FeignClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem.AvisoResultado.FALHA;

@RestController
@RequestMapping("/api/v1/viagem")
public class AvisoViagemController {
    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private CartaoRepository cartaoRepository;
    private AvisoViagemRepository avisoViagemRepository;
    private AvisoViagemClient avisoViagemClient;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository, AvisoViagemClient avisoViagemClient) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.avisoViagemClient = avisoViagemClient;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<String> criaAvisoViagem(@PathVariable Long idCartao, @Valid @RequestBody AvisoViagemRequest avisoViagemRequest, HttpServletRequest httpServletRequest) {
        logger.info("AvisoViagemController.AvisoViagemController - Iniciando aviso viagem " + avisoViagemRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cartão com esse id!"));

        var ip = httpServletRequest.getRemoteAddr();
        var userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        try {
            AvisoViagemResponse avisoViagemResponse = avisoViagemClient.avisoViagem(cartao.getId(), avisoViagemRequest);

            if (FALHA.equals(avisoViagemResponse.getResultado())) {
                return ResponseEntity.unprocessableEntity().body("Falha ao Criado aviso viagem!");
            }

            var novoAvisoViagem = avisoViagemRequest.toModel(cartao, ip, userAgent);
            avisoViagemRepository.save(novoAvisoViagem);
            logger.info("Finalizando aviso viagem" + novoAvisoViagem);

            return ResponseEntity.ok().body("Aviso Viagem Criado!");

        } catch (FeignClientException ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.unprocessableEntity().body("Falha ao Criado aviso viagem!");
        } catch (Exception ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.internalServerError().body("Erro inesperado, tente novamente!");
        }
    }
}
