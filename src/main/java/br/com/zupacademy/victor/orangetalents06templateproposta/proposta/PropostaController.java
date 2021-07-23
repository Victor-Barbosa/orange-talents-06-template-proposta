package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/proposta")
public class PropostaController {

    Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder){
        logger.info("PropostaController.criaProposta - Iniciando criação de proposta" + request);

        Proposta novaProposta = request.toModel();
        manager.persist(novaProposta);

        URI consultaProposta = builder.path("ConsultaProposta/{id}").build(novaProposta.getId());

        logger.info("Finalizando criação da proposta" + novaProposta );

        return ResponseEntity.status(HttpStatus.CREATED).body(consultaProposta);

    }
}
