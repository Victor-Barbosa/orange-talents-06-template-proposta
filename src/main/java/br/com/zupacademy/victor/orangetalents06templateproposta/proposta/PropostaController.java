package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/proposta")
public class PropostaController {

    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private PropostaRepository propostaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder){
        logger.info("PropostaController.criaProposta - Iniciando criação de proposta" + request);

        if (propostaRepository.existsByDocumento(request.getDocumento())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta para esse documento!");
        }

        Proposta novaProposta = request.toModel();
        propostaRepository.save(novaProposta);

        URI consultaProposta = builder.path("ConsultaProposta/{id}").build(novaProposta.getId());

        logger.info("Finalizando criação da proposta" + novaProposta );

        return ResponseEntity.status(HttpStatus.CREATED).body(consultaProposta);

    }
}
