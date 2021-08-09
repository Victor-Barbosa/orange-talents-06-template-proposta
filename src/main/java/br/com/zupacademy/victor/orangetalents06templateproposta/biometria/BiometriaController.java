package br.com.zupacademy.victor.orangetalents06templateproposta.biometria;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.PropostaController;
import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/biometria")
public class BiometriaController {

    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private CartaoRepository cartaoRepository;
    private BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<String> cadastraBiometria(@PathVariable("idCartao") Long idCartao, @Valid @RequestBody BiometriaRequest biometriaRequest,
                                               UriComponentsBuilder uriComponentsBuilder){
        logger.info("BiometriaController.cadastraBiometria - Iniciando criação de biometria " + biometriaRequest);

        var cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Não existe cartão com esse id!"));

        var novaBiometria = biometriaRequest.toModel(cartao);
        biometriaRepository.save(novaBiometria);
        var uri = uriComponentsBuilder.path("/api/v1/biometria/{id}").build(novaBiometria.getId());

        logger.info("Finalizando criação da biometia" + novaBiometria);
        return ResponseEntity.created(uri).body("Biometria Criada");

    }

}
