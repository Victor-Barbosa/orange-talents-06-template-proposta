package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.AnalisaPropostaRequest;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ConsultaRestricao;
import feign.FeignException.FeignClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise.ELEGIVEL;
import static br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise.SEM_RESTRICAO;

@RestController
@RequestMapping("/api/v1/proposta")
public class PropostaController {


    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private final PropostaRepository propostaRepository;
    private final ConsultaRestricao consultaRestricao;

    public PropostaController(PropostaRepository propostaRepository, ConsultaRestricao consultaRestricao) {
        this.propostaRepository = propostaRepository;
        this.consultaRestricao = consultaRestricao;
    }


    @PostMapping
    public ResponseEntity<String> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder) {
        logger.info("PropostaController.criaProposta - Iniciando criação de proposta " + request);
        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            return ResponseEntity.unprocessableEntity().body("Já existe proposta para esse documento!");
        }
        var novaProposta = request.toModel();
        propostaRepository.save(novaProposta);
        var uri = builder.path("/api/v1/proposta/{id}").build(novaProposta.getId());

        try {
            var analisaPropostaRequest = new AnalisaPropostaRequest(novaProposta.getId().toString(),
                    novaProposta.getDocumento(), novaProposta.getNome());

            var analisaPropostaResponse = consultaRestricao.avaliaProposta(analisaPropostaRequest);

            if (SEM_RESTRICAO.equals(analisaPropostaResponse
                    .getResultadoSolicitacao())) novaProposta.setResultadoAnalise(ELEGIVEL);

            propostaRepository.save(novaProposta);

            logger.info("Finalizando criação da proposta" + novaProposta);

            return ResponseEntity.created(uri).body("Proposta criada: Cliente elegivel!");
        } catch (FeignClientException ex) {
            logger.info(ex.getMessage());

            return ResponseEntity.created(uri).body("Proposta criada: Cliente não elegivel no momento!");
        } catch (Exception ex) {
            logger.info(ex.getMessage());

            propostaRepository.delete(novaProposta);

            return ResponseEntity.internalServerError().body("Erro inesperado, tente novamente!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosPropostaDto> buscaStatusProposta(@PathVariable("id") Long idProposta) {

        var buscaPropostaPorId = propostaRepository.findById(idProposta);

        if (buscaPropostaPorId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe proposta para esse id!");
        }

        return ResponseEntity.ok(new DadosPropostaDto(buscaPropostaPorId.get()));
    }
}