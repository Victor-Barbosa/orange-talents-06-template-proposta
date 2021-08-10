package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira.CarteiraResultado.FALHA;

@RestController
@RequestMapping("/api/v1/carteira")
public class CarteiraController {

    Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    private CartaoRepository cartaoRepository;
    private CarteiraRepository carteiraRepository;
    private AssociaCarteiraClient associaCarteiraClient;

    public CarteiraController(CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository, AssociaCarteiraClient associaCarteiraClient) {
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.associaCarteiraClient = associaCarteiraClient;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<String> associaCarteira (@PathVariable Long idCartao, @Valid @RequestBody AssociaCarteiraRequest associaCarteiraRequest, UriComponentsBuilder builder){
        logger.info("CarteiraController.associaCarteira - Iniciando associção carteira " + associaCarteiraRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cartão com esse id!"));


        if (carteiraRepository.existsByCartao_IdCartaoAndCarteira(idCartao,associaCarteiraRequest.getCarteira())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Esse cartão já foi associado a essa carteira!");
        }

        try {
            var associaCarteiraResponse = associaCarteiraClient.associaCartão(cartao.getId(), associaCarteiraRequest);

            if (FALHA.equals(associaCarteiraResponse.getResultado())){
                return ResponseEntity.unprocessableEntity().body("Falha ao associar carteira!");
            }

            var novaCarteira = associaCarteiraRequest.toModel(cartao);
            carteiraRepository.save(novaCarteira);
            var uri = builder.path("/api/v1/carteira/{id}").build(novaCarteira.getId());

            logger.info("Finalizando associa carteira" + novaCarteira);

            return ResponseEntity.created(uri).body("Cartao associado a carteira!");
        } catch (FeignException.FeignClientException ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.unprocessableEntity().body("Falha ao associar carteira!");
        } catch (Exception ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.internalServerError().body("Erro inesperado, tente novamente!");
        }

    }

}